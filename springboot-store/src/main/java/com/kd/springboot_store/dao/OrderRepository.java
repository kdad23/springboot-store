package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>
{



}
