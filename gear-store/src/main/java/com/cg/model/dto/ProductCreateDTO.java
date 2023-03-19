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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCreateDTO implements Validator {


    private long id;

    private String title;

    private String price;
    private String quantity;
    private String description;
    private Brand brand;
    private ProductAvatar productAvatar;

    public ProductCreateDTO(long id,
                            String title,
                            BigDecimal price,
                            Long quantity,
                            String description,
                            Brand brand,
                            ProductAvatar productAvatar){
                            this.id = id;
                            this.title = title;
                            this.price = price.toString();
                            this.quantity = quantity.toString();
                            this.description = description;
                            this.brand = brand;
                            this.productAvatar = productAvatar;
    }

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(new BigDecimal(Long.parseLong(price)))
                .setQuantity(Long.parseLong(quantity))
                .setBrand(brand);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreateDTO productDTO = (ProductCreateDTO) target;
        String title = productDTO.title;
        String price = productDTO.price;

        if (title.length() == 0) {
            errors.rejectValue("title", "title.null", "Title is required");
        }
        else {
            if (title.length() < 4 || title.length() > 60) {
                errors.rejectValue("title", "title.length", "Title is accept between 4 and 60 characters");
            }
        }

        if (price.length() == 0){
            errors.rejectValue("price", "price.null", "Price is required");
        }
        else {
            if (!price.matches("(^$|[0-9]*$)") ){
                errors.rejectValue("price", "price.null", "Price is valid ");
            }
        }

        if (quantity.length() == 0){
            errors.rejectValue("quantity", "quantity.null", "Quantity is required");
        }
        else {
            if (!quantity.matches("(^$|[0-9]*$)") ){
                errors.rejectValue("quantity", "quantity.null", "Quantity is valid ");
            }
        }
    }
}
