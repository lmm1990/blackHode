package com.github.lmm1990.blackhode.model.table;

/**
 * 分表类型
 * */
public enum TableShardingType {

    /**
     * 按年分表
     * */
    YEAR,

    /**
     * 按月分表
     * */
    MONTH,

    /**
     * 不分表
     * */
    NONE
}
