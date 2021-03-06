package com.github.lmm1990.blackhode.handler.task;

import com.alibaba.fastjson.JSONObject;
import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.disruptor.countDisruptor.Event;
import com.github.lmm1990.blackhode.handler.disruptor.countDisruptor.EventFactory;
import com.github.lmm1990.blackhode.handler.disruptor.countDisruptor.EventHandler;
import com.github.lmm1990.blackhode.handler.disruptor.countDisruptor.EventProducer;
import com.github.lmm1990.blackhode.utils.LogUtil;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.stereotype.Component;

/**
 * 计数统计disruptor生产者任务
 */
@Component
public class CountDisruptorProducerTask implements ITask {
    @Override
    public void execute() {
        // The factory for the event
        EventFactory factory = new EventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024*8;

        // Construct the Disruptor
        Disruptor<Event> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(new EventHandler());

        //记录disruptor 错误日志
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, Event event) {
                LogUtil.error(String.format("countEventProducer.handleEventException event:%s", JSONObject.toJSONString(event)),ex);
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                LogUtil.error("countEventProducer.handleOnStartException",ex);
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                LogUtil.error("countEventProducer.handleOnShutdownException",ex);
            }
        });
        AppConfig.eventProducer = new EventProducer(disruptor.start());
    }
}
