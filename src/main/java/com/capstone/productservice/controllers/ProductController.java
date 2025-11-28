package com.capstone.productservice.controllers;


import com.capstone.productservice.dtos.CreateFakeStoreProductRequestDto;
import com.capstone.productservice.dtos.ProductResponseDto;
import com.capstone.productservice.exceptions.ProductNotFoundException;
import com.capstone.productservice.models.Product;
import com.capstone.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;


@RestController
public class ProductController {
    ProductService productService;

    public ProductController(
            @Qualifier("fakeStoreProductService") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id)
            throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return ProductResponseDto.from(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();

//        List<ProductResponseDto> productResponseDtos =
//                products.stream().map(ProductResponseDto::from)
//                        .collect(Collectors.toList());

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody CreateFakeStoreProductRequestDto
                    createFakeStoreProductRequestDto) {
        Product product = productService.createProduct(
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto replaceProduct(@PathVariable("id") long id,
                                             @RequestBody CreateFakeStoreProductRequestDto
                                                     createFakeStoreProductRequestDto) {
        Product product = productService.replaceProduct(
                id,
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );

        return ProductResponseDto.from(product);
    }

    @PatchMapping(
            path = "/products/{id}",
            consumes = "application/json-patch+json" // REQUIRED
    )
    public ProductResponseDto updateProduct(
            @PathVariable("id") long id,
            @RequestBody JsonPatch jsonPatch
    ) throws ProductNotFoundException, JsonPatchException, JsonProcessingException
    {
        Product product = productService.applyPatchToProduct(id, jsonPatch);
        return ProductResponseDto.from(product);
    }

}