package com.kd.springboot_store.dao.impl;

import com.kd.springboot_store.dao.OrderItemResponseDTODao;
import com.kd.springboot_store.dto.OrderItemResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemResponseDTODaoImpl implements OrderItemResponseDTODao
{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<OrderItemResponseDTO> getOrderItemResponseDTOByOrderId(Integer orderId)
    {

        Query result = entityManager.createNativeQuery(
                "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, " +
                " p.product_name, p.imag " +
                " FROM order_item as oi " +
                " LEFT JOIN product as p ON oi.product_id=p.product_id " +
                " WHERE  oi.order_id=:orderId ", OrderItemResponseDTO.class).setParameter("orderId",
                orderId);
//        List<OrderItemResponseDTO> resultList = (List<OrderItemResponseDTO>)result.getResultList();

        result.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OrderItemResponseDTO.class));
        List<OrderItemResponseDTO> results = result.getResultList();




        return results;
    }
}
