package com.cg.model.dto;

public class ProductUpdateReqDTO {

    private Long id;

    private String title;
    private String price;
    private String quantity;
    private String description;

    private BrandDTO brandDTO;
    private ProductAvatarDTO productAvatarDTO;

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(toProductDTO().getQuantity())
                .setDescription(description)
                .setBrandDTO(brandDTO)
                .setProductAvatarDTO(productAvatarDTO);
    }

}
