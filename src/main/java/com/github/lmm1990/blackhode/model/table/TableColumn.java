package com.github.lmm1990.blackhode.model.table;

/**
 * 列
 * */
public class TableColumn {

    /**
     * 列名
     * */
    private String name;

    /**
     * 列类型
     * */
    private TableColumnType type;

    /**
     * 长度
     * */
    private int length;

    /**
     * 小数位长度
     * */
    private int decimalLength;

    /**
     * 备注
     * */
    private String comment;

    /**
     * 是否是自增列
     * */
    private boolean autoIncrement;

    /**
     * 是否是主键
     * */
    private boolean primaryKey;

    /**
     * 是否不为null
     * */
    private boolean notNull;

    /**
     * 默认值
     * */
    private String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableColumnType getType() {
        return type;
    }

    public void setType(TableColumnType type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDecimalLength() {
        return decimalLength;
    }

    public void setDecimalLength(int decimalLength) {
        this.decimalLength = decimalLength;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}