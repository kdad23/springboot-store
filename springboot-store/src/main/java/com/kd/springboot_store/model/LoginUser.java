package com.kd.springboot_store.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;//封装用户信息

    private List<String> permissions;//存储权限信息

    public LoginUser(User user, List<String> list) {
        this.user = user;
        this.permissions = list;
    }
    //我们把这个List写到外面这里了，注意成员变量名一定要是authorities，不然会出现奇奇怪怪的问题
    @JSONField(serialize = false) //这个注解的作用是不让下面那行的成员变量序列化存入redis，避免redis不支持而报异常
    private List<SimpleGrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null)
        {
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    //获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //获取用户名
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    //账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}