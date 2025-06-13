package com.Products.ProductsMicroService.controller;

import com.Products.ProductsMicroService.common.ProductsType;
import com.Products.ProductsMicroService.model.dto.ProductRequestDTO;
import com.Products.ProductsMicroService.model.dto.ProductResponseDTO;
import com.Products.ProductsMicroService.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/products")
@Slf4j
public class ProductsController {

    private final ProductsService productService;

    @Operation(summary = "Add product", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products Sucesfully added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDTO addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        log.info("New request for endpoint POST/api/products logged");
        return productService.createProduct(productRequestDTO);
    }

    @Operation(summary = "Get Products", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products succesfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponseDTO> getProducts(@ParameterObject Pageable pageable) {
        log.info("New request for endpoint GET/api/products logged");
        return productService.getProducts(pageable);
    }

    @Operation(summary = "Get Products By Id", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found by id is successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        log.info("New request for endpoint GET/api/products/" + id + " logged");
        return productService.getProductById(id);
    }

    @Operation(summary = "Get Products By Type", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found By Type is successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
    })
    @GetMapping("/by-type")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponseDTO> getProductsByType(@RequestParam("type") ProductsType type, @ParameterObject Pageable pageable) {
        log.info("New request for endpoint GET/api/products/by-type" + type + " logged");
        return productService.getProductsByType(type, pageable);
    }

    @Operation(summary = "Delete product", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully deleted",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        log.info("New request for endpoint DELETE/api/products/" + id + " logged");
        productService.deleteProduct(id);
    }

    @Operation(summary = "Update product", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProductById(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO updatedProduct) {
        log.info("New request for endpoint PATCH/api/products/" + id + " logged");
        return productService.updateProductById(id, updatedProduct);
    }
}
