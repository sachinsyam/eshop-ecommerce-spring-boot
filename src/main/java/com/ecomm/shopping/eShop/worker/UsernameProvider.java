package com.ecomm.shopping.eShop.worker;

import com.ecomm.shopping.eShop.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UsernameProvider {
    @Autowired
    UserInfoService userInfoService;
    public UUID get() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userInfoService.findByUsername(username).getUuid();
    }

}
