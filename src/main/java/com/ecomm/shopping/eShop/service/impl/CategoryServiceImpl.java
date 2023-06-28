package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.repository.CategoryRepository;
import com.ecomm.shopping.eShop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(UUID uuid) {
        categoryRepository.deleteById(uuid);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> findAll() {
       return categoryRepository.findAll();
    }

    public Category getCategory(UUID uuid) {
        return categoryRepository.findById(uuid).orElse(null);
    }
}
