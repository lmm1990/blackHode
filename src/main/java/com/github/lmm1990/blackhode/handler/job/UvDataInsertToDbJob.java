package com.github.lmm1990.blackhode.handler.job;

import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import com.github.lmm1990.blackhode.model.InsertToDbTableData;
import com.github.lmm1990.blackhode.service.TableService;
import com.github.lmm1990.blackhode.utils.TableSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * uv数据插入数据库，每分钟执行一次
 */
@Component
public class UvDataInsertToDbJob {

    @Autowired
    private TableService tableService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void run() {
        //停止运行后不用入库
        if (MonitorDataHandler.runingState == 3) {
            return;
        }

        //待插入数据表的数据
        Map<String, InsertToDbTableData> tableDataMap = new HashMap<>();
        //k:表名|主键数据|列名
        redisTemplate.keys("*").forEach((k) -> {
            String[] baseData = k.split("\\|");
            if (!tableDataMap.containsKey(baseData[0])) {
                tableDataMap.put(baseData[0], new InsertToDbTableData() {{
                    setTableName(baseData[0]);
                }});
            }
            if (!tableDataMap.get(baseData[0]).getRowDataMap().containsKey(baseData[1])) {
                tableDataMap.get(baseData[0]).getRowDataMap().put(baseData[1], new HashMap<>());
            }

            final long uvCount = redisTemplate.opsForSet().size(k);
            tableDataMap.get(baseData[0]).getRowDataMap().get(baseData[1]).put(baseData[2], uvCount);
        });
        TableSqlUtil.insertDataToDb(tableDataMap.values(), tableService);
    }
}
