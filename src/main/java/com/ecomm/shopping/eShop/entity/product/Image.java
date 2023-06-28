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
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String fileName;

    //relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product_id;

    public Image(String fileName, Product product) {
        this.fileName = fileName;
        this.product_id = product;
    }


    @Override
    public String toString() {
        return "Image{" +
                "uuid=" + uuid +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
