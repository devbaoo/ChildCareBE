package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    String username;
    String userpass;
    String role;
    String firstname;
    String lastname;
    int age;
    String email;
    String phoneNumber;
    String status;
    String resetToken;
    String specialization;
}
