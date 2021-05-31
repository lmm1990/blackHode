package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.model.source.SourceData;
import com.github.lmm1990.blackhode.model.table.TableConfig;
import com.github.lmm1990.blackhode.utils.DateUtil;
import com.github.lmm1990.blackhode.utils.SafeConvertUtil;
import com.github.lmm1990.blackhode.utils.TableSqlUtil;
import com.lmax.disruptor.EventTranslatorOneArg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountEventProducerWithTranslator implements EventTranslatorOneArg<CountEvent, String> {

    @Override
    public void translateTo(CountEvent event, long sequence, String data) {
        String[] baseData = data.split("\\|");
        List<Long> columnDataList = new ArrayList<>();
        List<String> primaryKeyList = new ArrayList<>();
        Date date;
        TableConfig tableConfig;
        for (SourceData item : AppConfig.sourceDataMapperList){
            tableConfig = AppConfig.tableConfigMap.getOrDefault(item.getTableName(),null);
            if(tableConfig==null){
                throw new RuntimeException(String.format("数据表：%s 未正确配置",item.getTableName()));
            }
            item.getPrimaryKeyList().forEach((primaryKey)->{
                primaryKeyList.add(baseData[primaryKey.getIndex()]);
            });
            item.getCountColumnList().forEach((column)->{
                columnDataList.add(SafeConvertUtil.toLong(baseData[column.getIndex()]));
            });
            date = DateUtil.parse(baseData[item.getDateTimeColumn().getIndex()],item.getDateTimeColumn().getDateFormat());
            event.setTableName(TableSqlUtil.computeTableName(item.getTableName(),tableConfig.getTableShardingType(),date));
        }
        AppConfig.sourceDataMapperList.forEach((item)->{

        });

        event.setPrimaryKeyData(String.join("','",primaryKeyList));
        event.setColumnDataList(columnDataList);
    }
}