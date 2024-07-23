package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.model.OrderItem;

import java.util.List;

public interface OrderDao
{
    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);





}
