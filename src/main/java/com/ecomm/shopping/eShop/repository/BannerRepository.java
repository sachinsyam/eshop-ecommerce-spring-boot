package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.product.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BannerRepository extends JpaRepository<Banner, UUID> {
    @Query(value = "SELECT * FROM banner WHERE product_id = :uuid", nativeQuery = true)
    List<Banner> findBannersByProductId(@Param("uuid") UUID uuid);
}
