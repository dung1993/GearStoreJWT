package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductAvatarRepository extends JpaRepository<ProductAvatar, String> {

    Optional<ProductAvatar> findByProduct(Product product);
}
