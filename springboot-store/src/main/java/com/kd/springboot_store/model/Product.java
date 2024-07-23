package com.kd.springboot_store.model;

import com.kd.springboot_store.constant.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Schema(title = "產品Model")
@Entity(name="product")
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable
{
    @Schema(title = "商品Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id ")
    private Integer productId;
    @Schema(title = "商品名稱")
    @Column(name = "product_name")
    private String productName;
    @Schema(title = "商品種類")
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    // enum枚舉類要加上@Enumerated(EnumType.STRING)
    private ProductCategory category;
    @Schema(title = "商品圖片Url")
    @Column(name = "imag")
    private String imag;
    @Schema(title = "商品價格")
    @Column(name = "price")
    private Integer price;
    @Schema(title = "商品存貨")
    @Column(name = "stock")
    private Integer stock;
    @Schema(title = "商品description")
    @Column(name = "description")
    private String description;
    @Schema(title = "商品建立時間")
    @Column(name = "created_date")
    private Date createdDate=new Date();
    @Schema(title = "商品最後修改時間")
    @Column(name = "last_modified_date")
    private Date lastModifiedDate=new Date();

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }



    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }



    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
