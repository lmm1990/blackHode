package com.github.lmm1990.blackhode.utils;

/**
 * 线程工具类
 * */
public class ThreadUtil {

    /**
     * 休眠
     *
     * @param millis 毫秒数
     * */
    public static void sleep(long millis){
        try{
            Thread.sleep(millis);
        }catch (Exception e){
            LogUtil.error("ThreadUtil.sleep error",e);
        }
    }
}
