package com.example.demo.Repository;

import com.example.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByCustomerId(String customerId);
    List<Cart> findByCustomer_IdAndService_Id(String customerId, String serviceId);
    Optional<Cart> findByCustomerIdAndServiceId(String customerId, String serviceId);
    void deleteByCustomerIdAndServiceId(String customerId, String serviceId);

}
