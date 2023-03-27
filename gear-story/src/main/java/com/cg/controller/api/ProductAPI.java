package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.model.dto.*;
import com.cg.service.product.IProductService;
import com.cg.service.productAvatar.IProductAvatarService;
import com.cg.service.upload.UploadServiceImpl;
import com.cg.utils.AppUtils;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IProductAvatarService productAvatarService;

    @Autowired
    private UploadServiceImpl uploadService;

    @Autowired
    private UploadUtils uploadUtils;


    @GetMapping
    public ResponseEntity<?> getAll() {

        List<ProductResDTO> productResDTOS = productService.findAllByDeletedIsFalse();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (ProductResDTO item : productResDTOS) {
            ProductAvatarDTO productAvatarDTO = new ProductAvatarDTO();
            productAvatarDTO.setId(item.getAvatarId());
            productAvatarDTO.setFileFolder(item.getFileFolder());
            productAvatarDTO.setFileName(item.getFileName());
            productAvatarDTO.setFileUrl(item.getFileUrl());
            ProductDTO productDTO = item.toProductDTO(productAvatarDTO);
            productDTOS.add(productDTO);
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId){

        Optional<ProductResDTO> productResDTOOptional = productService.findAllProductResDTOById(productId);
        if (!productResDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Product not valid");
        }

        ProductAvatarDTO productAvatarDTO = new ProductAvatarDTO();
        productAvatarDTO.setId(productResDTOOptional.get().getAvatarId());
        productAvatarDTO.setFileFolder(productResDTOOptional.get().getFileFolder());
        productAvatarDTO.setFileName(productResDTOOptional.get().getFileName());
        productAvatarDTO.setFileUrl(productResDTOOptional.get().getFileUrl());

        ProductDTO productDTO = productResDTOOptional.get().toProductDTO(productAvatarDTO);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

//    @GetMapping("/{productId}")
//    public ResponseEntity<?> showUpdate(@PathVariable Long productId, @Validated @RequestBody ProductCreateReqDTO productCreateReqDTO, BindingResult bindingResult){
//        Optional<Product> productOptional = productService.findById(productId);
//
//        if (!productOptional.isPresent()){
//            throw new ResourceNotFoundException("Product not found");
//        }
//        new ProductCreateResDTO().validate(productCreateReqDTO,bindingResult);
//
//        if (bindingResult.hasFieldErrors()){
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//
//        ProductAvatar productAvatar = new ProductAvatar();
//        Product product = productCreateReqDTO.toProduct(productAvatar);
//        product.setId(productId);
//        productService.save(product);
//
//        return new ResponseEntity<>(product.toProductCreateResDTO(), HttpStatus.OK);
//    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        Optional<Product> customerOptional = productService.findById(productId);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer not found");
        }

        new ProductDTO().validate(productDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Product product = productDTO.toProduct();
        product.setId(productId);
        productService.save(product);

        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }



    @PatchMapping("/update-with-avatar/{productId}")
    public ResponseEntity<?> updateWithAvatar(@PathVariable Long productId, MultipartFile file, ProductUpdateReqDTO productUpdateReqDTO, BindingResult bindingResult) throws IOException{
        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()){
            throw new ResourceNotFoundException("Product not found");
        }

        if (file == null){
            ProductDTO productDTO = productUpdateReqDTO.toProductDTO();
            Product product = productDTO.toProduct();
            product.setId(productId);

            ProductUpdateResDTO productUpdateResDTO = productService.update(product);
            return new ResponseEntity<>(productUpdateResDTO,HttpStatus.OK);
        }
        else {

            ProductDTO productDTO = productUpdateReqDTO.toProductDTO();
            Product product = productDTO.toProduct();
            product.setId(productId);

            ProductUpdateResDTO productUpdateResDTO = productService.updateWithAvatar(product, file);
            return new ResponseEntity<>(productUpdateResDTO,HttpStatus.OK);
        }
    }

    @PostMapping("/create-with-avatar")
    public ResponseEntity<?> createWithAvatar(ProductCreateReqDTO productCreateReqDTO) {

        MultipartFile file = productCreateReqDTO.getFile();

        ProductDTO productDTO = productCreateReqDTO.toProductDTO();

        if (file != null){
            Product product = productDTO.toProduct();
            ProductCreateResDTO productCreateResDTO = productService.createWithAvatar(product, file);

            return new ResponseEntity<>(productCreateResDTO, HttpStatus.CREATED);
        }
        else {
            productDTO.setId(null);
            productDTO.getBrandDTO().setIdBrand(null);

            Product product = productDTO.toProduct();
            product = productService.save(product);

            productDTO = product.toProductDTO();

            return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete-avatar/{productId}")
    public ResponseEntity<?> deleteAvatar(@PathVariable Long productId) throws Exception{

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()){
            throw new ResourceNotFoundException("Product invalid");
        }

        Optional<ProductAvatar> productAvatarOptional = productAvatarService.findByProduct(productOptional.get());
        String publicId = productAvatarOptional.get().getCloudId();

        uploadService.destroyImage(publicId, uploadUtils.buildImageUploadParams(productAvatarOptional.get()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> doDelete(@PathVariable Long productId) {

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new DataInputException("Customer not valid");
        }

        Product product = productOptional.get();
        product.setDeleted(true);
        productService.save(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}