package com.cg.service.product;


import com.cg.exception.DataInputException;
import com.cg.model.Brand;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.model.dto.ProductCreateResDTO;
import com.cg.model.dto.ProductResDTO;
import com.cg.model.dto.ProductUpdateResDTO;
import com.cg.repository.BrandRepository;
import com.cg.repository.ProductAvatarRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.upload.IUploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAvatarRepository productAvatarRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<ProductResDTO> findAllProductResDTO() {
        return productRepository.findAllProductResDTO();
    }

    @Override
    public List<ProductResDTO> findAllByDeletedIsFalse() {
        return productRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public ProductCreateResDTO createWithAvatar(Product product, MultipartFile avatarFile) {
        Brand brand = product.getBrand();
        brandRepository.save(brand);

        product.setBrand(brand);
        productRepository.save(product);

        ProductAvatar productAvatar = new ProductAvatar();
        productAvatar.setProduct(product);
        productAvatarRepository.save(productAvatar);

        uploadAndSaveProductImage(avatarFile, productAvatar);

        return new ProductCreateResDTO(product, brand, productAvatar.toProductAvatarDTO());
    }

    @Override
    public ProductUpdateResDTO update(Product product) {
        Brand brand = product.getBrand();
        brandRepository.save(brand);

        product.setBrand(brand);
        productRepository.save(product);

        ProductAvatar productAvatar = productAvatarRepository.findByProduct(product).get();

        return new ProductUpdateResDTO(product, brand, productAvatar.toProductAvatarDTO());
    }

    @Override
    public ProductUpdateResDTO updateWithAvatar(Product product, MultipartFile avatarFile) throws IOException {
        Brand brand = product.getBrand();
        brandRepository.save(brand);

        product.setBrand(brand);

        productRepository.save(product);

        Optional<ProductAvatar> productAvatarOptional = productAvatarRepository.findByProduct(product);

        ProductAvatar productAvatar = new ProductAvatar();

        if (!productAvatarOptional.isPresent()){
            productAvatar.setProduct(product);

            productAvatarRepository.save(productAvatar);
            uploadAndSaveProductImage(avatarFile, productAvatar);
        }
        else {
            productAvatar = productAvatarOptional.get();
            uploadService.destroyImage(productAvatar.getCloudId(), uploadUtils.buildImageUploadParams(productAvatar));
            uploadAndSaveProductImage(avatarFile, productAvatar);
        }

        return new ProductUpdateResDTO(product, brand, productAvatar.toProductAvatarDTO());
    }


    private void uploadAndSaveProductImage(MultipartFile file, ProductAvatar productAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(productAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productAvatar.setFileName(productAvatar.getId() + "." + fileFormat);
            productAvatar.setFileUrl(fileUrl);
            productAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            productAvatar.setCloudId(productAvatar.getFileFolder() + "/" + productAvatar.getId());
            productAvatarRepository.save(productAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public void remove(Long id) {

    }

//    @Override
//    public List<Product> findAllByProductTitleLike(String title) {
//        return productRepository.findAllByProductTitleLike(title);
//    }

    @Override
    public Optional<ProductResDTO> findAllProductResDTOById(Long productId) {
        return productRepository.findCustomerResDTOById(productId);
    }
}
