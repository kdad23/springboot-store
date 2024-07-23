package com.kd.springboot_store.service;

import com.kd.springboot_store.dto.ProductQueryParams;

import com.kd.springboot_store.dto.ProductRequestDTO;
import com.kd.springboot_store.dto.ProductResponseDTO;
import com.kd.springboot_store.model.Product;

import java.util.List;

public interface ProductService
{
    Integer countProduct(ProductQueryParams productQueryParams);
    List<ProductResponseDTO> getProducts(ProductQueryParams productQueryParams);
    ProductResponseDTO getProductById(Integer productId);
    Integer createProduct(ProductRequestDTO productRequest);
    void updateProduct(Integer productId, ProductRequestDTO productRequest);
    void deleteProductById(Integer productId);

}
