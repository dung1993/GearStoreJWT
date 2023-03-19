package com.cg.service.product;


import com.cg.model.Product;

import com.cg.model.dto.ProductCreateDTO;
import com.cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product> {

    List<ProductCreateDTO> findAllProductDTO();

    List<Product> findAllByDeletedIsFalse();

    List<Product> findAllByIdNot(Long id);

    List<Product> findAllByIdNotAndDeletedIsFalse(Long id);

    Optional<Product> findByEmail(String email);

    Optional<Product> findByEmailAndIdIsNot(String email, Long id);
}
