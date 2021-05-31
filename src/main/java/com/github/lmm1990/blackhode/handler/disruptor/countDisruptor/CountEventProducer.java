package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import com.lmax.disruptor.RingBuffer;

public class CountEventProducer {

    private final RingBuffer<CountEvent> ringBuffer;
    private final CountEventProducerWithTranslator translator;

    public CountEventProducer(RingBuffer<CountEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
        this.translator = new CountEventProducerWithTranslator();
    }

    public void onData(String data)
    {
        ringBuffer.publishEvent(translator,data);
    }
}