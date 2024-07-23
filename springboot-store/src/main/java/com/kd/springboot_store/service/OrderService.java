package com.kd.springboot_store.service;

import com.kd.springboot_store.dto.CreateOrderRequestDTO;
import com.kd.springboot_store.dto.OrderQueryParamsDTO;
import com.kd.springboot_store.dto.OrderResponseDTO;
import com.kd.springboot_store.model.Order;

import java.util.List;

public interface OrderService
{
    Integer countOrder(OrderQueryParamsDTO orderQueryParamsDTO);
    List<OrderResponseDTO> getOrders(OrderQueryParamsDTO orderQueryParamsDTO);
    OrderResponseDTO getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequestDTO createOrderRequestDTO);





}
