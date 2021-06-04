package com.github.lmm1990.blackhode.model.source;

import java.util.List;

/**
 * 源数据
 * */
public class SourceData {

    /**
     * 表名
     * */
    private String tableName;

    /**
     * 时间列
     * */
    private SourceDateTimeColumn dateTimeColumn;

    /**
     * 联合主键列表
     * */
    private List<SourceColumn> primaryKeyList;

    /**
     * 数量统计列表
     * */
    private List<SourceColumn> countColumnList;
    /**
     * uv统计列表
     * */
    private List<SourceColumn> uvColumnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public SourceDateTimeColumn getDateTimeColumn() {
        return dateTimeColumn;
    }

    public void setDateTimeColumn(SourceDateTimeColumn dateTimeColumn) {
        this.dateTimeColumn = dateTimeColumn;
    }

    public List<SourceColumn> getPrimaryKeyList() {
        return primaryKeyList;
    }

    public void setPrimaryKeyList(List<SourceColumn> primaryKeyList) {
        this.primaryKeyList = primaryKeyList;
    }

    public List<SourceColumn> getCountColumnList() {
        return countColumnList;
    }

    public void setCountColumnList(List<SourceColumn> countColumnList) {
        this.countColumnList = countColumnList;
    }

    public List<SourceColumn> getUvColumnList() {
        return uvColumnList;
    }

    public void setUvColumnList(List<SourceColumn> uvColumnList) {
        this.uvColumnList = uvColumnList;
    }
}
