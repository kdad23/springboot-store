package com.kd.springboot_store.service.impl;

import com.kd.springboot_store.dto.UserResponseDTO;
import com.kd.springboot_store.model.LoginUser;
import com.kd.springboot_store.model.User;
import com.kd.springboot_store.dto.ResponseResult;

import com.kd.springboot_store.service.LoginService;
import com.kd.springboot_store.util.JwtUtil;
import com.kd.springboot_store.util.RedisCache;
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
        //1.封装Authentication对象，這裡放入email，後面的ProviderManager就會調用UserdetailServiceImpl
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        System.out.println("在LoginServiceImpl裡面---UsernamePasswordAuthenticationToken-----" + authenticationToken+
                "---------");
        //2.通过AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);




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




        System.out.println("在LoginServiceImpl-----authenticated----" + authenticated+
                "------");
        // 如果驗證沒通過，給出對應的提示
        if(Objects.isNull(authenticated))
        {
            System.out.println("LoginServiceImpl----登入失敗------");
            throw new RuntimeException("登入失敗");
        }
        // 如果認證通過了，使用userId生成一個Jwt，Jwt存入ResponseResult返回
        //3.在Authentication中获取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
//        LoginUser loginUser = (LoginUser)userDetailsService.loadUserByUsername(user.getEmail());


        String userId = loginUser.getUser().getUserId().toString();
        //4.认证通过生成token
//        String jwt = "111111111";
        String jwt = JwtUtil.createJWT(userId);
        //5.用户信息存入redis，存入的物件型態為LoginUser
//        redisCache.setCacheObject("login:" + userId, loginUser);
        //6.把token返回给前端
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("token", jwt);
        // 把完整的用戶訊息存入redis userId作為key
        redisCache.setCacheObject("login:" + userId, loginUser);
        System.out.println("這裡是LoginServiceImpl-----這裡有登入成功----userId為-----" + userId);

        UserResponseDTO userResponseDTO=new UserResponseDTO();
        BeanUtils.copyProperties(loginUser.getUser(), userResponseDTO);
        userResponseDTO.setAccessToken(jwt);


        return new ResponseResult(200, "登录成功", userResponseDTO);

    }


    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id，目前還可以從SecurityContextHolder中
        // 獲取到裡面的值，因為要進到這裡來還需要先經過JwtAuthenticationTokenFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getUserId();
        //删除redis中的用户信息來達登出的功能
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "Logout登出成功");
    }



}
