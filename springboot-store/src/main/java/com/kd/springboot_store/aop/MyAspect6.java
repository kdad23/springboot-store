package com.kd.springboot_store.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class MyAspect6
{

//    @Pointcut("@annotation(com.jason.springboot_mall.aop.RenewJwt)")
    private void pt()
    {

    }

    @Before("pt()")
    public void xxxxxxxxxxxx()
    {
        log.info("AOP啟用-------------------");
    }


}
