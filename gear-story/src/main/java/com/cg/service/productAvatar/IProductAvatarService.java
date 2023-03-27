package com.cg.service.productAvatar;

import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IProductAvatarService extends IGeneralService<ProductAvatar> {
    Optional<ProductAvatar> findByProduct(Product product);
}
