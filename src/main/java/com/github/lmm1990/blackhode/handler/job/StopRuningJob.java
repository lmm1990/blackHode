package com.github.lmm1990.blackhode.handler.job;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 停止运行，每5秒执行一次
 */
@Component
public class StopRuningJob {

    @Autowired
    private UvDataInsertToDbJob uvDataInsertToDbJob;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void run() {
        if (MonitorDataHandler.runingState != 2) {
            return;
        }
        int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
        //uv数据待插入redis数量
        long uvDataWaitToRedisCount = MonitorDataHandler.uvDataStatisticsCount.getOrDefault(day, new AtomicLong()).get() -
                MonitorDataHandler.uvDataToRedisCount.getOrDefault(day, new AtomicLong()).get();
        //待统计数量
        long waitStatisticsCount = MonitorDataHandler.kafkaConsumeCount.getOrDefault(day, new AtomicLong()).get() -
                MonitorDataHandler.statisticsCountRowCount.getOrDefault(day, new AtomicLong()).get();
        //待入库数量
        long waitInsertToDbCount = DataHandler.countDataList.get(0).size() + DataHandler.countDataList.get(1).size();
        //数据全部插入redis && 已统计已入库
        if (uvDataWaitToRedisCount==0 && waitStatisticsCount==0 && waitInsertToDbCount==0) {
            //安全起见，uv再统计一遍
            uvDataInsertToDbJob.run();
            MonitorDataHandler.runingState = 3;
        }
    }
}
