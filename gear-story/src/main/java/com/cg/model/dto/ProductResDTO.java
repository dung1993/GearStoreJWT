package com.cg.model.dto;

import com.cg.model.Brand;
import com.cg.model.ProductAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductResDTO {

    private Long id;
    private String title;
    private String price;
    private Long quantity;
    private String description;

    private BrandDTO brand;

    private String avatarId;
    private String fileFolder;
    private String fileName;
    private String fileUrl;

    public ProductResDTO(Long id, String title, BigDecimal price, Long quantity, String description, Brand brand, String avatarId, String fileFolder, String fileName, String fileUrl) {
        this.id = id;
        this.title = title;
        this.price = String.valueOf(price);
        this.quantity = quantity;
        this.description = description;
        this.brand = brand.toBrandDTO();
        this.avatarId = avatarId;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public ProductResDTO(Long id, String title, String price, Long quantity, String description, String avatarId, String fileFolder, String fileName, String fileUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.avatarId = avatarId;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public ProductDTO toProductDTO(ProductAvatarDTO productAvatarDTO){
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setBrandDTO(brand.toBrand().toBrandDTO())
                .setProductAvatarDTO(productAvatarDTO);
    }


}
