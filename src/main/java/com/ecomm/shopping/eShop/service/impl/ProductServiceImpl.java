package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.*;
import com.ecomm.shopping.eShop.repository.ProductRepository;
import com.ecomm.shopping.eShop.service.CategoryService;
import com.ecomm.shopping.eShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    //TODO; change injection to interfaces
    @Autowired
    ImageServiceImpl imageService;
    @Autowired
    ProductRepository productRepository;

    private final VariantServiceImpl variantService;
    public ProductServiceImpl(@Lazy VariantServiceImpl variantService) {
        this.variantService = variantService;
    }
    @Autowired
    BannerServiceImpl bannerService;
    @Autowired
    CategoryService categoryService;




    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAllByEnabledTrue(pageable);
    }

    @Override
    public Product getProduct(UUID uuid) {
        return productRepository.findById(uuid).orElse(null);
    }


    //cannot delete a product because it has dependencies;
    @Override
    public void delete(UUID uuid) {
        //deleting dependencies of the product first
        List<Image> images = imageService.findImagesByProductId(uuid);
        if(!images.isEmpty()){
            for (Image image : images) {
                imageService.delete(image.getUuid());
            }
        }
        List<Variant> variants = variantService.findVariantsByProductId(uuid);
        if(!variants.isEmpty()){
            for (Variant variant : variants) {
                variantService.delete(variant.getUuid());
            }
        }

        List<Banner> banners = bannerService.findBannersByProductId(uuid);
        if(!banners.isEmpty()){
            for (Banner banner : banners) {
                bannerService.delete(banner);
            }
        }

        productRepository.deleteById(uuid);
    }

    @Override
    public List<Product> findByNameLike(String key) {
        return productRepository.findByNameLike(key);
    }

    @Override
    public Page<Product> findByNameLike(String key, Pageable pageable) {
        return productRepository.findByNameContainingAndEnabledIsTrue(key, pageable);
    }

    @Override
    public Page<Product> findByCategory(String filter, Pageable pageable) {
        try {
            UUID uuid = UUID.fromString(filter); // Check if the string is a valid UUID
            return productRepository.findByCategory(categoryService.getCategory(uuid), pageable);
        } catch (IllegalArgumentException e) {
            // Handle the case when the string is not a valid UUID
            // You can log an error, throw an exception, or return an empty page, etc.
            return Page.empty();
        }
    }

    @Override
    public Page<Product> findAllPaged(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByNameLikePaged(String keyword, Pageable pageable) {
        return productRepository.findByNameLike("%"+keyword+"%", pageable);
    }

    @Override
    public List<Product> findAllEnabled() {
        return productRepository.findAllByEnabledTrue();
    }
}
