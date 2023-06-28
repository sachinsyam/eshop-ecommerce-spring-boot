package com.ecomm.shopping.eShop.entity.user;

import com.ecomm.shopping.eShop.entity.baseEntity.BaseEntity;
import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.product.ProductReview;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//to prevent stack overflow, exclude cartItems
/*
The stack overflow error is occurring due to a bidirectional relationship
between the UserInfo and Cart entities. The UserInfo entity has a OneToMany
relationship with Cart, and the Cart entity has a ManyToOne relationship with UserInfo.
This circular relationship causes an infinite loop when generating the toString method.

To fix this issue, you can exclude CartItems
 */
@ToString
public class UserInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;
    @Column(unique=true)

    @NotNull(message = "username is empty")
    private String username;
    @NotNull(message = "First name is empty")
    private String firstName;
    private String lastName;
    @NotNull(message = "password is empty")
    private String password;

    @Column(unique = true)
    @Email(message = "invalid email")
    private String email;

    @Column(unique = true)
    private String phone;
    private boolean enabled = true;

    //Verifying all users by default because twilio only sends otp to my phone.
    private boolean verified = true;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    //relationships
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo")
    @ToString.Exclude
    private List<UserAddress> savedAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo")
    @ToString.Exclude
    private List<Cart> cartItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="coupon_id")
    private Coupon coupon;

    @OneToMany(mappedBy = "userInfo")
    @ToString.Exclude
    private List<OrderHistory> orderHistories = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo")
    @ToString.Exclude
    private List<Wishlist> wishlist = new ArrayList<>();

    @OneToOne(mappedBy = "userInfo")
    private Wallet wallet;

    @OneToMany(mappedBy = "userInfo")
    @ToString.Exclude
    private List<WalletHistory> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo")
    @ToString.Exclude
    private List<ProductReview> reviews;

    //overriding hashCode to remove circular dependency and stack overflow error
    @Override
    public int hashCode() {
        // Exclude fields contributing to the circular reference
        return Objects.hash(uuid,username);
    }
    public String getName(){
        return firstName + " " +lastName;
    }


}
