package com.Products.ProductsMicroService.model.dto;

import com.Products.ProductsMicroService.common.ProductsType;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name must not be blank")
    private String name;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;
    @NotNull(message = "Product type must not be null")

    private ProductsType type;
    private String processor;
    private Integer ram;

    private String color;
    private String batteryCapacity;
    private List<String> accessories;
}
