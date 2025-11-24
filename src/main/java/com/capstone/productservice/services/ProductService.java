package com.capstone.productservice.services;


import com.capstone.productservice.exceptions.ProductNotFoundException;
import com.capstone.productservice.models.Product;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

public interface ProductService
{
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(String name, String description, double price, String imageUrl, String category);
    Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category);
    Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException;
}