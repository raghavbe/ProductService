package com.capstone.productservice.services;


import com.capstone.productservice.dtos.FakeStoreResponseDto;
import com.capstone.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService
{
    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id)
    {
       FakeStoreResponseDto fakeStoreResponseDto  = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreResponseDto.class);

       return fakeStoreResponseDto.toProduct();
    }
}