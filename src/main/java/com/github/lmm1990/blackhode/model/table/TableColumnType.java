package com.github.lmm1990.blackhode.model.table;

/**
 * 列类型
 * */
public enum TableColumnType {

    /**
     * 定长字符串
     * */
    CHAR,

    /**
     * 变长字符串
     * */
    VARCHAR,

    /**
     * 长文本数据
     * */
    TEXT,

    /**
     * 时间戳
     * */
    TIMESTAMP,

    /**
     * 时间
     * */
    DATETIME,

    /**
     * 日期
     * */
    DATE,

    /**
     * 时间
     * */
    TIME,

    /**
     * 整数
     * */
    INT,

    /**
     * 浮点数
     * */
    FLOAT;
}
