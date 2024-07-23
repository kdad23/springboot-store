package com.kd.springboot_store.dao;

@FunctionalInterface
public interface BeanCopyUtilCallBack <S,T>
{
    void callBack(S t, T s);
}
