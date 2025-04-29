package com.Products.ProductsMicroService.model.entity;

import com.Products.ProductsMicroService.common.ProductsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "PRODUCT_NAME", length = 50, nullable = false)
    private String name;
    @Column(name = "PRODUCT_PRICE", length = 50, nullable = false)
    private double price;
    @Column(name = "PRODUCT_TYPE", length = 50, nullable = false)
    private ProductsType productsType;
}
