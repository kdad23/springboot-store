package com.kd.springboot_store.service.impl;

import com.kd.springboot_store.dao.UserRepository;


import com.kd.springboot_store.model.User;
import com.kd.springboot_store.model.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询用户信息
        User user=userRepository.findByEmail(username);

        //如果没有该用户就抛出异常
        if (Objects.isNull(user))
        {
            throw new RuntimeException("用户名或密碼錯誤");
        }

        //TODO: 查询权限信息封装到LoginUser中
        ArrayList<String> list = new ArrayList<>();
        list.add(user.getRoles());
//        list.add("USER");

        System.out.println( "使用者的角色--------" + user.getRoles());

        // 将用户信息封装到UserDetails实现类中
        return new LoginUser(user, list);
    }
}
