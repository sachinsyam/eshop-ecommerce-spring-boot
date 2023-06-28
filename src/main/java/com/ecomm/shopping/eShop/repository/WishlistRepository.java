package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.Wishlist;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {

    List<Wishlist> findByUserInfo(UserInfo user, Sort sort);

    List<Wishlist> findByUserInfo(UserInfo user);

    @Modifying
    @Query(value = "DELETE FROM wishlist WHERE user_id = :userId AND variant_id = :variantId", nativeQuery = true)
    void deleteByUserInfoAndVariant(String userId, String variantId);

}
