package com.Products.ProductsMicroService.service;

import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.exceptions.MissingConfigurationException;
import com.Products.ProductsMicroService.exceptions.NoIdNumberException;
import com.Products.ProductsMicroService.mapper.ProductMapper;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import com.Products.ProductsMicroService.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductsRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {

        switch (productRequestDTO.getType()) {
            case COMPUTER -> configureComputer(productRequestDTO);
            case SMARTPHONE -> configureSmartphone(productRequestDTO);
            case ELECTRONICS -> {
            }
        }
        ProductEntity productEntity = productMapper.mapProductRequestDTOToEntity(productRequestDTO);
        assignProductConfiguration(productEntity, productRequestDTO);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return productMapper.mapEntityToResponseDTO(savedEntity);
    }

    public Page<ProductResponseDTO> getProducts(Pageable pageable) {
        Page<ProductEntity> page = productRepository.findAll(pageable);
        return page.map(productMapper::mapEntityToResponseDTO);
    }

    public ProductResponseDTO getProductById(Long id) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new NoIdNumberException("Product with id: " + id + " not found", HttpStatus.NOT_FOUND));
        return productMapper.mapEntityToResponseDTO(productEntity);
    }

    public Page<ProductResponseDTO> getProductsByType(ProductsType productsType, Pageable pageable) {
        Page<ProductEntity> page = productRepository.findAllByType(productsType, pageable);
        return page.map(productMapper::mapEntityToResponseDTO);
    }

    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new NoIdNumberException("Product with id: " + id + " not found", HttpStatus.NOT_FOUND));
        productRepository.delete(productEntity);
    }

    @Transactional
    public ProductResponseDTO updateProductById(Long id, ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productById = getProductById(id);
        ProductEntity productEntity = productMapper.mapResponseDTOToEntity(productById);
        productEntity.setName(productRequestDTO.getName());
        productEntity.setPrice(productRequestDTO.getPrice());
        productEntity.setType(productRequestDTO.getType());

        productRepository.save(productEntity);

        return productMapper.mapEntityToResponseDTO(productEntity);
    }

    private void configureComputer(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO.getRam() == null || productRequestDTO.getProcessor() == null) {
            throw new MissingConfigurationException("Computer must have processor and RAM selected", HttpStatus.BAD_REQUEST);
        }
    }

    private void configureSmartphone(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO.getBatteryCapacity() == null || productRequestDTO.getColor() == null) {
            throw new MissingConfigurationException("Smartphone must have color and battery capacity selected", HttpStatus.BAD_REQUEST);
        }
    }

    private void assignProductConfiguration(ProductEntity entity, ProductRequestDTO productRequestDTO) {
        if (productRequestDTO.getType() == ProductsType.COMPUTER || productRequestDTO.getType() == ProductsType.SMARTPHONE) {
            ProductConfiguration config = new ProductConfiguration();

            if (productRequestDTO.getType() == ProductsType.COMPUTER) {
                config.setProcessor(productRequestDTO.getProcessor());
                config.setRam(productRequestDTO.getRam());
            }

            if (productRequestDTO.getType() == ProductsType.SMARTPHONE) {
                config.setColor(productRequestDTO.getColor());
                config.setBatteryCapacity(productRequestDTO.getBatteryCapacity());
                config.setAccessories(productRequestDTO.getAccessories());
            }

            config.setProduct(entity);
            entity.setConfiguration(config);
        }
    }
}
