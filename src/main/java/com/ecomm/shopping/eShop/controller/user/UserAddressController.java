package com.ecomm.shopping.eShop.controller.user;

import com.ecomm.shopping.eShop.entity.user.UserAddress;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.service.UserAddressService;
import com.ecomm.shopping.eShop.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/address")
public class UserAddressController {
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    UserInfoService userInfoService;


    @PostMapping("/save")
    public String save(@ModelAttribute UserAddress userAddress) {
        UserInfo user = userInfoService.findByUsername(getCurrentUsername());
        userAddress.setUserInfo(user);

        List<UserAddress> userAddressList = userAddressService.findByUser(user);

        if (userAddressList.isEmpty()) {
            // Saving first address
            userAddress.setDefaultAddress(true);
            userAddressService.save(userAddress);
            return "redirect:/profile";
        }else {
            if(userAddress.isDefaultAddress()){
                UserAddress existingDefaultAddress = userAddressList.stream()
                        .filter(UserAddress::isDefaultAddress)
                        .findFirst()
                        .orElse(null);

                if (existingDefaultAddress != null) {
                    existingDefaultAddress.setDefaultAddress(false);
                    //remove default from previous default address
                    userAddressService.save(existingDefaultAddress);
                }
                //save new address(default)
                userAddressService.save(userAddress);
            }
        }

        return "redirect:/profile";
    }


    @GetMapping("/delete/{uuid}")
    public String delete(@PathVariable UUID uuid){


        //userAddressService.deleteById(uuid);
        userAddressService.disableAddress(uuid);
        return "redirect:/profile";
    }

    //getting current logged in username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }



}
