package com.ecomm.shopping.eShop.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerOrderDto {
    private UUID uuid;

    private String date;

    private Float total;

    private Float tax;

    private Float gross;

    private Float offPrice;

    private Float pricePaid;

    private String orderStatus;

    private String orderType;
}
