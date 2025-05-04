package com.Products.ProductsMicroService.mapper;

import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "configuration", source = ".", qualifiedByName = "mapToConfiguration")
    ProductEntity mapProductRequestDTOToEntity(ProductRequestDTO dto);

    @Mapping(source = "configuration.processor", target = "processor")
    @Mapping(source = "configuration.ram", target = "ram")
    @Mapping(source = "configuration.color", target = "color")
    @Mapping(source = "configuration.batteryCapacity", target = "batteryCapacity")
    @Mapping(source = "configuration.accessories", target = "accessories")
    ProductResponseDTO mapEntityToResponseDTO(ProductEntity entity);

    ProductEntity mapResponseDTOToEntity(ProductResponseDTO dto);

    @Named("mapToConfiguration")
    default ProductConfiguration mapToConfiguration(ProductRequestDTO dto) {
        ProductConfiguration config = new ProductConfiguration();
        config.setProcessor(dto.getProcessor());
        config.setRam(dto.getRam());
        config.setColor(dto.getColor());
        config.setBatteryCapacity(dto.getBatteryCapacity());
        config.setAccessories(dto.getAccessories());
        return config;
    }
}
