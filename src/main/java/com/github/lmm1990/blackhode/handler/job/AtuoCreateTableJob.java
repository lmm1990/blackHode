package com.github.lmm1990.blackhode.handler.job;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.model.InsertToDbTableData;
import com.github.lmm1990.blackhode.model.table.TableColumn;
import com.github.lmm1990.blackhode.model.table.TableConfig;
import com.github.lmm1990.blackhode.service.TableService;
import com.github.lmm1990.blackhode.utils.DateUtil;
import com.github.lmm1990.blackhode.utils.TableSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 自动创建数据表，每天凌晨2点执行
 */
@Component
public class AtuoCreateTableJob {

    @Autowired
    private TableService tableService;

    @Scheduled(cron = "0 0 2 1/1 * ? ")
    public void run() {
        validationTable();
    }

    /**
     * 验证数据表
     */
    private void validationTable() {
        Map<String, TableConfig> baseTableMap = new HashMap<>();
        AppConfig.tableConfigMap.forEach((baseTableName, item) -> {
            Calendar calendar = Calendar.getInstance();
            String tableName = TableSqlUtil.computeTableName(baseTableName, item.getTableShardingType(),calendar);
            baseTableMap.put(tableName, item);
            switch (item.getTableShardingType()) {
                case YEAR:
                    calendar = DateUtil.addYears(calendar,1);
                    break;
                case MONTH:
                    calendar = DateUtil.addMonths(calendar,1);
            }
            //下一年/月表名
            tableName = TableSqlUtil.computeTableName(baseTableName, item.getTableShardingType(),calendar);
            baseTableMap.put(tableName, item);
        });
        List<String> existsTableList = tableService.getExistsTableList(baseTableMap.keySet());
        baseTableMap.forEach((tableName, config) -> {
            if (!existsTableList.contains(tableName)) {
                createTable(config, tableName);
            }
        });
    }

    /**
     * 创建数据表
     *
     * @param config         数据表配置信息
     * @param finalTableName 最终数据表名称
     */
    private void createTable(TableConfig config, String finalTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("CREATE TABLE `%s`  (\n", finalTableName));
        List<String> primaryKeyList = new ArrayList<>();
        boolean hasAutoIncrement = false;
        for (TableColumn item : config.getColumnList()) {
            if (!hasAutoIncrement) {
                hasAutoIncrement = item.isAutoIncrement();
            }
            if (item.isPrimaryKey()||item.isAutoIncrement()) {
                primaryKeyList.add(item.getName());
            }
            sql.append(TableSqlUtil.computeColumnStr(item));
        }
        sql.delete(sql.length() - 1, sql.length());

        if (!primaryKeyList.isEmpty()) {
            sql.append(String.format(",  PRIMARY KEY (`%s`) USING BTREE ", String.join("`,`", primaryKeyList)));
        }

        sql.append(") ENGINE = InnoDB ");
        if (hasAutoIncrement) {
            sql.append("AUTO_INCREMENT = 1");
        }
        sql.append(" CHARACTER SET = utf8 COLLATE = utf8_general_ci ");
        if (!config.getTableComment().isBlank()) {
            sql.append(String.format("COMMENT = '%s' ", config.getTableComment()));
        }
        sql.append("ROW_FORMAT = Dynamic;");
        tableService.insert(sql.toString());
    }
}
