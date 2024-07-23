package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>
{

//    List<Product> findByProductName(String name);
//    @Query(value = "SELECT  product_id, product_name, category, image_url, price, \" +\n" +
//            "                \"stock, \" +\n" +
//            "                \"description, created_date, last_modified_date \" +\n" +
//            "                \"FROM product WHERE 1=1 ")
//    List<Product> findAllByProductQueryParams(String name);



}
