package com.ecomm.shopping.eShop.entity.product;

import com.ecomm.shopping.eShop.entity.baseEntity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Banner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String bannerFile;

    private String description1;

    private String description2;

    private String description3;


    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    private boolean enabled=true;
}
