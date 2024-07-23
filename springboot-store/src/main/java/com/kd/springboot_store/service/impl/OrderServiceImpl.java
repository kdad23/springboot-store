package com.kd.springboot_store.service.impl;

import com.alibaba.fastjson.JSON;
import com.kd.springboot_store.dao.*;
import com.kd.springboot_store.dto.*;
import com.kd.springboot_store.model.*;
import com.kd.springboot_store.service.OrderService;
import com.kd.springboot_store.util.BeanCopyUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderItemResponseDTODao orderItemResponseDTODao;

    @Autowired
    OrderQueryDSLRepository orderQueryDSLRepository;

    @Override
    public Integer countOrder(OrderQueryParamsDTO orderQueryParamsDTO)
    {
        JPAQueryFactory jpaQueryFactory=new JPAQueryFactory(entityManager);
        QOrder qOrder=QOrder.order;

        List<Order> orderList = (List<Order>) orderQueryDSLRepository.findAll
                (
                    qOrder.userId.eq(orderQueryParamsDTO.getUserId())
        );
        int totalCount = orderList.size();

        return totalCount;
    }

    @Override
    public List<OrderResponseDTO> getOrders(OrderQueryParamsDTO orderQueryParamsDTO)
    {

        JPAQueryFactory jpaQueryFactory=new JPAQueryFactory(entityManager);
        QOrder qOrder=QOrder.order;

        //初始化組裝條件(類似where 1=1)
        Predicate predicate = qOrder.isNotNull().or(qOrder.isNull());
        //執行動態條件件拼装
        predicate = orderQueryParamsDTO.getUserId() == null ? predicate : ExpressionUtils.and(predicate,
                qOrder.userId.eq(orderQueryParamsDTO.getUserId()));
        //執行拚装好的條件並根據userId排序，根據uIndex分組
        List<Order> orderList = jpaQueryFactory.selectFrom(qOrder)
                .where(predicate)
                .offset(orderQueryParamsDTO.getOffset())
                .limit(orderQueryParamsDTO.getLimit())
                .orderBy(qOrder.createdDate.desc())
                .fetch();

        List<OrderResponseDTO> orderResponseDTOS=BeanCopyUtils.copyListProperties(orderList
                , OrderResponseDTO::new);

        for (OrderResponseDTO orderResponseDTO: orderResponseDTOS)
        {
            List<Map<String,String>> rawOrderItemList =
                    orderItemRepository.getOrderItemByOrderId(orderResponseDTO.getUserId());
            List<OrderItemResponseDTO> orderItemList = JSON.parseArray(JSON.toJSONString(rawOrderItemList), OrderItemResponseDTO.class);
            orderResponseDTO.setOrderItemList(orderItemList);

        }


        return orderResponseDTOS;


    }

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




















