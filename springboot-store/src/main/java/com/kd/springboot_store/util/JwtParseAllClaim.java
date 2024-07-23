package com.kd.springboot_store.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

// 解析Jwt中使用者資訊的工具類
//public class JwtParseAllClaim
//{
//    public static AngularUserResponseDTO getWhoIsTheUser(HttpServletRequest req)
//    {
//
//        String jwt=null;
//
//        Cookie[] mapForCookie= req.getCookies();
//
//        for(int i=0; i< mapForCookie.length; i++)
//        {
//            if(mapForCookie[i].getName().equals("tokenFromJava"))
//            {
//                // 從Cookie中獲取已生成的jwt
//                // 把 存在Cookie中的token賦值給jwt
//                jwt=mapForCookie[i].getValue();
//            }
//        }
//
//        JwtUtils.parseJWT(jwt);
//
//        // 也可以單獨獲取某個claim
//        Integer userId = JwtUtils.parseJWT(jwt).get("userId", Integer.class);
//        String email = JwtUtils.parseJWT(jwt).get("email", String.class);
//        Date createdDate = JwtUtils.parseJWT(jwt).get("createdDate", Date.class);
//        Date lastModifiedDate = JwtUtils.parseJWT(jwt).get("lastModifiedDate", Date.class);
//        String roles = JwtUtils.parseJWT(jwt).get("roles", String.class);
//        String accessToken = JwtUtils.parseJWT(jwt).get("accessToken", String.class);
//
//
//        AngularUserResponseDTO angularUserResponseDTO=new AngularUserResponseDTO();
//
//        angularUserResponseDTO.setUserId(userId);
//        angularUserResponseDTO.setEmail(email);
//        angularUserResponseDTO.setCreatedDate(createdDate);
//        angularUserResponseDTO.setLastModifiedDate(lastModifiedDate);
//        angularUserResponseDTO.setRoles(roles);
//        angularUserResponseDTO.setAccessToken(accessToken);
//
//        return angularUserResponseDTO;
//
//    }
//
//}
