package com.github.lmm1990.blackhode.utils;

/**
 * 安全的类型转换类
 * */
public class SafeConvertUtil {

    /**
     * 字符串转换成long类型
     * */
    public static long toLong(String value){
        if(value==null||value.isBlank()){
            return 0;
        }
        try{
            return Long.parseLong(value);
        }catch (Exception e){
            return 0;
        }
    }
}
