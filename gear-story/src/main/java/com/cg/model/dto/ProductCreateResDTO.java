package com.cg.model.dto;

import com.cg.model.Brand;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCreateResDTO implements Validator {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;

    private BrandDTO brandDTO;

    private ProductAvatarDTO avatar;


    public ProductCreateResDTO(Product product, Brand brand, ProductAvatarDTO productAvatarDTO) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.brandDTO = brand.toBrandDTO();
        this.avatar = productAvatarDTO;
    }



    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    public Product toProduct() {
        return new Product()
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setBrand(brandDTO.toBrand())
                .setProductAvatar(avatar.toProductAvatar());
    }
}
