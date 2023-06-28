package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.entity.product.ProductReview;
import com.ecomm.shopping.eShop.entity.user.UserInfo;

import java.util.List;

public interface ProductReviewService {
    void save(ProductReview productReview);

    List<ProductReview> findByProductAndUserInfo(Product product, UserInfo userInfo);

    List<ProductReview> findByProduct(Product selectedProduct);
}
