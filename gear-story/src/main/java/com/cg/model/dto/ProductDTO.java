package com.cg.model.dto;


import com.cg.model.Product;
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
public class ProductDTO implements Validator {

    private Long id;
    private String title;
    private String price;
    private Long quantity;
    private String description;

    private BrandDTO brandDTO;

    private ProductAvatarDTO productAvatarDTO;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDTO productDTO = (ProductDTO) target;
        String title = productDTO.getTitle();
        String price = productDTO.getPrice();

        if(title.length() == 0){
            errors.rejectValue("title", "title.null", "Title is required");
        }
        if (!price.matches("^[0-9]{3,11}$")){
            errors.rejectValue("price", "price.error", "Please input number, min 3 number and max 11 number ");
        }
    }

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(toProduct().getPrice())
                .setQuantity(quantity)
                .setDescription(description)
                .setBrand(brandDTO.toBrand())
                .setProductAvatar(productAvatarDTO.toProductAvatar());

    }
}
