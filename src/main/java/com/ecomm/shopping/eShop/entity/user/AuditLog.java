package com.ecomm.shopping.eShop.entity.user;

import com.ecomm.shopping.eShop.entity.baseEntity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @Column(name = "login_timestamp")
    private LocalDateTime loginTimestamp;

    @Column(name = "ip_address")
    private String ipAddress;

    private String sessionId;

    private String city;

    private String country;

    private String network;

    private String asn;

    private String organization;
}
