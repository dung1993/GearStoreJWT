package com.cg.service.product;


import com.cg.model.Brand;
import com.cg.model.Product;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.repository.BrandRepository;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public List<Product> findALl() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductCreateDTO> findAllProductDTO() {
        return productRepository.findAllProductDTO();
    }

    @Override
    public List<Product> findAllByDeletedIsFalse() {
        return productRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Product> findAllByIdNot(Long id) {
        return productRepository.findAllByIdNot(id);
    }

    @Override
    public List<Product> findAllByIdNotAndDeletedIsFalse(Long id) {
        return productRepository.findAllByIdNotAndDeletedIsFalse(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {

        Brand brand = product.getBrand();
        brandRepository.save(brand);
        product.setBrand(brand);

        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Product product) {

    }


    @Override
    public Optional<Product> findByEmail(String email) {
        return productRepository.findByEmail(email);
    }

    @Override
    public Optional<Product> findByEmailAndIdIsNot(String email, Long id) {
        return productRepository.findByEmailAndIdIsNot(email, id);
    }
}
