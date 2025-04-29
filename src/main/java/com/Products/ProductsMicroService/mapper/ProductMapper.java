package com.Products.ProductsMicroService.mapper;

import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity mapProductRequestDTOToEntity (ProductRequestDTO productRequestDTO);
    ProductResponseDTO mapEntityToResponseDTO(ProductEntity productEntity);
    ProductEntity mapResponseDTOToEntity(ProductResponseDTO productResponseDTO);

}
