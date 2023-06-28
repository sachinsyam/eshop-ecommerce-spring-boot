package com.ecomm.shopping.eShop.controller.user;

import com.ecomm.shopping.eShop.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class UserAddressRestController {

    @Autowired
    UserAddressService userAddressService;

//    @PostMapping("/save")
//    public String save(@ModelAttribute UserAddress userAddress){
//        userAddressService.save(userAddress);
//        return "Saved";
//    }


}
