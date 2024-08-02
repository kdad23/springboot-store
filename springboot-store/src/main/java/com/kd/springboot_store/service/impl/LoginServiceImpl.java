package com.kd.springboot_store.service.impl;

import com.kd.springboot_store.dto.UserResponseDTO;
import com.kd.springboot_store.model.LoginUser;
import com.kd.springboot_store.model.User;
import com.kd.springboot_store.dto.ResponseResult;

import com.kd.springboot_store.service.LoginService;
import com.kd.springboot_store.util.JwtUtil;
import com.kd.springboot_store.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService
{
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public ResponseResult login(User user)
    {
        //1.封裝Authentication物件，這裡放入email，後面的ProviderManager就會調用UserdetailServiceImpl
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        //2.通過AuthenticationManager的authenticate方法来進行用户認證
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);




        log.info("在LoginServiceImpl裡面---UsernamePasswordAuthenticationToken-----{}", authenticationToken);
//        try {
//            Authentication authenticated = authenticationManager.authenticate(authenticationToken);
//            System.out.println("trycatch 認證成功-----");
//
//            // 認證成功
//        } catch (AuthenticationException e) {
//            // 認證失敗
//            System.out.println("trycatch認證失敗-----");
//            System.out.println(e.getMessage());
//        }

        // 如果驗證沒通過，給出對應的提示
        if(Objects.isNull(authenticated))
        {
            log.info("LoginServiceImpl----登入失敗------");
            throw new RuntimeException("登入失敗");
        }
        // 如果認證通過了，使用userId生成一個Jwt，Jwt存入ResponseResult返回
        //3.在Authentication中獲取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();

        String userId = loginUser.getUser().getUserId().toString();
        //4.認證通過生成token
        String jwt = JwtUtil.createJWT(userId);
        //5.用戶訊息存入redis，存入的物件型態為LoginUser
        redisCache.setCacheObject("login:" + userId, loginUser);
        redisCache.setCacheObject("Jwt", jwt, 100, TimeUnit.SECONDS);
        //6.把token返回給前端
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("token", jwt);

        log.info("這裡是LoginServiceImpl-----這裡有登入成功----userId為---{} ", userId);

        UserResponseDTO userResponseDTO=new UserResponseDTO();
        BeanUtils.copyProperties(loginUser.getUser(), userResponseDTO);
        userResponseDTO.setAccessToken(jwt);

        return new ResponseResult(200, "登入成功", userResponseDTO);

    }
//        LoginUser loginUser = (LoginUser)userDetailsService.loadUserByUsername(user.getEmail());

    @Override
    public ResponseResult logout() {
        //獲取SecurityContextHolder中的用户id，目前還可以從SecurityContextHolder中
        // 獲取到裡面的值，因為要進到這裡來還需要先經過JwtAuthenticationTokenFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getUserId();
        //删除redis中的用户信息來達登出的功能
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "Logout登出成功");
    }



}
