package com.cg.model;

import com.cg.model.dto.BrandDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "brands")
@Accessors(chain = true)
public class Brand extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBrand;
    private String brandName;


    public BrandDTO toBrandDTO(){
        return new BrandDTO()
                .setIdBrand(idBrand)
                .setBrandName(brandName);
//                .setProductDTO(product.toProductDTO());
    }


}
