package com.ecomm.shopping.eShop.controller.user;

import com.ecomm.shopping.eShop.dto.WalletDto;
import com.ecomm.shopping.eShop.entity.user.Wallet;
import com.ecomm.shopping.eShop.enums.WalletTransactionType;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.service.WalletHistoryService;
import com.ecomm.shopping.eShop.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    WalletHistoryService walletHistoryService;

    @PostMapping("/credit")
    public ResponseEntity<String> credit(@RequestBody WalletDto walletDto){
        if (walletDto.getAmount() < 1){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid amount");
        }


        Wallet wallet = walletService.findByUserInfo(userInfoService.getUser(walletDto.getUuid()));

        //credit amount
        wallet.setBalance(wallet.getBalance() + walletDto.getAmount());
        walletService.save(wallet);

        //Save history
        walletHistoryService.transfer(WalletTransactionType.CREDITED, wallet.getUserInfo(), walletDto.getAmount());

        System.out.println(walletDto.getAmount()+" credited to the wallet of "+wallet.getUserInfo().getUsername());
        return ResponseEntity.ok().body("{\"message\": \"Wallet balance updated successfully.\"}");

    }

    @PostMapping("/debit")
    public ResponseEntity<String> debit(@RequestBody WalletDto walletDto){
        if (walletDto.getAmount() < 1){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid amount");
        }
        Wallet wallet = walletService.findByUserInfo(userInfoService.getUser(walletDto.getUuid()));

        //debit amount
        wallet.setBalance(wallet.getBalance() - walletDto.getAmount());
        walletService.save(wallet);

        //Save history
        walletHistoryService.transfer(WalletTransactionType.DEBITED, wallet.getUserInfo(), walletDto.getAmount());

        System.out.println(walletDto.getAmount()+" debited from the wallet of "+wallet.getUserInfo().getUsername());
        return ResponseEntity.ok().body("{\"message\": \"Wallet balance updated successfully.\"}");

    }


}
