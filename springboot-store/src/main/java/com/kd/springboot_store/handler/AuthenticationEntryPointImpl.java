package com.kd.springboot_store.handler;

//import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson.JSON;
import com.kd.springboot_store.dto.ResponseResult;
import com.kd.springboot_store.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse
            response, AuthenticationException authException) throws IOException,
            ServletException
    {
        ResponseResult result = new
                ResponseResult(HttpStatus.UNAUTHORIZED.value(), "認證失敗請重新登入");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}
