package com.github.lmm1990.blackhode.model.source;

/**
 * 源数据时间列
 * */
public class SourceDateTimeColumn extends SourceColumn{

    /**
     * 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     * */
    private String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
