package com.cg.repository;

import com.cg.model.Product;

import com.cg.model.dto.ProductCreateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.cg.model.dto.ProductCreateDTO (" +
                "c.id, " +
                "c.title, " +
                "c.price, " +
                "c.quantity, " +
                "c.description, " +
                "c.brand," +
                "c.productAvatar" +
            ") " +
            "FROM Product AS c " +
            "WHERE c.deleted = false "
    )
    List<ProductCreateDTO> findAllProductDTO();

    List<Product> findAllByDeletedIsFalse();

    List<Product> findAllByIdNot(Long id);

    List<Product> findAllByIdNotAndDeletedIsFalse(Long id);

    Optional<Product> findByEmail(String email);

//    @Modifying
//    @Query("UPDATE Product AS c SET c.balance = c.balance + :transactionAmount WHERE c.id = :customerId")
//    void incrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);
//
//    @Modifying
//    @Query("UPDATE Customer AS c SET c.balance = c.balance - :transactionAmount WHERE c.id = :customerId")
//    void decrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);

    Optional<Product> findByEmailAndIdIsNot(String email, Long id);
}

