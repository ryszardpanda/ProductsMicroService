package com.Products.ProductsMicroService.controller;

import com.Products.ProductsMicroService.common.ConfigurationType;
import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.model.dto.ProductConfigurationDTO;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.service.ProductsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.math.BigDecimal;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsService productsService;

    @Test
    void addProduct_PayloadExist_ProductCreated() throws Exception {
        //given
        ProductConfigurationDTO configDTO = new ProductConfigurationDTO(1L, "config1", BigDecimal.valueOf(111.11), ConfigurationType.PROCESSOR);

        List<ProductConfigurationDTO> configListDTO = List.of(configDTO);

        ProductConfiguration productConfiguration = new ProductConfiguration(1L, "config1",
                BigDecimal.valueOf(111.11), ConfigurationType.PROCESSOR, new HashSet<>(), new ArrayList<>());

        ProductRequestDTO productRequestDTO = new ProductRequestDTO("product1", BigDecimal.valueOf(222.22), ProductsType.COMPUTER, 1, configListDTO);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(1L, "product1", BigDecimal.valueOf(222.22), ProductsType.COMPUTER, 1, configListDTO);

        Mockito.when(productsService.createProduct(any())).thenReturn(productResponseDTO);
        //when/then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .content(objectMapper.writeValueAsString(productRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("product1"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(222.22)))
                .andExpect(jsonPath("$.type").value("Computer"))
                .andExpect(jsonPath("$.quantity").value(1));
    }

    @Test
    void getProducts_ProductsExist_ProductPageReturned() throws Exception {
        //given
        ProductResponseDTO product1 = new ProductResponseDTO(1L, "product1", BigDecimal.valueOf(100), ProductsType.COMPUTER, 5, List.of());
        ProductResponseDTO product2 = new ProductResponseDTO(1L, "product2", BigDecimal.valueOf(200), ProductsType.COMPUTER, 5, List.of());

        Pageable pageReq = PageRequest.of(0, 2, Sort.by("price").descending());

        PageImpl<ProductResponseDTO> repoPage = new PageImpl<>(List.of(product1, product2), pageReq, 2);

        Mockito.when(productsService.getProducts(any())).thenReturn(repoPage);
        //when/then

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                        .content(objectMapper.writeValueAsString(pageReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name").value("product1"))
                .andExpect(jsonPath("$.content[0].price").value(100))
                .andExpect(jsonPath("$.content[1].name").value("product2"))
                .andExpect(jsonPath("$.content[1].price").value(200))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    void getProductById_ProductExist_ProductReturned() throws Exception {
        //given
        ProductResponseDTO product1 = new ProductResponseDTO(1L, "product1", BigDecimal.valueOf(100), ProductsType.COMPUTER, 5, List.of());

        when(productsService.getProductById(any())).thenReturn(product1);

        //when/then

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("product1"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.type").value("Computer"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void getProductByType_ProductExist_ProductReturned() throws Exception {
        //given
        ProductResponseDTO product1 = new ProductResponseDTO(1L, "product1", BigDecimal.valueOf(100), ProductsType.COMPUTER, 5, List.of());
        ProductResponseDTO product2 = new ProductResponseDTO(1L, "product2", BigDecimal.valueOf(200), ProductsType.COMPUTER, 5, List.of());

        Pageable pageReq = PageRequest.of(0, 2, Sort.by("price").descending());

        PageImpl<ProductResponseDTO> repoPage = new PageImpl<>(List.of(product1, product2), pageReq, 2);

        when(productsService.getProductsByType(eq(ProductsType.COMPUTER),
                any(Pageable.class)))
                .thenReturn(repoPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/by-type")
                        .param("type", ProductsType.COMPUTER.name())
                        .content(objectMapper.writeValueAsString(pageReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("product1"))
                .andExpect(jsonPath("$.content[0].price").value(100))
                .andExpect(jsonPath("$.content[1].name").value("product2"))
                .andExpect(jsonPath("$.content[1].price").value(200))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    void deleteProduct_ProductExist_ProductDeleted() throws Exception {
        //given
        Long id = 1L;
        doNothing().when(productsService).deleteProduct(id);

        // when / then
        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());

        verify(productsService, times(1)).deleteProduct(id);
        verifyNoMoreInteractions(productsService);
    }

    @Test
    void updateProduct_ProductExist_ProductUpdated() throws Exception {

        //given
        ProductConfigurationDTO configDTO = new ProductConfigurationDTO(1L, "config1", BigDecimal.valueOf(111.11), ConfigurationType.PROCESSOR);

        List<ProductConfigurationDTO> configListDTO = List.of(configDTO);

        ProductConfiguration productConfiguration = new ProductConfiguration(1L, "config1",
                BigDecimal.valueOf(111.11), ConfigurationType.PROCESSOR, new HashSet<>(), new ArrayList<>());


        ProductRequestDTO productRequestDTO = new ProductRequestDTO("New product", BigDecimal.valueOf(30.22), ProductsType.SMARTPHONE, 2, configListDTO);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(1L,"New product1", BigDecimal.valueOf(3000.22), ProductsType.SMARTPHONE, 2, configListDTO);


        when(productsService.updateProductById(any(), any())).thenReturn(productResponseDTO);
        //when
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/products/{id}", 1L)
                .content(objectMapper.writeValueAsString(productRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New product1"))
                .andExpect(jsonPath("$.price").value(3000.22))
                .andExpect(jsonPath("$.type").value("Smartphone"))
                .andExpect(jsonPath("$.quantity").value(2));
    }
}
