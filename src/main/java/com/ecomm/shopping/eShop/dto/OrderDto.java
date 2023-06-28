package com.ecomm.shopping.eShop.dto;


import com.ecomm.shopping.eShop.enums.OrderStatus;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private UUID uuid;

    private OrderStatus orderStatus;


}
