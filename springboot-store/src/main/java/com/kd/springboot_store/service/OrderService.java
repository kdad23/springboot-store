package com.kd.springboot_store.service;

import com.kd.springboot_store.dto.CreateOrderRequestDTO;
import com.kd.springboot_store.dto.OrderResponseDTO;
import com.kd.springboot_store.model.Order;

public interface OrderService
{
    OrderResponseDTO getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequestDTO createOrderRequestDTO);





}
