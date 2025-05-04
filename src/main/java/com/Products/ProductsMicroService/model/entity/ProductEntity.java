package com.Products.ProductsMicroService.model.entity;

import com.Products.ProductsMicroService.common.ProductsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "PROCESSOR", length = 100)
    private String processor;
    @Column(name = "RAM")
    private Integer ram;
    @Column(name = "COLOR", length = 30)
    private String color;
    @Column(name = "BATTERY_CAPACITY", length = 30)
    private String batteryCapacity;

    @ElementCollection
    @CollectionTable(name = "PRODUCT_ACCESSORIES", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "ACCESSORY")
    private List<String> accessories;
}
