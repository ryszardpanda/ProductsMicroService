package com.Products.ProductsMicroService.model.dto;

import com.Products.ProductsMicroService.common.ProductsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private double price;
    private ProductsType type;

    private String processor;
    private Integer ram;

    private String color;
    private String batteryCapacity;
    private List<String> accessories;
}
