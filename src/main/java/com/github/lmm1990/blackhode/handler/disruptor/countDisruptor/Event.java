package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import java.util.List;

public class Event {

    /**
     * 数据表名
     * */
    private String tableName;

    /**
     * 计数列数据列表
     * */
    private List<Long> countColumnDataList;

    /**
     * 联合主键数据
     * */
    private String primaryKeyData;

    /**
     * uv列数据列表
     */
    private List<String> uvColumnDataList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Long> getCountColumnDataList() {
        return countColumnDataList;
    }

    public void setCountColumnDataList(List<Long> countColumnDataList) {
        this.countColumnDataList = countColumnDataList;
    }

    public String getPrimaryKeyData() {
        return primaryKeyData;
    }

    public void setPrimaryKeyData(String primaryKeyData) {
        this.primaryKeyData = primaryKeyData;
    }

    public List<String> getUvColumnDataList() {
        return uvColumnDataList;
    }

    public void setUvColumnDataList(List<String> uvColumnDataList) {
        this.uvColumnDataList = uvColumnDataList;
    }
}
