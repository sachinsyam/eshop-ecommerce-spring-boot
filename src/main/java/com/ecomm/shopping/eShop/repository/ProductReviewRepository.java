package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.entity.product.ProductReview;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {

    List<ProductReview> findByProductAndUserInfo(Product product, UserInfo userInfo);

    List<ProductReview> findByProduct(Product selectedProduct);
}
