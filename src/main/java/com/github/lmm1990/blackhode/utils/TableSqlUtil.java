package com.github.lmm1990.blackhode.utils;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.model.InsertToDbTableData;
import com.github.lmm1990.blackhode.model.table.TableColumn;
import com.github.lmm1990.blackhode.model.table.TableConfig;
import com.github.lmm1990.blackhode.model.table.TableShardingType;
import com.github.lmm1990.blackhode.service.TableService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * 数据表sql工具类
 */
public class TableSqlUtil {

    /**
     * 计算数据表名
     *
     * @param tableName 表名
     * @param type      分表类型
     * @param date      时间
     */
    public static String computeTableName(String tableName, TableShardingType type, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return computeTableName(tableName, type, calendar);
    }

    /**
     * 计算数据表名
     *
     * @param tableName 表名
     * @param type      分表类型
     * @param calendar  时间
     */
    public static String computeTableName(String tableName, TableShardingType type, Calendar calendar) {
        switch (type) {
            case YEAR:
                return String.format("%s_%s", tableName, DateUtil.getYear(calendar));
            case MONTH:
                return String.format("%s_%s", tableName, DateUtil.formatToString(calendar.getTime(), "yyyyMM"));
            default:
                return tableName;
        }
    }

    /**
     * 计算创建数据表-列sql
     *
     * @param column 列配置
     */
    public static String computeColumnStr(TableColumn column) {
        StringBuilder columnSql = new StringBuilder();
        columnSql.append(String.format("`%s` ", column.getName()));
        switch (column.getType()) {
            case INT:
                columnSql.append("int(11) ");
                break;
            case CHAR:
                columnSql.append(String.format("char(%d) CHARACTER SET utf8 COLLATE utf8_general_ci ", column.getLength()));
                break;
            case DATE:
                columnSql.append("date ");
                break;
            case TEXT:
                columnSql.append("text CHARACTER SET utf8 COLLATE utf8_general_ci ");
                break;
            case TIME:
                columnSql.append("time(6) ");
                break;
            case FLOAT:
                columnSql.append(String.format("float(%d, %d) ", column.getLength(), column.getDecimalLength()));
                break;
            case VARCHAR:
                columnSql.append(String.format("varchar(%d) CHARACTER SET utf8 COLLATE utf8_general_ci ", column.getLength()));
                break;
            case DATETIME:
                columnSql.append("datetime(6) ");
                break;
            case TIMESTAMP:
                columnSql.append("timestamp(6) ");
                break;
        }
        if (column.isAutoIncrement()) {
            columnSql.append("AUTO_INCREMENT ");
        }
        columnSql.append(column.isNotNull() ? "NOT NULL " : "NULL ");
        if(column.getDefaultValue()!=null){
            columnSql.append(String.format("DEFAULT %s ",column.getDefaultValue()));
        }
        if (!column.getComment().isBlank()) {
            columnSql.append(String.format("COMMENT '%s'", column.getComment()));
        }
        columnSql.append(",");
        return columnSql.toString();
    }

    /**
     * 根据表名获得数据表配置
     *
     * @param tableName 表名
     * */
    public static TableConfig getTableConfigByTableName(String tableName){
        int shardingStartIndex = tableName.lastIndexOf("_");
        TableConfig tableConfig;
        //未分表，且表名没有_的情况
        if (shardingStartIndex == -1) {
            tableConfig = AppConfig.tableConfigMap.get(tableName);
        } else {
            String baseTableName = tableName.substring(0, shardingStartIndex);
            tableConfig = AppConfig.tableConfigMap.get(baseTableName);
        }
        //兼容未分表，且表名有下划线的情况
        if (tableConfig == null) {
            tableConfig = AppConfig.tableConfigMap.get(tableName);
        }
        return tableConfig;
    }

    /**
     * 生成插入sql字段部分
     *
     * @param tableConfig 数据表配置
     * @param finalTableName 最终的数据表名
     * */
    public static String generateInsertSqlField(TableConfig tableConfig,String finalTableName){
        StringBuilder primaryKeyField = new StringBuilder();
        StringBuilder field = new StringBuilder();
        tableConfig.getColumnList().forEach((item)->{
            if(item.isPrimaryKey()){
                primaryKeyField.append(String.format("%s,",item.getName()));
            }else {
                field.append(String.format("%s,",item.getName()));
            }
        });

        StringBuilder sql = new StringBuilder();
        sql.append(String.format("insert into %s(",finalTableName));
        sql.append(primaryKeyField);
        sql.append(field);
        sql.delete(sql.length()-1,sql.length());
        sql.append(")");
        return sql.toString();
    }

    /**
     * 每次插入数据的行数
     */
    private static final int everyTimeInsertRowCount = 500;

    /**
     * 格式化插入数据
     */
    public static void insertDataToDb(Collection<InsertToDbTableData> tableDataList,TableService tableService) {
        int[] count = new int[]{0};
        StringBuilder insertData = new StringBuilder();
        for (InsertToDbTableData tableData : tableDataList) {
            TableConfig tableConfig = TableSqlUtil.getTableConfigByTableName(tableData.getTableName());
            tableData.getRowDataMap().forEach((primaryKeyData, columnValue) -> {
                //先记录主键列数据
                insertData.append(String.format("('%s',", primaryKeyData));
                tableConfig.getColumnList().forEach((column) -> {
                    //记录统计列数据
                    if (!column.isPrimaryKey()) {
                        insertData.append(String.format("%d,", columnValue.getOrDefault(column.getName(), 0L)));
                    }
                });
                insertData.delete(insertData.length() - 1, insertData.length());
                insertData.append("),");
                count[0]++;
                if (count[0] == everyTimeInsertRowCount) {
                    insertDataToDb(tableConfig, tableData.getTableName(), insertData,tableService);
                    count[0] = 0;
                    insertData.delete(0, insertData.length());
                }
            });

            //数据表剩余数据入库
            if (insertData.length() > 0) {
                insertDataToDb(tableConfig, tableData.getTableName(), insertData,tableService);
                count[0] = 0;
                insertData.delete(0, insertData.length());
            }
        }
    }

    /**
     * 插入数据到数据库
     */
    private static void insertDataToDb(TableConfig tableConfig, String finalTableName, StringBuilder insertData, TableService tableService) {
        insertData.delete(insertData.length() - 1, insertData.length());

        StringBuilder insertSql = new StringBuilder();
        insertSql.append(TableSqlUtil.generateInsertSqlField(tableConfig, finalTableName));
        insertSql.append(String.format(" values%s ON DUPLICATE KEY UPDATE ", insertData));

        tableConfig.getColumnList().forEach((item) -> {
            if (item.isPrimaryKey()) {
                return;//continue
            }
            //uv统计字段，uv数需要覆盖
            if(AppConfig.uvFieldMap.getOrDefault(tableConfig.getTableName(),new HashSet<>()).contains(item.getName())){
                insertSql.append(String.format("%s=VALUES(%s),", item.getName(), item.getName()));
            }else if (!item.isPrimaryKey()) {
                insertSql.append(String.format("%s=%s+VALUES(%s),", item.getName(), item.getName(), item.getName()));
            }
        });
        //删除最后一个,
        insertSql.delete(insertSql.length() - 1, insertSql.length());

        //入库
        tableService.insert(insertSql.toString());
    }
}
