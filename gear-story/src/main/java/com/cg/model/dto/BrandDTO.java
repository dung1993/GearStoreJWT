package com.cg.model.dto;


import com.cg.model.Brand;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BrandDTO implements Validator {

    private Long idBrand;
    private String brandName;

    private ProductDTO productDTO;

    public Brand toBrand(){
        return new Brand()
                .setIdBrand(idBrand)
                .setBrandName(brandName);
//                .setProduct(productDTO.toProduct());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BrandDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BrandDTO brandDTO = (BrandDTO) target;
        String brandName = brandDTO.brandName;
        if (brandName.length() == 0){
            errors.rejectValue("brandName", "brandName.null", "Brand Name is required");
        }
        else {
            if (brandName.length() <= 4 || brandName.length() >= 60){
                errors.rejectValue("brandName", "brandName.length", "Brand Name is accept between 4 and 60 characters");
            }
        }

    }
}
