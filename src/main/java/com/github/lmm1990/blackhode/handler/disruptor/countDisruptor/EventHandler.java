package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import com.github.lmm1990.blackhode.model.UvData;
import com.github.lmm1990.blackhode.model.source.SourceColumn;
import com.github.lmm1990.blackhode.utils.ConcurrentHashMapUtil;

/**
 * 执行统计逻辑
 */
public class EventHandler implements com.lmax.disruptor.EventHandler<Event> {
    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) {
        int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
        AppConfig.sourceDataMapperList.forEach((item) -> {
            if (!event.getTableName().contains(item.getTableName())) {
                return;//continue
            }
            SourceColumn column;
            String key;
            String baseKey = String.format("%s|%s", event.getTableName(), event.getPrimaryKeyData());
            for (int i = 0, length = item.getCountColumnList().size(); i < length; i++) {
                column = item.getCountColumnList().get(i);
                key = String.format("%s|%s", baseKey, column.getColumnName());
                final long value = event.getCountColumnDataList().get(i);
                ConcurrentHashMapUtil.add(DataHandler.countDataList.get(DataHandler.dataVersion), key, value);
                ConcurrentHashMapUtil.add(MonitorDataHandler.statisticsCount, day, 1);
            }
            int uvDataWaitToRedisCount = 0;
            for (int i = 0, length = item.getUvColumnList().size(); i < length; i++) {
                column = item.getUvColumnList().get(i);
                final String uvKey = String.format("%s|%s", baseKey, column.getColumnName());
                final String uid = event.getUvColumnDataList().get(i);
                uvDataWaitToRedisCount++;
                DataHandler.uvDataQueue.offer(new UvData() {{
                    setPrimaryKeyData(uvKey);
                    setUid(uid);
                }});
            }
            ConcurrentHashMapUtil.add(MonitorDataHandler.uvDataStatisticsCount, day, uvDataWaitToRedisCount);
        });
        ConcurrentHashMapUtil.add(MonitorDataHandler.statisticsCountRowCount, day, 1);
    }
}