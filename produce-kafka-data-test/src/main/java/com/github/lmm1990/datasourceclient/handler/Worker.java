package com.github.lmm1990.datasourceclient.handler;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable{

    private CountDownLatch downLatch;
    KafkaTemplate<String,String> kafkaTemplate;
    private String date;
    private String area;
    private int siteId;
    private int adSpaceId;
    private int pv;
    private int click;
    private int dataCount;
    private int index;
    private List<String> uidList = new ArrayList<>();
    private static final int uidCount = 50;

    public Worker(CountDownLatch downLatch,KafkaTemplate<String,String> kafkaTemplate,int index, String date,String area,int siteId,int adSpaceId,int pv,int click,int dataCount){
        this.downLatch = downLatch;
        this.kafkaTemplate = kafkaTemplate;
        this.index = index;
        this.date = date;
        this.area = area;
        this.siteId = siteId;
        this.adSpaceId = adSpaceId;
        this.pv = pv;
        this.click = click;
        this.dataCount = dataCount;
        for (int i = 0; i < uidCount; i++) {
            uidList.add(UUID.randomUUID().toString().replace("-","").toLowerCase());
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < this.dataCount; i++) {
            kafkaTemplate.send(AppConfig.topicName,i%AppConfig.partitionCount,"",String.format("%s|%s|%d|%d|%d|%d|%s",date,area,siteId,adSpaceId,pv,click,uidList.get(new Random().nextInt(uidCount))));
        }
        System.out.println(String.format("下标：%d，测试数据初始化完毕！",this.index));
        this.downLatch.countDown();
    }
}