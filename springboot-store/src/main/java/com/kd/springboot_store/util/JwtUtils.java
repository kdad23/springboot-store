package com.kd.springboot_store.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

// 生成Jwt 的工具類
public class JwtUtils {
    private static String signKey = "goodtoken";//簽名密鑰
    //    private static Long expire = 43200000L; //有效時間
    private static Long expire = 3600L * 1000L; //有效時間 1個小時


    /**
     * 生成JWT令牌
     *
     * @param claims JWT第二部分 payload 中儲存的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)//自定義信息（有效載荷）
                .signWith(SignatureAlgorithm.HS256, signKey)//簽名算法（頭部）
                .setExpiration(new Date(System.currentTimeMillis() + expire))//過期的時間
                .compact();

        return jwt;
    }


    /**
     * 解析JWT令牌
     *
     * @param jwt JWT令牌
     * @return JWT第二部分 payload 中儲存的内容
     */
    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)//指定簽名密鑰
                .parseClaimsJws(jwt)//放入要解析的指定令牌Token
                .getBody();


        return claims;
    }

}
