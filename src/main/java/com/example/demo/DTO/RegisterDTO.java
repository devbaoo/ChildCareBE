package com.example.demo.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String username;
    private String userpass;
    private String firstname;
    private String lastname;
    private int age;
    private String email;
    private String phoneNumber;
    private String address;
}