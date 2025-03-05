package com.example.demo.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceDTO {
    private String serviceName;
    private String serviceDetail;
    private Integer serviceQuantity;
    private BigDecimal servicePrice;
    private String categoryId;
}