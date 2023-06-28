package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface  ProductService {
    Product save(Product product);

    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);


    Product getProduct(UUID uuid);

    void delete(UUID uuid);


    List<Product> findByNameLike(String key);

    Page<Product> findByNameLike(String key, Pageable pageable);

    Page<Product> findByCategory(String filter, Pageable pageable);

    Page<Product> findAllPaged(Pageable pageable);

    Page<Product> findByNameLikePaged(String keyword, Pageable pageable);

    List<Product> findAllEnabled();
}
