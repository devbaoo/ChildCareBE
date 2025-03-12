package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @Column(name = "CartID", length = 255, nullable = false)
    private String cartId;

    @ManyToOne
    @JoinColumn(name = "CustomerID", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "Cart_Customer_FK"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ServiceID", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "Cart_Service_FK"))
    private ServiceEntity service;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "Cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "CreatedAt", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}