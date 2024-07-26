package com.kd.springboot_store.controller;

import com.kd.springboot_store.dto.*;
import com.kd.springboot_store.service.UserService;
import com.kd.springboot_store.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name="用戶", description = "用戶API")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "註冊")
    @PostMapping("/api/users/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequestDTO, HttpServletResponse response)
    {
        // create 要返回Integer
        Integer userId=userService.register(userRegisterRequestDTO);
        UserResponseDTO userResponseDTO=userService.getUserById(userId);



        Map<String , Object> claims = new HashMap<>();
        claims.put("userId",userResponseDTO.getUserId());
        claims.put("email", userResponseDTO.getEmail());
        claims.put("password",userResponseDTO.getPassword());
        claims.put("createdDate",userResponseDTO.getCreatedDate());
        claims.put("lastModifiedDate",userResponseDTO.getLastModifiedDate());
        claims.put("accessToken",userResponseDTO.getAccessToken());
        claims.put("roles",userResponseDTO.getRoles());

        //使用JWT，生成令牌
        // generateJwt() 參數要放入自訂義的訊息
        String jwt = JwtUtils.generateJwt(claims);
        userResponseDTO.setAccessToken(jwt);


        // 為Cookie 設置 HttpOnly
        Cookie myCookie = new Cookie("tokenFromJava", jwt);
//        myCookie.setPath(request.getContextPath());
        myCookie.setPath("/");
        myCookie.setHttpOnly(true);
        myCookie.setMaxAge(1000);
        response.addCookie(myCookie);
        log.warn("-----註冊成功------");




        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }


    @Operation(summary = "登入")
    @PostMapping("/api/users/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO, HttpServletRequest request,
                                                 HttpServletResponse response)
    {
        //
        UserResponseDTO userResponseDTO=userService.login(userLoginRequestDTO);


        Map<String , Object> claims = new HashMap<>();
        claims.put("userId",userResponseDTO
                .getUserId());
        claims.put("email", userResponseDTO.getEmail());
        claims.put("password",userResponseDTO.getPassword());
        claims.put("createdDate",userResponseDTO.getCreatedDate());
        claims.put("lastModifiedDate",userResponseDTO.getLastModifiedDate());
        claims.put("accessToken",userResponseDTO.getAccessToken());
        claims.put("roles",userResponseDTO.getRoles());
        //使用JWT，生成令牌
        // generateJwt() 參數要放入自訂義的訊息
        String jwt = JwtUtils.generateJwt(claims);
        userResponseDTO.setAccessToken(jwt);


        // 為Cookie 設置 HttpOnly
        Cookie myCookie = new Cookie("tokenFromJava", jwt);
//        myCookie.setPath(request.getContextPath());
        myCookie.setPath("/");
        myCookie.setHttpOnly(true);
        myCookie.setMaxAge(1000);
        response.addCookie(myCookie);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);

    }




    @Operation(summary = "取得所有用戶")
    @GetMapping("/api/users/getalluser")
    public ResponseEntity<List<UserResponseDTO>> getAllUser()
    {
        List<UserResponseDTO> userResponseDTOList = userService.getAllUser();

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTOList);
    }


    @Operation(summary = "取得用戶")
    @GetMapping("/api/users/getUser/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Integer userId)
    {
        //檢查user是否存在
        UserResponseDTO userResponseDTO=userService.getUserById(userId);
        if(userResponseDTO ==null)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }


    @Operation(summary = "更新用戶角色")
    @PutMapping("/api/users/update/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response, @PathVariable Integer userId)
    {

        //檢查user是否存在
        UserResponseDTO userResponseDTO=userService.getUserById(userId);
        if(userResponseDTO ==null)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 修改使用角色權限
        userService.updateUser(userId, userRequestDTO);
        // 返回新增的資料物件
        UserResponseDTO userResponseDTO2=userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO2);

    }






}
