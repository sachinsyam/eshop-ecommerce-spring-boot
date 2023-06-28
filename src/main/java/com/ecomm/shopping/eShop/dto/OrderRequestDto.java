package com.ecomm.shopping.eShop.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    private Float amount;
    private String currency;
    private String receipt;
    private UUID address;
    private String coupon;


}
