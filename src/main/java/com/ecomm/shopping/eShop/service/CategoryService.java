package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {

     Category getCategory(UUID uuid);

     List<Category> findAll();

     void addCategory(Category category);

    void delete(UUID uuid);

    void updateCategory(Category category);

    void save(Category category);
}
