package com.ecomm.shopping.eShop.dto;

import com.ecomm.shopping.eShop.enums.WalletTransactionType;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WalletDto {
    float amount;
    WalletTransactionType walletTransactionType;
    UUID uuid;
}
