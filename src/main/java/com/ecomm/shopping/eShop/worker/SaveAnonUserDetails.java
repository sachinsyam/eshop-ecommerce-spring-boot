package com.ecomm.shopping.eShop.worker;

import com.ecomm.shopping.eShop.entity.user.AuditLog;
import com.ecomm.shopping.eShop.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class SaveAnonUserDetails {
    @Autowired
    AuditLogRepository auditLogRepository;
    @Autowired
    GetLocation getLocation;
    public boolean save(String sessionId, String clientIP) throws IOException {
        List<AuditLog> auditLogs = auditLogRepository.findBySessionId(sessionId);
        if (auditLogs.isEmpty()) {
            System.out.println("Saving new IP details to audit log "+LocalDateTime.now().toLocalTime());
            AuditLog auditLog = new AuditLog();
            auditLog.setSessionId(sessionId);
            auditLog.setLoginTimestamp(LocalDateTime.now());
            auditLog.setIpAddress(clientIP);
            Map<String,String> map = getLocation.getCity(clientIP);

            auditLog.setCity(map.get("city"));
            auditLog.setCountry(map.get("country"));

            auditLog.setAsn(map.get("asn"));
            auditLog.setOrganization(map.get("organization"));
            auditLog.setNetwork(map.get("network"));

            auditLogRepository.save(auditLog);
          /*uncomment the line below if you want to update city and country for all audit log items
            the app wont run with this code enabled. comment it after populating audit with details.
                getLocation.setLocationDetails();
           */
            return true;
        }else{
            return false;
        }
    }
}
