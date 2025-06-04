package com.Products.ProductsMicroService.model.dto;

import com.Products.ProductsMicroService.common.ConfigurationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfigurationDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private ConfigurationType type;
}
