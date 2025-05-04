package com.Products.ProductsMicroService.model.dto;

import com.Products.ProductsMicroService.common.ProductsType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @NotBlank
    private String name;
    @NotNull
    private double price;
    private ProductsType productsType;

    private String processor;
    private Integer ram;

    private String color;
    private String batteryCapacity;
    private List<String> accessories;
}
