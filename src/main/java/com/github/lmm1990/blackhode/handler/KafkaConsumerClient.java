package com.github.lmm1990.blackhode.handler;

import com.github.lmm1990.blackhode.utils.ConcurrentHashMapUtil;
import com.github.lmm1990.blackhode.utils.LogUtil;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * kafka消费者客户端
 */
public class KafkaConsumerClient extends Thread {

    /**
     * kafka服务端url
     */
    private String kafkaServer;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分区id
     */
    private int partitionId;

    /**
     * topic
     */
    private String topicName;

    /**
     * kafka消费者
     */
    private KafkaConsumer<String, String> consumer;

    public KafkaConsumerClient(String kafkaServer, String groupId, String topicName, int partitionId) {
        this.kafkaServer = kafkaServer;
        this.groupId = groupId;
        this.topicName = topicName;
        this.partitionId = partitionId;
    }

    @Override
    public void run() {
        Properties props = new Properties() {{
            put("bootstrap.servers", kafkaServer);
            put("key.deserializer", StringDeserializer.class.getName());
            put("value.deserializer", StringDeserializer.class.getName());
            put("group.id", groupId);

            // 自动提交offset,每1s提交一次（提交后的消息不再消费，避免重复消费问题）
            put("enable.auto.commit", "false");//自动提交offset:true【PS：只有当消息提交后，此消息才不会被再次接受到】
//            put("auto.commit.interval.ms", "1000");//自动提交的间隔
            put("max.poll.records", 500);
            /**
             * earliest： 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
             * latest： 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
             * none： topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
             */
            put("auto.offset.reset", "earliest ");//earliest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        }};
        //根据上面的配置，新增消费者对象
        consumer = new KafkaConsumer<>(props);
        consumer.assign(new ArrayList<>() {{
            add(new TopicPartition(topicName, partitionId));
        }});

        consume();
    }

    /**
     * 消费kafka数据
     */
    private void consume() {
        while (true) {
            if (MonitorDataHandler.runingState != 1) {
                consumer.close();
                LogUtil.info(String.format("topic:%s partition:%s consumer stoped",topicName,partitionId));
                break;
            }
            int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
            consumer.poll(Duration.ofSeconds(5)).forEach(record -> {
                AppConfig.countEventProducer.onData(record.value());
                ConcurrentHashMapUtil.add(MonitorDataHandler.kafkaConsumeCount, day, 1);
            });
            consumer.commitAsync();
        }
    }
}
