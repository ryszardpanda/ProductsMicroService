package com.Products.ProductsMicroService.mapper;

import com.Products.ProductsMicroService.model.dto.ProductConfigurationDTO;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductRequestDTO dto);

    @Mapping(source = "productId", target = "productId")
    ProductResponseDTO toResponse(ProductEntity entity);

    ProductConfiguration toEntity(ProductConfigurationDTO dto);

    @Mapping(source = "productConfigurationId", target = "productConfigurationId")
    ProductConfigurationDTO toDto(ProductConfiguration entity);

    ProductEntity toEntity(ProductResponseDTO productResponseDTO);
}
