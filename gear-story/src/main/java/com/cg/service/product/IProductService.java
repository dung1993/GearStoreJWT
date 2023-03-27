package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductCreateResDTO;
import com.cg.model.dto.ProductResDTO;
import com.cg.model.dto.ProductUpdateResDTO;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product> {

    List<ProductResDTO> findAllProductResDTO();

    List<ProductResDTO> findAllByDeletedIsFalse();

    Product getById(Long id);

    ProductCreateResDTO createWithAvatar(Product product, MultipartFile avatarFile);

    ProductUpdateResDTO update(Product product);

    ProductUpdateResDTO updateWithAvatar(Product product, MultipartFile avatarFile) throws IOException;

    void remove(Long id);

//    List<Product> findAllByProductTitleLike(String title);

    Optional<ProductResDTO> findAllProductResDTOById(Long productId);
}
