package com.Products.ProductsMicroService.service;

import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.exceptions.NoIdNumberException;
import com.Products.ProductsMicroService.mapper.ProductMapper;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import com.Products.ProductsMicroService.repository.ProductConfigurationRepository;
import com.Products.ProductsMicroService.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductsRepository productRepository;
    private final ProductConfigurationRepository productConfigurationRepository;
    private final ProductMapper productMapper;


    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        ProductEntity product = productMapper.toEntity(dto);

        Set<ProductConfiguration> confs = dto.getConfigurations().stream()
                .map(confDto -> {
                    if (confDto.getId() != null) {
                        return productConfigurationRepository
                                .findById(confDto.getId())
                                .orElseThrow(() -> new NoIdNumberException("Product with id: " + confDto.getId() + " not found", HttpStatus.NOT_FOUND));
                    } else {
                        return productMapper.toEntity(confDto);
                    }
                })
                .collect(Collectors.toSet());

        product.setConfigurations(confs);
        productRepository.save(product);

        return productMapper.toResponse(product);
    }



//    @Transactional
//    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
//
//        switch (productRequestDTO.getType()) {
//            case COMPUTER -> configureComputer(productRequestDTO);
//            case SMARTPHONE -> configureSmartphone(productRequestDTO);
//            case ELECTRONICS -> {
//            }
//        }
//        ProductEntity productEntity = productMapper.toEntity(productRequestDTO);
//        assignProductConfiguration(productEntity, productRequestDTO);
//        ProductEntity savedEntity = productRepository.save(productEntity);
//        log.info("Product " + productRequestDTO +  " sucesfully saved");
//        return productMapper.toResponse(savedEntity);
//    }

    public Page<ProductResponseDTO> getProducts(Pageable pageable) {
        Page<ProductEntity> page = productRepository.findAll(pageable);
        log.info("Product Page " + pageable +  " successfully returned");
        return page.map(productMapper::toResponse);
    }

    public ProductResponseDTO getProductById(Long id) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new NoIdNumberException("Product with id: " + id + " not found", HttpStatus.NOT_FOUND));
        ProductResponseDTO productResponseDTO = productMapper.toResponse(productEntity);
        log.info("Product with id: " + id + " successfully returned: " + productResponseDTO);
        return productResponseDTO;
    }

    public Page<ProductResponseDTO> getProductsByType(ProductsType productsType, Pageable pageable) {
        Page<ProductEntity> page = productRepository.findAllByType(productsType, pageable);
        Page<ProductResponseDTO> productResponseDTO = page.map(productMapper::toResponse);
        log.info("Products with type: " + productsType + " successfully returned: " + productResponseDTO);
        return productResponseDTO;
    }

    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new NoIdNumberException("Product with id: " + id + " not found", HttpStatus.NOT_FOUND));
        productRepository.delete(productEntity);
        log.info("Product with id: " + id + " successfully deleted");
    }

    @Transactional
    public ProductResponseDTO updateProductById(Long id, ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productById = getProductById(id);
        ProductEntity productEntity = productMapper.toEntity(productById);
        productEntity.setName(productRequestDTO.getName());
        productEntity.setPrice(productRequestDTO.getPrice());
        productEntity.setType(productRequestDTO.getType());

        productRepository.save(productEntity);

        ProductResponseDTO productResponseDTO = productMapper.toResponse(productEntity);
        log.info("Product with id: " + id + " successfully updated: " + productResponseDTO);
        return productResponseDTO;
    }

//    private void configureComputer(ProductRequestDTO productRequestDTO) {
//        if (productRequestDTO.getRam() == null || productRequestDTO.getProcessor() == null) {
//            throw new MissingConfigurationException("Computer must have processor and RAM selected", HttpStatus.BAD_REQUEST);
//        }
//        log.info("Added specific config for this computer - RAM capacity: " + productRequestDTO.getRam() + " and - processor " + productRequestDTO.getProcessor());
//
//    }

//    private void configureSmartphone(ProductRequestDTO productRequestDTO) {
//        if (productRequestDTO.getBatteryCapacity() == null || productRequestDTO.getColor() == null) {
//            throw new MissingConfigurationException("Smartphone must have color and battery capacity selected", HttpStatus.BAD_REQUEST);
//        }
//        log.info("Added specific config for this smartphone - battery capacity: " + productRequestDTO.getBatteryCapacity() + " and - color " + productRequestDTO.getColor());
//    }

//    private void assignProductConfiguration(ProductEntity entity, ProductRequestDTO productRequestDTO) {
//        if (productRequestDTO.getType() == ProductsType.COMPUTER || productRequestDTO.getType() == ProductsType.SMARTPHONE) {
//            ProductConfiguration config = new ProductConfiguration();
//
//            if (productRequestDTO.getType() == ProductsType.COMPUTER) {
//                config.setProcessor(productRequestDTO.getProcessor());
//                config.setRam(productRequestDTO.getRam());
//            }
//
//            if (productRequestDTO.getType() == ProductsType.SMARTPHONE) {
//                config.setColor(productRequestDTO.getColor());
//                config.setBatteryCapacity(productRequestDTO.getBatteryCapacity());
//                config.setAccessories(productRequestDTO.getAccessories());
//            }
//
//            config.setProduct(entity);
//            entity.setConfiguration(config);
//            log.info("Configuration for product " + productRequestDTO.getName() + " saved");
//        }
//    }
}
