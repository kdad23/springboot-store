package com.kd.springboot_store.filter;

import com.kd.springboot_store.model.LoginUser;

import com.kd.springboot_store.util.JwtUtil;
import com.kd.springboot_store.util.RedisCache;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
//OncePerRequestFilter特点是在处理单个HTTP请求时确保过滤器的 doFilterInternal 方法只被调用一次
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1.在请求头中获取token
        String token = request.getHeader("token");
        //此处需要判断token是否为空
        if (!StringUtils.hasText(token)){
            //没有token放行 此时的SecurityContextHolder没有用户信息 会被后面的过滤器拦截
            System.out.println("這裡是在JwtAuthenticationTokenFilter裡面========沒有拿到JWT========-----");

            filterChain.doFilter(request,response);
            // 沒有token在這裡放行給FilterSecurityInterceptor
            return;
        }

        //2.解析token获取用户id
        String subject;
        try
        {
            Claims claims = JwtUtil.parseJWT(token);
            // 拿到userId
            subject = claims.getSubject();

        }
        catch (Exception e)
        {
            //解析失敗
            throw new RuntimeException("token非法");
        }
        //3.在redis中获取用户信息 注意：redis中的key是login：+userId
        String redisKey = "login:" + subject;
        LoginUser loginUser = (LoginUser) redisCache.getCacheObject(redisKey);

        //此处需要判断loginUser是否为空
        if (Objects.isNull(loginUser))
        {
            throw new RuntimeException("用户未登入");
        }
        //4.将获取到的用户信息存入SecurityContextHolder 参数（用户信息，，权限信息），用三個參數的構造器，可以讓後面的過濾器知道已經認證了
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //5.放行
        filterChain.doFilter(request,response);

    }
}
