package com.ecomm.shopping.eShop.worker;

import com.ecomm.shopping.eShop.entity.user.AuditLog;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.AuditLogRepository;
import com.ecomm.shopping.eShop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String ipAddress = request.getRemoteAddr(); // Retrieve the IP address

        // Create an audit log entry
        AuditLog auditLog = new AuditLog();
        UserInfo user = userInfoRepository.findByUsername(authentication.getName()).orElse(null);
        auditLog.setUser(user);
        auditLog.setLoginTimestamp(LocalDateTime.now());
        auditLog.setIpAddress(ipAddress);
        auditLog.setSessionId(request.getSession().getId());
        auditLogRepository.save(auditLog);

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/dashboard/admin");
            } else {
                response.sendRedirect("/");
            }
        }
    }
}
