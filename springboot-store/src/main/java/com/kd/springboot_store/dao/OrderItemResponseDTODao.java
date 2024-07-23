package com.kd.springboot_store.dao;

import com.kd.springboot_store.dto.OrderItemResponseDTO;
import com.kd.springboot_store.model.OrderItem;

import java.util.List;

public interface OrderItemResponseDTODao
{
    public List<OrderItemResponseDTO> getOrderItemResponseDTOByOrderId(Integer orderId);

}
