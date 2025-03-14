package com.example.demo.Controller;

import com.example.demo.DTO.CartDTO;
import com.example.demo.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartRequest) {
        cartService.addToCart(cartRequest);
        return ResponseEntity.ok("Dịch vụ đã được thêm vào giỏ hàng");
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartDTO>> getCartByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCartItemQuantity(@RequestParam String customerId,
                                                         @RequestParam String serviceId,
                                                         @RequestParam int newQuantity) {
        cartService.updateCartItemQuantity(customerId, serviceId, newQuantity);
        return ResponseEntity.ok("Cập nhật số lượng dịch vụ thành công");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeServiceFromCart(@RequestParam String customerId,
                                                        @RequestParam String serviceId) {
        cartService.removeServiceFromCart(customerId, serviceId);
        return ResponseEntity.ok("Xóa dịch vụ khỏi giỏ hàng thành công");
    }

    @GetMapping("/verify-prices/{customerId}")
    public ResponseEntity<Boolean> verifyServicePricesBeforeCheckout(@PathVariable String customerId) {
        boolean isPriceValid = cartService.verifyServicePricesBeforeCheckout(customerId);
        return ResponseEntity.ok(isPriceValid);
    }
}