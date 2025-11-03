package com.capstone.productservice.controllers;


import com.capstone.productservice.dtos.ProductResponseDto;
import com.capstone.productservice.models.Product;
import com.capstone.productservice.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
    ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id)
    {
        Product product = productService.getProductById(id);

        return ProductResponseDto.from(product);
    }
}