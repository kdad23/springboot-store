package com.kd.springboot_store.dao;

import com.kd.springboot_store.dto.ProductQueryParams;

import com.kd.springboot_store.dto.ProductRequestDTO;
import com.kd.springboot_store.model.Product;

import java.util.List;

public interface ProductDao
{
    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequestDTO productRequestDTO);

    void updateProduct(Integer productId, ProductRequestDTO productRequestDTO);
    void deleteProductById(Integer productId);



}
