package com.kd.springboot_store.dto;

import com.kd.springboot_store.model.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.List;

public class OrderResponseDTO
{

    @Schema(title = "訂單Id")
    private Integer orderId;
    @Schema(title = "用戶Id")
    private Integer userId;
    @Schema(title = "總花費金額")
    private Integer totalAmount;

    @Schema(title = "建立時間")
    private Date createdDate=new Date();
    @Schema(title = "最後修改時間")
    private Date lastModifiedDate=new Date();

    @Schema(title = "訂單產品細項")
    private List<OrderItemResponseDTO> orderItemList;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<OrderItemResponseDTO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemResponseDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
