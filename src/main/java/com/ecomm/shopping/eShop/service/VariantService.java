package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface VariantService {
    List<Variant> findAll();

    Variant save(Variant variant);

    Variant findById(UUID uuid);

    void delete(UUID uuid);

    Variant update(Variant variant);
    List<Variant> findVariantsByProductId(UUID uuid);

    Page<Variant> findByProductPaged(String filter, Pageable pageable);

    Page<Variant> findByNameLike(String keyword, Pageable pageable);

    Page<Variant> findAllPaged(Pageable pageable);
}
