package com.ecomm.shopping.eShop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RazorpayDto {
    String key;
    String currency;
    String order_id;
    String name;
    String description;

}
