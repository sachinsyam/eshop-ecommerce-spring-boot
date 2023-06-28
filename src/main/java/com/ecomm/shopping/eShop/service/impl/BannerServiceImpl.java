package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.Banner;
import com.ecomm.shopping.eShop.repository.BannerRepository;
import com.ecomm.shopping.eShop.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerRepository bannerRepository;

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner getBanner(UUID uuid) {
        return bannerRepository.findById(uuid).orElse(null);
    }

    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public void delete(Banner banner) {
         bannerRepository.delete(banner);
    }

    @Override
    public List<Banner> findBannersByProductId(UUID uuid) {
       return bannerRepository.findBannersByProductId(uuid);
    }

    @Override
    public Banner findById(UUID uuid) {
        return bannerRepository.findById(uuid).orElse(null);
    }
}
