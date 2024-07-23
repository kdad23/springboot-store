package com.kd.springboot_store.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Schema(title = "order訂單")
@Entity
@Table(name="ordersummary")
public class Order
{
    @Schema(title = "訂單Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id ")
    private Integer orderId;
    @Schema(title = "用戶Id")
    @Column(name = "user_id")
    private Integer userId;
    @Schema(title = "總花費金額")
    @Column(name = "total_amount")
    private Integer totalAmount;

    @Schema(title = "建立時間")
    @Column(name = "created_date")
    private Date createdDate=new Date();
    @Schema(title = "最後修改時間")
    @Column(name = "last_modified_date")
    private Date lastModifiedDate=new Date();

//    @OneToMany(cascade = CascadeType.PERSIST , mappedBy = "order")
//    private List<OrderItem> orderItemList;


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




}
