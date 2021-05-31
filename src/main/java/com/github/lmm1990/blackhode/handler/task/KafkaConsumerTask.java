package com.github.lmm1990.blackhode.handler.task;

import com.github.lmm1990.blackhode.handler.KafkaConsumerClient;
import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerTask implements ITask {

    /**
     * kafka服务端url
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    /**
     * 分组id
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    /**
     * topic
     */
    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    /**
     * kafka topic分区数
     */
    private static final int partitionCount = 6;

    @Override
    public void execute() {
        MonitorDataHandler.startStatisticsTime = System.currentTimeMillis();
        for (int i = 0; i < partitionCount; i++) {
            new KafkaConsumerClient(kafkaServer,groupId,topicName,i).start();
        }
    }
}
