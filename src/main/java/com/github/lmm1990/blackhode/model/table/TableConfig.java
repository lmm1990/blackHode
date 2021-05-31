package com.github.lmm1990.blackhode.model.table;

import java.util.List;

/**
 * 数据表配置
 * */
public class TableConfig {

    /**
     * 数据库名称
     * */
    private String dbName;

    /**
     * 数据表名称
     * */
    private String tableName;

    /**
     * 数据表备注
     * */
    private String tableComment;

    /**
     * 分表类型
     * */
    private TableShardingType tableShardingType;

    /**
     * 表字段列表
     * */
    private List<TableColumn> columnList;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public TableShardingType getTableShardingType() {
        return tableShardingType;
    }

    public void setTableShardingType(TableShardingType tableShardingType) {
        this.tableShardingType = tableShardingType;
    }

    public List<TableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<TableColumn> columnList) {
        this.columnList = columnList;
    }
}
