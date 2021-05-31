package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import com.github.lmm1990.blackhode.model.source.SourceColumn;
import com.github.lmm1990.blackhode.utils.ConcurrentHashMapUtil;
import com.lmax.disruptor.EventHandler;

/**
 * 执行统计逻辑
 */
public class CountEventHandler implements EventHandler<CountEvent> {
    @Override
    public void onEvent(CountEvent event, long sequence, boolean endOfBatch) {
        int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
        AppConfig.sourceDataMapperList.forEach((item) -> {
            SourceColumn column;
            String key;
            for (int i = 0, length = item.getCountColumnList().size(); i < length; i++) {
                column = item.getCountColumnList().get(i);
                key = String.format("%s|%s|%s", event.getTableName(), event.getPrimaryKeyData(), column.getColumnName());
                long value = event.getColumnDataList().get(i);
                ConcurrentHashMapUtil.add(DataHandler.countDataList.get(DataHandler.dataVersion), key, value);
                ConcurrentHashMapUtil.add(MonitorDataHandler.statisticsCount, day, 1);
            }
        });
        ConcurrentHashMapUtil.add(MonitorDataHandler.statisticsCountRowCount, day, 1);
    }
}