package com.kd.springboot_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Schema(title = "用戶的Model")
@Entity(name="user")
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
//用这个注解才能实现动态更新（update_time的更新）
@DynamicUpdate
public class User
{
    @Schema(title = "用戶Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Schema(title = "電子郵件")
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Schema(title = "密碼")
    @Column(name = "password")
    private String password;

    @Schema(title = "商品建立時間")
    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate=new Date();

    @Schema(title = "商品最後修改時間")
    @Column(name = "last_modified_date")
    @LastModifiedDate
    private Date lastModifiedDate=new Date();


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
