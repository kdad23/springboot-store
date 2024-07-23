package com.kd.springboot_store.config;

import com.kd.springboot_store.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Autowired
    private Interceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/angular/users/login")
                .excludePathPatterns("/angular/users/register")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/angular/note/**")
                .excludePathPatterns("/angular/notes/**")
                .excludePathPatterns("/v3/**");


        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
