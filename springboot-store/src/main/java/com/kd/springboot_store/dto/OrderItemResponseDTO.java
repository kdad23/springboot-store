package com.kd.springboot_store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


//@Entity
public class OrderItemResponseDTO
{
    @Schema(title = "訂單項目Id")
//    @Id
    private Integer orderItemId;

    @Schema(title = "訂單Id")
    private Integer orderId;


    @Schema(title = "產品Id")
    private Integer productId;


    @Schema(title = "數量")
    private Integer quantity;

    @Schema(title = "金額")
    private Integer amount;

    @Schema(title = "產品名稱")
    private String productName;

    @Schema(title = "產品圖片")
    private String imag;

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }
}
