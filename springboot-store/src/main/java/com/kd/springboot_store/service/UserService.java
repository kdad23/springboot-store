package com.kd.springboot_store.service;

import com.kd.springboot_store.dto.*;

import java.util.List;

public interface UserService
{
    UserResponseDTO getUserById(Integer userId);
    Integer register(UserRegisterRequestDTO userRegisterRequestDTO);

    UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);

    List<UserResponseDTO> getAllUser();

    void updateUser(Integer userId, UserRequestDTO userRequestDTO);


}
