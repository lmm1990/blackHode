package com.github.lmm1990.blackhode.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 入库数据
 * */
public class InsertToDbTableData {

    /**
     * 表名
     * */
    private String tableName;

    /**
     * 行数据map[主键列数据,map[统计的列名，统计数据值]]
     * */
    private Map<String,Map<String,Long>> rowDataMap = new HashMap<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Map<String, Long>> getRowDataMap() {
        return rowDataMap;
    }

    public void setRowDataMap(Map<String, Map<String, Long>> rowDataMap) {
        this.rowDataMap = rowDataMap;
    }
}
