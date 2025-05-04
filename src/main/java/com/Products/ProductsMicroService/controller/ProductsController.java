package com.Products.ProductsMicroService.controller;

import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.service.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
@Slf4j
public class ProductsController {

    private final ProductsService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDTO addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.addProduct(productRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponseDTO> getProducts(@ParameterObject Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/byType")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponseDTO> getProductsByType(@RequestParam("type") ProductsType type, @ParameterObject Pageable pageable) {
        return productService.getProductsByType(type, pageable);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProductById(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO updatedProduct) {
        return productService.updateProductById(id, updatedProduct);
    }
}
