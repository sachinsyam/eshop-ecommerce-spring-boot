package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    List<AuditLog> findBySessionId(String sessionId);
}
