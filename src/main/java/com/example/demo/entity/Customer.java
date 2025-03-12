package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name = "ID", length = 255, nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "AccountId", referencedColumnName = "ID", unique = true, foreignKey = @ForeignKey(name = "Customer_ibfk_1"))
    private Account account;
}
