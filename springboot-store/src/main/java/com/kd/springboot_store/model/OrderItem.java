package com.kd.springboot_store.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(title = "訂單項目")
@Entity
@Table(name="order_item")
public class OrderItem
{

    @Schema(title = "訂單項目Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @Schema(title = "訂單Id")
    @Column(name = "order_id")
    private Integer orderId;


    @Schema(title = "產品Id")
    @Column(name = "product_id")
    private Integer productId;


    @Schema(title = "數量")
    @Column(name = "quantity")
    private Integer quantity;

    @Schema(title = "金額")
    @Column(name = "amount")
    private Integer amount;

//    @Schema(title = "產品product")
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id")
//    private Order order;





    // orphanRemoval為true:關聯的資料為null，對應的關聯表中的資料一併刪除
//    @Schema(title = "產品product")
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "product_id")
//    private Product product;


//    @Column(name = "product_name")
//    private String productName;
//    @Column(name = "imag")
//    private String imag;




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
}
