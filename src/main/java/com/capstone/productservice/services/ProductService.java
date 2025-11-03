package com.capstone.productservice.services;


import com.capstone.productservice.models.Product;

public interface ProductService
{
    Product getProductById(long id);
}