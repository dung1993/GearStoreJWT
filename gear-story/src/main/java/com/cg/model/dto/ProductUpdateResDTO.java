package com.cg.model.dto;

import com.cg.model.Brand;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductUpdateResDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;

    private BrandDTO brand;

    private ProductAvatarDTO productAvatar;

    public ProductUpdateResDTO(Product product, Brand brand, ProductAvatarDTO productAvatarDTO){
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.brand = brand.toBrandDTO();
        this.productAvatar = productAvatarDTO;
    }
}
