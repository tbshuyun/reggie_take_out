package com.itheima.reggie.common;

/**
 * 基于Threadlocal封装的工具类,保存用户的ID
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
       return threadLocal.get();
    }
}
