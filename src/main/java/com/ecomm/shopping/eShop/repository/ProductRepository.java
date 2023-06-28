package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByNameLike(String key);
    Page<Product> findByNameLike(String key, Pageable pageable);

   // @Query(value = "SELECT * FROM product WHERE name LIKE  :key% and enabled=true;", nativeQuery = true)
    Page<Product> findByNameContainingAndEnabledIsTrue(String key, Pageable pageable);

    Page<Product> findAllByEnabledTrue(Pageable pageable);

    List<Product> findAllByEnabledTrue();

    Page<Product> findByCategory(Category category, Pageable pageable);
}
