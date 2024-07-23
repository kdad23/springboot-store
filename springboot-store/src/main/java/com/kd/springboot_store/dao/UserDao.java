package com.kd.springboot_store.dao;

import com.kd.springboot_store.dto.UserRegisterRequestDTO;
import com.kd.springboot_store.model.User;

public interface UserDao {
    User getUserById(Integer userId);

    User getUserByEmail(String email);
    Integer createUser(UserRegisterRequestDTO userRegisterRequestDTO);
}
