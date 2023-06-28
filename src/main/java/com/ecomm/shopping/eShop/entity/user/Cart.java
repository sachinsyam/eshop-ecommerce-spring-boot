package com.ecomm.shopping.eShop.entity.user;

import com.ecomm.shopping.eShop.entity.baseEntity.BaseEntity;
import com.ecomm.shopping.eShop.entity.product.Variant;
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
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    int quantity;

    @ManyToOne
    @JoinColumn(name="variant_id")
    private Variant variant;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserInfo userInfo;


}