package com.ecomm.shopping.eShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NewOrderDto {
    UUID addressId;
    String coupon;
    String paymentMethod;
    Float offPrice;
    String generatedOrderUuid;
}
