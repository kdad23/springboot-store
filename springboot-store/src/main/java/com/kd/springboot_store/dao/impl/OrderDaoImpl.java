package com.kd.springboot_store.dao.impl;

import com.kd.springboot_store.dao.OrderDao;
import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.model.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImpl implements OrderDao
{
    @Override
    public Order getOrderById(Integer orderId) {
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        return null;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        return null;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

    }
}
