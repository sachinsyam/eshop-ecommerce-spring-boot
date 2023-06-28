package com.ecomm.shopping.eShop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponValidityResponseDto {
    boolean valid = false;
    double priceOff = 0;
    double cartTotal = 0;
    double cartSaved = 0;
    String coupon="";

}
