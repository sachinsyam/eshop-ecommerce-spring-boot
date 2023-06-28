package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Image;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {
    Image save(Image imageEntity);
    List<Image> findImagesByProductId(UUID uuid);

    void delete(UUID uuid);
    Image findFileNameById(UUID uuid);

    void deleteUnusedImages();
}
