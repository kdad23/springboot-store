package com.kd.springboot_store.dao;

import com.kd.springboot_store.dto.OrderItemResponseDTO;
import com.kd.springboot_store.model.OrderItem;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
    @Query(value = "SELECT " +
            "oi.order_item_id, " +
            "oi.order_id, oi.product_id, oi.quantity           , " +
            "oi.amount, " +
            "p.product_name,  " +
            " p.imag  " +
            " FROM order_item as oi  "  +
            " LEFT JOIN product as p ON oi.product_id=p.product_id " +
            " WHERE  oi.order_id=:orderId ", nativeQuery = true)
    List<Map<String, String>> getOrderItemByOrderId(@Param("orderId") Integer orderId);





}
