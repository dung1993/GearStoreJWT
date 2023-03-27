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
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCreateReqDTO implements Validator {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;
    private BrandDTO brandDTO;

    private MultipartFile file;

    public Product toProduct(ProductAvatar productAvatar) {
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setBrand(brandDTO.toBrand())
                .setProductAvatar(productAvatar)
                ;
    }

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(toProductDTO().getPrice())
                .setQuantity(quantity)
                .setBrandDTO(brandDTO);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
