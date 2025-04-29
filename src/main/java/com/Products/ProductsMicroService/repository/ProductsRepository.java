package com.Products.ProductsMicroService.repository;

import com.Products.ProductsMicroService.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import com.Products.ProductsMicroService.common.ProductsType;




@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByProductsType(ProductsType type, Pageable pageable);
}
