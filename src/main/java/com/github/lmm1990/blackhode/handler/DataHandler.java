package com.github.lmm1990.blackhode.handler;

import com.github.lmm1990.blackhode.model.UvData;
import com.github.lmm1990.blackhode.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class DataHandler {

    /**
     * 数量版本号
     */
    public static volatile int dataVersion = 0;

    /**
     * 数量统计数据列表
     */
    public static List<ConcurrentHashMap<String, AtomicLong>> countDataList = new ArrayList<>(2) {{
        add(new ConcurrentHashMap<>());
        add(new ConcurrentHashMap<>());
    }};

    /**
     * uv数据队列
     * */
    public static ConcurrentLinkedQueue<UvData> uvDataQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis()/1000/24/60/60);
        System.out.println(DateUtil.formatToString(new Date(18777L*1000*24*60*60),"yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
