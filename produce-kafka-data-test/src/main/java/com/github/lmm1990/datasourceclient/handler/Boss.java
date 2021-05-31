package com.github.lmm1990.datasourceclient.handler;

import java.util.concurrent.CountDownLatch;

public class Boss implements Runnable{

    private CountDownLatch countDownLatch;

    public Boss(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        System.out.println("正在初始化数据......");
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试数据初始化完毕");
    }
}
