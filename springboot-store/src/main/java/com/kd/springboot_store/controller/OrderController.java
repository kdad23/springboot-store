package com.kd.springboot_store.controller;

import com.kd.springboot_store.dto.CreateOrderRequestDTO;
import com.kd.springboot_store.dto.OrderResponseDTO;
import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO)
    {
        Integer orderId =orderService.createOrder(userId, createOrderRequestDTO);

        OrderResponseDTO orderResponseDTO=orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }









}
