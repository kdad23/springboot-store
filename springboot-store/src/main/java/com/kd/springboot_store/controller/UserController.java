package com.kd.springboot_store.controller;

import com.kd.springboot_store.dto.UserLoginRequestDTO;
import com.kd.springboot_store.dto.UserRegisterRequestDTO;
import com.kd.springboot_store.dto.UserResponseDTO;
import com.kd.springboot_store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequestDTO)
    {
        // create 要返回Integer
        Integer userId=userService.register(userRegisterRequestDTO);
        UserResponseDTO userResponseDTO=userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }


    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO)
    {
        //
        UserResponseDTO userResponseDTO=userService.login(userLoginRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }



}
