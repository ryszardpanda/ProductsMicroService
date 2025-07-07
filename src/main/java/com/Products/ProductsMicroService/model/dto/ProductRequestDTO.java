package com.Products.ProductsMicroService.model.dto;

import com.Products.ProductsMicroService.common.ProductsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private String name;
    private BigDecimal price;
    private ProductsType type;
    private int quantity;
    private List<ProductConfigurationDTO> configurations;
}
