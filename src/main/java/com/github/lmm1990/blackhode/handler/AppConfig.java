package com.github.lmm1990.blackhode.handler;

import com.github.lmm1990.blackhode.handler.disruptor.countDisruptor.CountEventProducer;
import com.github.lmm1990.blackhode.model.source.SourceData;
import com.github.lmm1990.blackhode.model.table.TableConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用配置
 */
public class AppConfig {

    /**
     * 程序根目录
     */
    public static final String rootPath = System.getProperty("user.dir").replace("\\", "/");

    /**
     * 配置文件目录
     * */
    public static final String configPath = String.format("%s/configure", rootPath);

    /**
     * 1天的毫秒数
     * */
    public static final int dayMilliseconds = 1000*24*60*60;

    /**
     * 数据表配置列表
     * */
    public static Map<String,TableConfig> tableConfigMap = new HashMap<>();

    /**
     * 数据源映射信息
     * */
    public static List<SourceData> sourceDataMapperList = new ArrayList<>();

    /**
     * 数量统计发布者
     * */
    public static CountEventProducer countEventProducer;
}
