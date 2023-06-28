package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query(value = "SELECT * FROM role WHERE role_name = :name", nativeQuery = true)
    Optional<Role> findRoleByName(@Param("name") String name);
}
