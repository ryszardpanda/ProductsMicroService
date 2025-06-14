package com.Products.ProductsMicroService;

import com.Products.ProductsMicroService.common.ConfigurationType;
import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.exceptions.NoIdNumberException;
import com.Products.ProductsMicroService.mapper.ProductMapper;
import com.Products.ProductsMicroService.model.dto.ProductConfigurationDTO;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.model.entity.ProductConfiguration;
import com.Products.ProductsMicroService.model.entity.ProductEntity;
import com.Products.ProductsMicroService.repository.ProductConfigurationRepository;
import com.Products.ProductsMicroService.repository.ProductsRepository;
import com.Products.ProductsMicroService.service.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductsMicroServiceApplicationTests {

	private ProductsRepository productRepository;
	private ProductConfigurationRepository productConfigurationRepository;
	private ProductMapper productMapper;
	private ProductsService productsService;

	@BeforeEach
	void setUp(){
		this.productRepository = Mockito.mock(ProductsRepository.class);
		this.productConfigurationRepository = Mockito.mock(ProductConfigurationRepository.class);
		this.productMapper = Mappers.getMapper(ProductMapper.class);
		this.productsService = new ProductsService(productRepository, productConfigurationRepository, productMapper);
	}

	@Test
	public void createProduct_ProductSuccessfullyCreated(){

		//given
		ProductConfigurationDTO configDTO = new ProductConfigurationDTO(1L, "config1", BigDecimal.valueOf(111.11), ConfigurationType.PROCESSOR);

		List<ProductConfigurationDTO> configListDTO = List.of(configDTO);

		ProductConfiguration productConfiguration = new ProductConfiguration(1L, "config1",
				BigDecimal.valueOf(111.11), ConfigurationType.PROCESSOR, new HashSet<>(), new ArrayList<>());

		HashSet<ProductConfiguration> productConfigurationsSet = new HashSet<ProductConfiguration>(List.of(productConfiguration));


		ProductRequestDTO productRequestDTO = new ProductRequestDTO("product1", BigDecimal.valueOf(222.22), ProductsType.COMPUTER, 1, configListDTO);

		ProductEntity productEntity = new ProductEntity(1L, "product1", BigDecimal.valueOf(222.22), ProductsType.COMPUTER, 1, productConfigurationsSet);

		when(productConfigurationRepository.findById(Mockito.any())).thenReturn(Optional.of(productConfiguration));
		when(productRepository.saveAndFlush(Mockito.any())).thenReturn(productEntity);

		//when

		ProductResponseDTO result = productsService.createProduct(productRequestDTO);

		//then

		assertEquals("product1", result.getName());
		assertEquals(BigDecimal.valueOf(222.22), result.getPrice());
		assertEquals(1, result.getConfigurations().size());
		assertEquals(1L,
				result.getConfigurations().getFirst().getProductConfigurationId());


		verify(productConfigurationRepository).findById(1L);
		verify(productRepository).saveAndFlush(Mockito.any(ProductEntity.class));
		verifyNoMoreInteractions(productRepository, productConfigurationRepository);
	}

	@Test
	void createProduct_WhenConfigIdNotFound_ShouldThrowException() {
		//given
		ProductConfigurationDTO cfgDto = new ProductConfigurationDTO(99L, null, null, ConfigurationType.RAM);

		ProductRequestDTO request = new ProductRequestDTO("test-prod", BigDecimal.TEN, ProductsType.COMPUTER, 1,List.of(cfgDto));

		when(productConfigurationRepository.findById(99L)).thenReturn(Optional.empty());

		// when + then
		NoIdNumberException ex = assertThrows(NoIdNumberException.class, () -> productsService.createProduct(request));

		assertEquals("Product with id: 99 not found", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());

		verify(productRepository, never()).saveAndFlush(any());
	}

	@Test
	void getProducts_MappedPageReturned(){
		//given
		ProductEntity product1 = new ProductEntity(1L, "product1", BigDecimal.valueOf(100), ProductsType.COMPUTER, 5, Set.of());
		ProductEntity product2 = new ProductEntity(1L, "product2", BigDecimal.valueOf(200), ProductsType.COMPUTER, 5, Set.of());

		Pageable pageReq = PageRequest.of(0, 2, Sort.by("price").descending());

		PageImpl<ProductEntity> repoPage = new PageImpl<>(List.of(product1, product2), pageReq, 2);

		when(productRepository.findAll(pageReq)).thenReturn(repoPage);
		//when

		Page<ProductResponseDTO> result = productsService.getProducts(pageReq);

		//then
		assertEquals(2, result.getTotalElements());
		assertEquals(1, result.getTotalPages());
		assertEquals("product1", result.getContent().get(0).getName());
		assertEquals("product2", result.getContent().get(1).getName());

		verify(productRepository).findAll(pageReq);
		verifyNoMoreInteractions(productRepository);
	}
}
