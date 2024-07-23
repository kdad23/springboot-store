package com.kd.springboot_store.util;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

//@Slf4j
//public class createNewJwt
//{
//    public static String giveYouNewJWT(HttpServletRequest request)
//    {
//
//        AngularUserResponseDTO angularUserResponseDTO= JwtParseAllClaim.getWhoIsTheUser(request);
//
//        Map<String , Object> claims=new HashMap<>();
//        claims.put("userId",angularUserResponseDTO.getUserId());
//        claims.put("email", angularUserResponseDTO.getEmail());
//        claims.put("createdDate",angularUserResponseDTO.getCreatedDate());
//        claims.put("lastModifiedDate",angularUserResponseDTO.getLastModifiedDate());
//        claims.put("roles",angularUserResponseDTO.getRoles());
//        claims.put("accessToken",angularUserResponseDTO.getAccessToken());
//        //使用JWT，生成令牌
//        // generateJwt() 參數要放入自訂義的訊息
//        String jwt = JwtUtils.generateJwt(claims);
//        log.info("--------------createNewJwt--工具類--------------");
//        log.info("-----成功為1個新用戶生成1個Jwt-----{}", jwt);
//        log.info("這是解析後的token-----createNewJwt-中----{}" , JwtUtils.parseJWT(jwt));
//        // 回傳1個新的jwt
//        return jwt;
//
//
//    }
//
//
//}
