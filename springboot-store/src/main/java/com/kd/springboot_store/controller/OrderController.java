package com.kd.springboot_store.controller;

import com.kd.springboot_store.dto.CreateOrderRequestDTO;
import com.kd.springboot_store.dto.OrderQueryParamsDTO;
import com.kd.springboot_store.dto.OrderResponseDTO;
import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.service.OrderService;
import com.kd.springboot_store.service.ProductService;
import com.kd.springboot_store.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Tag(name="訂單", description = "訂單API")
@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "取得用戶所有訂單")
    @GetMapping("/api/users/{userId}/orders")
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


    @Operation(summary = "創建訂單")
    @PostMapping("/api/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO)
    {
        Integer orderId =orderService.createOrder(userId, createOrderRequestDTO);

        OrderResponseDTO orderResponseDTO=orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }





    @Operation(summary = "取得特定某一筆訂單")
    @GetMapping("/api/users/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable Integer orderId
           )
    {

        // 取得 order
        OrderResponseDTO orderResponseDTO=orderService.getOrderById(orderId);

        // 檢查user 是否存在
        if (orderResponseDTO == null)
        {
            log.warn("找不到此orderId為{} --的訂單" , orderId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }






}
