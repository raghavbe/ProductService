package com.capstone.productservice.services;

import com.capstone.productservice.dtos.FakeStoreRequestDto;
import com.capstone.productservice.dtos.FakeStoreResponseDto;
import com.capstone.productservice.exceptions.ProductNotFoundException;
import com.capstone.productservice.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@Service
public class FakeStoreProductService implements ProductService
{
    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
       FakeStoreResponseDto fakeStoreResponseDto  = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreResponseDto.class);

       if(fakeStoreResponseDto == null)
       {
           throw new ProductNotFoundException("Product with id " + id + " not found.");
       }

       return fakeStoreResponseDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts()
    {
        FakeStoreResponseDto[] fakeStoreResponseDtos =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products",
                        FakeStoreResponseDto[].class);

        List<Product> products = new ArrayList<>();
        for(FakeStoreResponseDto fakeStoreResponseDto : fakeStoreResponseDtos)
        {
            Product product = fakeStoreResponseDto.toProduct();
            products.add(product);
        }

        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category)
    {
        FakeStoreRequestDto fakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);

        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreRequestDto,
                FakeStoreResponseDto.class);

        return fakeStoreResponseDto.toProduct();
    }

    private FakeStoreRequestDto createDtoFromParams(String name, String description, double price, String imageUrl, String category)
    {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(name);
        fakeStoreRequestDto.setDescription(description);
        fakeStoreRequestDto.setPrice(price);
        fakeStoreRequestDto.setImage(imageUrl);
        fakeStoreRequestDto.setCategory(category);
        return fakeStoreRequestDto;
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category)
    {
        FakeStoreRequestDto updatedFakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreRequestDto> requestEntity =
                new HttpEntity<>(updatedFakeStoreRequestDto, headers);

        ResponseEntity<FakeStoreResponseDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreResponseDto.class
        );

        return responseEntity.getBody().toProduct();

    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch)
            throws ProductNotFoundException,
            JsonPatchException,
            JsonProcessingException {

        Product existingProduct = getProductById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productNode = objectMapper.valueToTree(existingProduct);

        JsonNode patchedNode = patch.apply(productNode);

        Product patchedProduct = objectMapper.treeToValue(patchedNode, Product.class);

        return replaceProduct(id,
                patchedProduct.getName(),
                patchedProduct.getDescription(),
                patchedProduct.getPrice(),
                patchedProduct.getCategory().getName(),
                patchedProduct.getImageUrl()
        );
    }
}