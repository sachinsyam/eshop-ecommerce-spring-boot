package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.Wishlist;
import com.ecomm.shopping.eShop.repository.WishlistRepository;
import com.ecomm.shopping.eShop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    @Override
    public List<Wishlist> findByUser(UserInfo user) {
        Sort sort = Sort.by(Sort.Direction.DESC,"savedTime");
        return wishlistRepository.findByUserInfo(user,sort);
    }

    @Override
    public boolean isWishlisted(Variant variant, UserInfo user) {
        List<Wishlist> wishlist = wishlistRepository.findByUserInfo(user);
        for(Wishlist item : wishlist){
            if(item.getVariant().equals(variant)){
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean save(Wishlist wishlist, UserInfo user, Variant variant) {
        if(isWishlisted(variant, user)){
            wishlistRepository.deleteByUserInfoAndVariant(user.getUuid().toString(), variant.getUuid().toString());
            return false;
        }else{
            wishlist.setSavedTime(new Date());
            wishlistRepository.save(wishlist);
            return true;
        }

    }

    @Override
    public void delete(UUID uuid) {
        wishlistRepository.deleteById(uuid);
    }

    @Override
    public Wishlist findById(UUID uuid) {
        return wishlistRepository.findById(uuid).orElse(null);
    }


}
