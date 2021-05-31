package com.github.lmm1990.datasourceclient.handler;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
    }

    @Override
    public void run() {
        for (int i = 0; i < this.dataCount; i++) {
            kafkaTemplate.send(AppConfig.topicName,i%AppConfig.partitionCount,"",String.format("%s|%s|%d|%d|%d|%d",date,area,siteId,adSpaceId,pv,click));
        }
        System.out.println(String.format("下标：%d，测试数据初始化完毕！",this.index));
        this.downLatch.countDown();
    }
}