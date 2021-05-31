package com.github.lmm1990.blackhode.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程安全map工具类
 */
public class ConcurrentHashMapUtil {

    /**
     * AtomicLong value增加
     */
    public static <T> void add(ConcurrentHashMap<T, AtomicLong> data, T key, long value) {
        if (data.containsKey(key)) {
            data.get(key).getAndAdd(value);
            return;
        }
        //没有设置成功和value不一致，需要+value
        if (data.computeIfAbsent(key, (k) -> new AtomicLong(value)).get() != value) {
            data.get(key).addAndGet(value);
        }
    }
}
