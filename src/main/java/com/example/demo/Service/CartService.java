package com.example.demo.Service;

import com.example.demo.DTO.CartDTO;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.ServiceRepository;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ServiceEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public void addToCart(CartDTO cartRequest) {
        Customer customer = customerRepository.findById(cartRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Khách hàng không tồn tại"));

        ServiceEntity serviceEntity = serviceRepository.findById(cartRequest.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Dịch vụ không tồn tại"));

        List<Cart> cartList = cartRepository.findByCustomer_IdAndService_Id(
                cartRequest.getCustomerId(), cartRequest.getServiceId());

        Cart cart;
        if (!cartList.isEmpty()) {
            // Nếu đã có dịch vụ này trong giỏ hàng, cập nhật số lượng
            cart = cartList.get(0);
            cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
            cart.setCost(cart.getService().getServicePrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        } else {
            // Nếu chưa có dịch vụ này, thêm mới vào giỏ hàng
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCustomer(customer);
            cart.setService(serviceEntity);
            cart.setQuantity(cartRequest.getQuantity());
            cart.setCost(serviceEntity.getServicePrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
            cart.setCreatedAt(LocalDateTime.now());
        }

        cartRepository.save(cart);
    }


    public List<CartDTO> getCartByCustomerId(String customerId) {
        List<Cart> cartItems = cartRepository.findByCustomerId(customerId);
        return cartItems.stream().map(cart -> new CartDTO(
                cart.getCustomer().getId(),
                cart.getService().getId(),
                cart.getQuantity()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void updateCartItemQuantity(String customerId, String serviceId, int newQuantity) {
        Cart cart = cartRepository.findByCustomerIdAndServiceId(customerId, serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Dịch vụ không có trong giỏ hàng"));
        cart.setQuantity(newQuantity);
        cart.setCost(cart.getService().getServicePrice().multiply(BigDecimal.valueOf(newQuantity)));
        cartRepository.save(cart);
    }

    @Transactional
    public void removeServiceFromCart(String customerId, String serviceId) {
        cartRepository.deleteByCustomerIdAndServiceId(customerId, serviceId);
    }

    public boolean verifyServicePricesBeforeCheckout(String customerId) {
        List<Cart> cartItems = cartRepository.findByCustomerId(customerId);
        for (Cart cart : cartItems) {
            ServiceEntity latestService = serviceRepository.findById(cart.getService().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Dịch vụ không tồn tại"));

            if (cart.getService().getServicePrice().compareTo(latestService.getServicePrice()) != 0) {
                return false; // Nếu giá không khớp, trả về false
            }
        }
        return true; // Nếu tất cả giá đều khớp, trả về true
    }

}
