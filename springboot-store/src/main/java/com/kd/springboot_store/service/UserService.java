package com.kd.springboot_store.service;

import com.kd.springboot_store.dto.UserLoginRequestDTO;
import com.kd.springboot_store.dto.UserRegisterRequestDTO;
import com.kd.springboot_store.dto.UserResponseDTO;

public interface UserService
{
    UserResponseDTO getUserById(Integer userId);
    Integer register(UserRegisterRequestDTO userRegisterRequestDTO);

    UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);

}
