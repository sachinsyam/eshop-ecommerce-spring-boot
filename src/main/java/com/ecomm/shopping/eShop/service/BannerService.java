package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface BannerService {
    List<Banner> findAll();

    Banner getBanner(UUID uuid);

    Banner save(Banner banner);

    void delete(Banner banner);

    List<Banner> findBannersByProductId(UUID uuid);

    Banner findById(UUID uuid);
}
