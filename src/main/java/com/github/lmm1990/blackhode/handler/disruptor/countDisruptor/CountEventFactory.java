package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

import com.lmax.disruptor.EventFactory;

public class CountEventFactory implements EventFactory<CountEvent> {
    @Override
    public CountEvent newInstance() {
        return new CountEvent();
    }
}