package com.ecomm.shopping.eShop.entity.product;

import com.ecomm.shopping.eShop.entity.baseEntity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String name;

    @Lob
    private String description;

    private boolean enabled=true;

    private int averageRating = 0;

    //relationships
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "productId")
    @ToString.Exclude
    private List<Variant> variants;

    @OneToMany(mappedBy = "product_id")
    @ToString.Exclude
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> reviews;

    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    public Float averagePrice() {
        final float[] sum = {0F};
        int count = this.variants.size();

        this.variants.forEach(variant -> {
            sum[0] += variant.getSellingPrice();
        });

        if (count > 0) {
            return sum[0] / count;
        } else {
            return 0F;
        }
    }

    public Double getRating(){
        return this.reviews
                .stream()
                .mapToInt(ProductReview::getRating)
                .average()
                .orElse(0);
    }

    public boolean isAllVariantsDisabled(){
        return variants.stream().anyMatch(Variant::isEnabled);
    }

}
