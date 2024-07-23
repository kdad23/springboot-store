package com.kd.springboot_store.service.impl;

import com.alibaba.fastjson.JSON;
import com.kd.springboot_store.dao.*;
import com.kd.springboot_store.dto.BuyItem;
import com.kd.springboot_store.dto.CreateOrderRequestDTO;
import com.kd.springboot_store.dto.OrderItemResponseDTO;
import com.kd.springboot_store.dto.OrderResponseDTO;
import com.kd.springboot_store.model.Order;
import com.kd.springboot_store.model.OrderItem;
import com.kd.springboot_store.model.Product;
import com.kd.springboot_store.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemResponseDTODao orderItemResponseDTODao;


    @Transactional
    @Override
    public OrderResponseDTO getOrderById(Integer orderId)
    {
        Order order=orderRepository.findById(orderId).orElse(null);

        OrderResponseDTO orderResponseDTO=new OrderResponseDTO();

        BeanUtils.copyProperties(order, orderResponseDTO);

        List<Map<String,String>> rawOrderItemList = orderItemRepository.getOrderItemByOrderId(orderId);

        List<OrderItemResponseDTO> orderItemList = JSON.parseArray(JSON.toJSONString(rawOrderItemList), OrderItemResponseDTO.class);

        orderResponseDTO.setOrderItemList(orderItemList);

        return orderResponseDTO;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequestDTO createOrderRequestDTO)
    {
        int totalAmount=0;
        List<OrderItem> orderItemList=new ArrayList<>();

        for (BuyItem buyItem : createOrderRequestDTO.getBuyItemList())
        {
            Product product=productRepository.findById(buyItem.getProductId()).orElse(null);
            //計算個別商品總價錢
            int amount= buyItem.getQuantity() * product.getPrice();
            //計算總價錢
            totalAmount= totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
//            orderItem.setProduct(product);
            orderItemList.add(orderItem);
        }

        Order order=new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setCreatedDate(new Date());
        order.setLastModifiedDate(new Date());

        // 創建訂單
        Integer orderId=
                orderRepository.save(order).getOrderId();

        for (OrderItem orderItem:orderItemList)
        {
            orderItem.setOrderId(orderId);
        }
        // 有修改多張Table一定要在方法上新增 @Transactional
        orderItemRepository.saveAll(orderItemList);

        return orderId;
    }
}




















