package com.ecomm.shopping.eShop.entity.order;

import com.ecomm.shopping.eShop.entity.baseEntity.BaseEntity;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.user.UserAddress;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderHistory extends BaseEntity { //Order is a reserved keyword
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private Float total=0F;

    private Float tax=0F;

    @Transient
    private Float gross;

    private Float offPrice=0F;


    private OrderStatus orderStatus = OrderStatus.UNKNOWN;

    private OrderType orderType=OrderType.UNKNOWN;

    //relationship

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserInfo userInfo;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItems> items = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name="coupon_id")
    @ToString.Exclude
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name="address_id")
    @ToString.Exclude
    private UserAddress userAddress;

    @OneToOne(mappedBy = "orderHistory")
    @ToString.Exclude
    private OnlineOrderRef onlineOrderRef;



    //custom getters
    public Float getGross() {
     return total + tax;
    }

    public Long getGrossLong() {
        if (offPrice != null) {
            return (long) Math.round(total + tax);
        } else if (total == null) {
            return 0L;
        } else {
            return (long) Math.round(total + tax);
        }
    }


    public Float getOff(){
        if(offPrice == null){
            return 0F;
        }else{
            return offPrice;
        }
    }


}
