package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * 发布者
 * */
public class EventProducer {

    private final RingBuffer<Event> ringBuffer;
    private final EventProducerWithTranslator translator;

    public EventProducer(RingBuffer<Event> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
        this.translator = new EventProducerWithTranslator();
    }

    public void onData(String data)
    {
        ringBuffer.publishEvent(translator,data);
    }
}