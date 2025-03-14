package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "AccountId")
    private Account account;
}