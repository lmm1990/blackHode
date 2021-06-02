package com.github.lmm1990.blackhode.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 监控数据
 * */
public class MonitorDataHandler {

    /**
     * 统计开始时间
     * */
    public static long startStatisticsTime;

    /**
     * 运行状态：1、运行中，2、停止中，3、已停止
     * */
    public static volatile short runingState = 1;

    /**
     * 运行状态
     * */
    public static final Map<Short,String> runingStateMap = new HashMap<>(){{
        put((short) 1,"<span style=\"color:#409EFF\">运行中</span>");
        put((short)2,"<span style=\"color:#E6A23C\">停止中</span>");
        put((short)3,"<span style=\"color:#F56C6C\">已停止</span>");
    }};

    /**
     * kafka消费数量map[天,数量]
     * */
    public static ConcurrentHashMap<Integer, AtomicLong> kafkaConsumeCount = new ConcurrentHashMap<>();

    /**
     * 统计行数量map[天,数量]
     * */
    public static ConcurrentHashMap<Integer,AtomicLong> statisticsCountRowCount = new ConcurrentHashMap<>();

    /**
     * 统计数量map[天,数量]
     * */
    public static ConcurrentHashMap<Integer,AtomicLong> statisticsCount = new ConcurrentHashMap<>();
}
