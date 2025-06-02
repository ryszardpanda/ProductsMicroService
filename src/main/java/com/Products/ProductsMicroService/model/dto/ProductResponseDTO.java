package com.Products.ProductsMicroService.model.dto;

import com.Products.ProductsMicroService.common.ProductsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductsType type;
    private List<ProductConfigurationDTO> configurations;
}
