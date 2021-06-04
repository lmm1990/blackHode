package com.github.lmm1990.blackhode.handler.task;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import com.github.lmm1990.blackhode.model.UvData;
import com.github.lmm1990.blackhode.utils.ConcurrentHashMapUtil;
import com.github.lmm1990.blackhode.utils.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

/**
 * uv插入redis
 */
@Component
public class UvToRedisTask implements ITask {

    /**
     * 单次插入uid到redis数量
     */
    private static final int batchUidCount = 5000;

    /**
     * uv数据有效期
     */
    private static final Duration uvExpire = Duration.ofHours(25);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void execute() {
        new Thread(() -> {
            pollUid();
        }).start();
    }

    /**
     * 从队列取uid数据
     */
    private void pollUid() {
        Map<String, Set<String>> uvDataMap = new HashMap<>();
        int count = 0;
        while (true) {
            if (DataHandler.uvDataQueue.isEmpty()) {
                //uv插入redis
                uvToRedis(uvDataMap, new HashSet<>(),count);
                count = 0;
                //休眠1秒
                ThreadUtil.sleep(1000);
                continue;
            }
            final UvData uvData = DataHandler.uvDataQueue.poll();
            if (!uvDataMap.containsKey(uvData.getPrimaryKeyData())) {
                uvDataMap.put(uvData.getPrimaryKeyData(), new HashSet<>());
            }
            uvDataMap.get(uvData.getPrimaryKeyData()).add(uvData.getUid());
            count++;
            //uv插入redis
            if (uvDataMap.get(uvData.getPrimaryKeyData()).size() == batchUidCount) {
                uvToRedis(uvDataMap, new HashSet<>() {{
                    add(uvData.getPrimaryKeyData());
                }},count);
                count = 0;
            }
            //uid数据=单次入库数量，插入redis
            if(count==batchUidCount){
                uvToRedis(uvDataMap, new HashSet<>(),count);
                count = 0;
            }
        }
    }

    /**
     * uv数据存储到redis中
     */
    private void uvToRedis(Map<String, Set<String>> uvDataMap, Collection<String> keyList,int queueCount) {
        Collection<String> canRemoveKeySet = new HashSet<>();
        int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
        uvDataMap.forEach((k, v) -> {
            //key不为空 且 不包含key跳过
            if (!keyList.isEmpty() && !keyList.contains(k)) {
                return;//continue
            }
            redisTemplate.opsForSet().add(k, v.toArray(String[]::new));
            redisTemplate.expire(k, uvExpire);
            canRemoveKeySet.add(k);
        });
        ConcurrentHashMapUtil.add(MonitorDataHandler.uvDataToRedisCount,day,queueCount);
        canRemoveKeySet.forEach((item)->{
            uvDataMap.remove(item);
        });
    }
}
