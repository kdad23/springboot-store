package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.Product;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductQueryDSLRepository extends PagingAndSortingRepository<Product, Integer>,
        QuerydslPredicateExecutor<Product>
{



}
