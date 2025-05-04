package com.Products.ProductsMicroService.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String processor;
    private Integer ram;

    private String color;
    private String batteryCapacity;

    @ElementCollection
    @CollectionTable(name = "product_accessories", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "accessory")
    private List<String> accessories;
}
