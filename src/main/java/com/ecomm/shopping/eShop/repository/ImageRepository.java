package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {


    @Query(value = "SELECT * FROM image WHERE product_id = :uuid", nativeQuery = true)
    List<Image> findByProductId(@Param("uuid") UUID uuid);

}
