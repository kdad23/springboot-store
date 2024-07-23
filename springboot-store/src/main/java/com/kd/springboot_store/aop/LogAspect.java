package com.kd.springboot_store.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@Aspect
public class LogAspect
{

    @Around("@annotation(com.jason,springboot_mall.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Objects result= (Objects) joinPoint.proceed();
        return  result;
    }

}
