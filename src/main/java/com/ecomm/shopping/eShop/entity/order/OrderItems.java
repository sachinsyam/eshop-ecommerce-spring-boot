package com.ecomm.shopping.eShop.entity.order;

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
public class OrderItems extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private int quantity;

    private float orderPrice;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderHistory orderHistory;

    public String getItemName(){
        return this.getVariant().getProductId().getName() + " " + this.getVariant().getName();
    }
    public Float getTotal(){
        return this.quantity * this.orderPrice;
    }
    public Float getProfitPerItem(){
        return orderPrice - variant.getWholesalePrice();
    }
}
