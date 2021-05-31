package com.github.lmm1990.blackhode.handler.job;

import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.model.InsertToDbTableData;
import com.github.lmm1990.blackhode.model.table.TableConfig;
import com.github.lmm1990.blackhode.service.TableService;
import com.github.lmm1990.blackhode.utils.TableSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计数据插入数据库，每分钟执行一次
 */
@Component
public class InsertToDbJob {

    @Autowired
    private TableService tableService;

    /**
     * 每次插入数据的行数
     * */
    private int everyTimeInsertRowCount = 500;

    @Scheduled(cron = "0 0/1 * * * ? ")
    @PostConstruct
    public void run() {
        int baseVersion = DataHandler.dataVersion;
        DataHandler.dataVersion = baseVersion == 1 ? 0 : 1;

        //无数据需要插入
        if(DataHandler.countDataList.get(baseVersion).isEmpty()){
            //清空已插入数据表的数据
            DataHandler.countDataList.get(baseVersion).clear();
            return;
        }

        //待插入数据表的数据
        Map<String, InsertToDbTableData> tableDataMap = new HashMap<>();

        //k:表名|主键数据|列名
        DataHandler.countDataList.get(baseVersion).forEach((k,v)->{
            String[] baseData = k.split("\\|");
            if(!tableDataMap.containsKey(baseData[0])){
                tableDataMap.put(baseData[0],new InsertToDbTableData(){{
                    setTableName(baseData[0]);
                }});
            }
            if(!tableDataMap.get(baseData[0]).getRowDataMap().containsKey(baseData[1])){
                tableDataMap.get(baseData[0]).getRowDataMap().put(baseData[1],new HashMap<>());
            }
            tableDataMap.get(baseData[0]).getRowDataMap().get(baseData[1]).put(baseData[2], v.get());
        });
        //清空已插入数据表的数据
        DataHandler.countDataList.get(baseVersion).clear();

        formatInsertData(tableDataMap.values());
    }

    /**
     * 格式化插入数据
     * */
    private void formatInsertData(Collection<InsertToDbTableData> tableDataList){
        int[] count = new int[]{0};
        StringBuilder insertData = new StringBuilder();
        for (InsertToDbTableData tableData : tableDataList){
            TableConfig tableConfig = TableSqlUtil.getTableConfigByTableName(tableData.getTableName());
            tableData.getRowDataMap().forEach((primaryKeyData,columnValue)->{
                //先记录主键列数据
                insertData.append(String.format("('%s',",primaryKeyData));
                tableConfig.getColumnList().forEach((column)->{
                    //记录统计列数据
                    if(!column.isPrimaryKey()){
                        insertData.append(String.format("%d,",columnValue.getOrDefault(column.getName(),0L)));
                    }
                });
                insertData.delete(insertData.length()-1,insertData.length());
                insertData.append("),");
                count[0]++;
                if(count[0]==everyTimeInsertRowCount){
                    insertDataToDb(tableConfig,tableData.getTableName(),insertData);
                    count[0] = 0;
                    insertData.delete(0,insertData.length());
                }
            });

            //数据表剩余数据入库
            if(insertData.length()>0){
                insertDataToDb(tableConfig,tableData.getTableName(),insertData);
                count[0] = 0;
                insertData.delete(0,insertData.length());
            }
        }
    }

    /**
     * 插入数据到数据库
     * */
    private void insertDataToDb(TableConfig tableConfig,String finalTableName,StringBuilder insertData){
        insertData.delete(insertData.length()-1,insertData.length());

        StringBuilder insertSql = new StringBuilder();
        insertSql.append(TableSqlUtil.generateInsertSqlField(tableConfig,finalTableName));
        insertSql.append(String.format(" values%s ON DUPLICATE KEY UPDATE ",insertData));

        tableConfig.getColumnList().forEach((item)->{
            if(!item.isPrimaryKey()){
                insertSql.append(String.format("%s=%s+VALUES(%s),",item.getName(),item.getName(),item.getName()));
            }
        });
        //删除最后一个,
        insertSql.delete(insertSql.length()-1,insertSql.length());

        //入库
        tableService.insert(insertSql.toString());
    }
}
