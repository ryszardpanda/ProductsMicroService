package com.Products.ProductsMicroService.repository;

import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductConfigurationRepository extends JpaRepository<ProductConfiguration, Long> {
}
