package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Username", unique = true, nullable = false)
    private String username;

    @Column(name = "user_pass", nullable = false)
    private String userpass; // Mật khẩu sẽ được mã hóa

    @Column(name = "Role", nullable = false)
    private String role;

    @Column(name = "FirstName", nullable = false)
    private String firstname;

    @Column(name = "LastName", nullable = false)
    private String lastname;

    @Column(name = "Age")
    private int age;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "PhoneNum", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "Address")
    private String address;

    @Column(name = "status", nullable = false)
    private String status; // ACTIVE / INACTIVE

    @Column(name = "jwt_token", columnDefinition = "TEXT")
    private String jwtToken;


    @Column(name = "reset_token")
    private String resetToken; // Token đặt lại mật khẩu

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false; // Kiểm tra xác thực email
}
