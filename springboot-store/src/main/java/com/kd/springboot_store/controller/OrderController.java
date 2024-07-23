package com.kd.springboot_store.controller;

import com.kd.springboot_store.dto.CreateOrderRequestDTO;
import com.kd.springboot_store.dto.OrderQueryParamsDTO;
import com.kd.springboot_store.dto.OrderResponseDTO;
import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.service.OrderService;
import com.kd.springboot_store.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @PathVariable Integer userId,
            // 分頁 Pagination
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset)
    {
        OrderQueryParamsDTO orderQueryParamsDTO=new OrderQueryParamsDTO();
        orderQueryParamsDTO.setUserId(userId);
        orderQueryParamsDTO.setLimit(limit);
        orderQueryParamsDTO.setOffset(offset);
        // 取得 order list
        List<OrderResponseDTO> orderList=orderService.getOrders(orderQueryParamsDTO);

        // 取得 order 總數
        Integer count=orderService.countOrder(orderQueryParamsDTO);

        // 分頁
        Page<OrderResponseDTO> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }






    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO)
    {
        Integer orderId =orderService.createOrder(userId, createOrderRequestDTO);

        OrderResponseDTO orderResponseDTO=orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }









}
