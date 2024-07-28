package com.kd.springboot_store.service;

import com.kd.springboot_store.dto.ResponseResult;
import com.kd.springboot_store.model.User;

public interface LoginService {
    ResponseResult login(User user);
    ResponseResult logout();



}
