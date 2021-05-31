package com.github.lmm1990.blackhode.handler;

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
