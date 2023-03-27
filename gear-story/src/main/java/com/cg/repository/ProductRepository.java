package com.cg.repository;

import com.cg.model.Brand;
import com.cg.model.Product;
import com.cg.model.dto.ProductCreateResDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.ProductResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.cg.model.dto.ProductResDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.brand, "+
            "pa.id, " +
            "pa.fileFolder, " +
            "pa.fileName, " +
            "pa.fileUrl " +
            ") " +
            "FROM Product AS p " +
            "LEFT JOIN ProductAvatar AS pa " +
            "ON pa.product = p " +
            "WHERE p.deleted = false "
    )
    List<ProductResDTO> findAllProductResDTO();

    List<Product> findAllByIdNot(Long id);


    List<ProductResDTO> findAllByDeletedIsFalse();

    @Query("SELECT NEW com.cg.model.dto.ProductResDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.brand, "+
            "pa.id, " +
            "pa.fileFolder, " +
            "pa.fileName, " +
            "pa.fileUrl " +
            ") " +
            "FROM Product AS p " +
            "LEFT JOIN ProductAvatar AS pa " +
            "ON pa.product = p " +
            "WHERE p.deleted = false " +
            "AND p.id = :id "
    )
    Optional<ProductResDTO> findCustomerResDTOById(@Param("id") Long id);

//    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
//            "p.id, " +
//            "p.title, " +
//            "p.price, " +
//            "p.quantity," +
//            "p.description, " +
//            "p.brand,"+
//            "p.productAvatar" +
//            ") " +
//            "FROM Product AS p"
//    )
//    List<ProductDTO> findAllProductDTO();
}
