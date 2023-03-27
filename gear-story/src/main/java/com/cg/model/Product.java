package com.cg.model;


import com.cg.model.dto.ProductCreateResDTO;
import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "products")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(precision = 10, scale = 0, nullable = false, updatable = false)
    private BigDecimal price;
    private Long quantity;
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_brand_id", referencedColumnName = "idBrand", nullable = false)
    private Brand brand;

    @OneToOne
    private ProductAvatar productAvatar;

    public ProductCreateResDTO toProductCreateResDTO() {
        return new ProductCreateResDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setBrandDTO(brand.toBrandDTO())
                .setAvatar(productAvatar.toProductAvatarDTO())
                ;
    }

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(toProductDTO().getPrice())
                .setQuantity(quantity)
                .setDescription(description)
                .setBrandDTO(brand.toBrandDTO())
                .setProductAvatarDTO(productAvatar.toProductAvatarDTO());
    }


}
