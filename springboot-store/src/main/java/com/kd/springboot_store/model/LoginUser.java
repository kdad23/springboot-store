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

    private User user;//封裝用戶訊息

    private List<String> permissions;//儲存權限訊息

    public LoginUser(User user, List<String> list) {
        this.user = user;
        this.permissions = list;
    }
    //我们把這個List寫到外面這裡了，注意成員變數名一定要是authorities，不然會出縣奇怪的问题
    @JSONField(serialize = false) //這個注解的作用是不讓下面那行的成員變數序列化存入redis，避免redis不支持而报异常
    private List<SimpleGrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null)
        {
            return authorities;
        }
        //把permissions中字符串類型的權限信息转换成GrantedAuthority物件存入authorities
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    //獲取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //獲取用户名
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    //帳號是否未過期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //帳號是否未鎖定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //密馬是否未過期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //帳號是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}