package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import java.util.List;

public class CountEvent {

    /**
     * 数据表名
     * */
    private String tableName;

    /**
     * 列数据列表
     * */
    private List<Long> columnDataList;

    /**
     * 联合主键数据
     * */
    private String primaryKeyData;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Long> getColumnDataList() {
        return columnDataList;
    }

    public void setColumnDataList(List<Long> columnDataList) {
        this.columnDataList = columnDataList;
    }

    public String getPrimaryKeyData() {
        return primaryKeyData;
    }

    public void setPrimaryKeyData(String primaryKeyData) {
        this.primaryKeyData = primaryKeyData;
    }
}
