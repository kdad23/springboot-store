package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderQueryDSLRepository extends JpaRepository<Order, Integer>, QuerydslPredicateExecutor<Order>
{




}
