package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.entity.product.ProductReview;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.ProductReviewRepository;
import com.ecomm.shopping.eShop.service.ProductReviewService;
import com.ecomm.shopping.eShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {
    @Autowired
    ProductReviewRepository productReviewRepository;
    @Autowired
    ProductService productService;


    @Override
    public void save(ProductReview productReview) {
        productReviewRepository.save(productReview);
        //save the avg. rating to product table
        List<ProductReview> productReviews = findByProduct(productReview.getProduct());

        double averageRating = productReviews
                .stream()
                .mapToInt(ProductReview::getRating)
                .average()
                .orElse(0);
        System.out.println("Calculated Average rating:"+averageRating);

        Product product = productService.getProduct(productReview.getProduct().getUuid());
        product.setAverageRating((int) averageRating);
        product = productService.save(product);

        System.out.println(product.getName()+" saved with average rating:"+product.getAverageRating());

    }

    @Override
    public List<ProductReview> findByProductAndUserInfo(Product product, UserInfo userInfo) {
        return productReviewRepository.findByProductAndUserInfo(product, userInfo);
    }

    @Override
    public List<ProductReview> findByProduct(Product selectedProduct) {
        return productReviewRepository.findByProduct(selectedProduct);
    }
}
