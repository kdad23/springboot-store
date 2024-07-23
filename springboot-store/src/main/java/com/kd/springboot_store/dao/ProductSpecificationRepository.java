package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductSpecificationRepository extends PagingAndSortingRepository<Product,
        Integer>, JpaSpecificationExecutor<Product>
{

}
