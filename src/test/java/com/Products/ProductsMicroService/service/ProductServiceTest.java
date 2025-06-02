package com.Products.ProductsMicroService.service;

import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.mapper.ProductMapper;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import com.Products.ProductsMicroService.repository.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ProductServiceTest {

    private  ProductsRepository productRepository;
    private  ProductMapper productMapper;
    private  ProductsService productsService;

//    @BeforeEach
//    public void setUp(){
//        this.productRepository = Mockito.mock(ProductsRepository.class);
//        this.productMapper = Mockito.mock(ProductMapper.class);
//        this.productsService = new ProductsService(productRepository, productMapper);
//    }

//    @Test
//    public void addProductComputer_ProductComputerAdded(){
//        // given
//        ProductRequestDTO request = new ProductRequestDTO("Lenovo", 2222, ProductsType.COMPUTER, "AMD", 16, null, null, new ArrayList<>());
//        ProductEntity mappedEntity = new ProductEntity(1L, "Lenovo", 2222, ProductsType.COMPUTER, null);
//        //ProductResponseDTO response = new ProductResponseDTO(1L, "Lenovo", 2222, ProductsType.COMPUTER, null);
//
//        Mockito.when(productMapper.mapProductRequestDTOToEntity(request)).thenReturn(mappedEntity);
//        Mockito.when(productRepository.save(mappedEntity)).thenReturn(mappedEntity);
//      //  Mockito.when(productMapper.mapEntityToResponseDTO(mappedEntity)).thenReturn(response);
//
//        // when
//        ProductResponseDTO result = productsService.addProduct(request);
//
//        // then
//        Assertions.assertEquals(1L, result.getId());
//
//    }
}
