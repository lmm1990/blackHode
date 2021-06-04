package com.github.lmm1990.datasourceclient.handler;

import com.github.lmm1990.datasourceclient.util.DateUtil;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

@Component
public class KafkaProducerSetup implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Autowired
    private AdminClient adminClient;

    @Bean
    public AdminClient adminClient() {
        Map<String, Object> props = new HashMap<>();
        //配置Kafka实例的连接地址                                                                    //kafka的地址，不是zookeeper
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        return AdminClient.create(props);
    }

    @Override
    public void run(String... args) {
        //创建topic
        NewTopic topic = new NewTopic(AppConfig.topicName, AppConfig.partitionCount, (short) 1);
        adminClient.createTopics(Arrays.asList(topic));

        int dayCount = 5;
        int threadCount = 8;
        CountDownLatch countDownLatch = new CountDownLatch(dayCount*4);
        ExecutorService executor = new ThreadPoolExecutor(threadCount, threadCount,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        executor.execute(new Boss(countDownLatch));

        Date date = new Date();
        String dateStr;
        int index = 0;
        for (int i = 0; i < dayCount; i++) {
            dateStr = DateUtil.formatToString(date,"yyyy-MM-dd");
            // adSpace:1  pv:100W
            Worker w1 = new Worker(countDownLatch,kafkaTemplate,index++,dateStr,"上海",1,1,1,0,1000000);
            // adSpace:2  pv:100W
            Worker w2 = new Worker(countDownLatch,kafkaTemplate,index++,dateStr,"上海",1,2,1,0,1000000);

            // adSpace:1  click:10W
            Worker w3 = new Worker(countDownLatch,kafkaTemplate,index++,dateStr,"上海",1,1,0,1,100000);

            // adSpace:2  click:10W
            Worker w4 = new Worker(countDownLatch,kafkaTemplate,index++,dateStr,"上海",1,2,0,1,100000);

            executor.execute(w1);
            executor.execute(w2);
            executor.execute(w3);
            executor.execute(w4);
            date = DateUtil.addDays(date,1).getTime();
        }
        executor.shutdown();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Pless presss any key to continue.");
    }
}