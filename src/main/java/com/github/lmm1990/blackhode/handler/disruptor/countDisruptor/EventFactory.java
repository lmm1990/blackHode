package com.github.lmm1990.blackhode.handler.disruptor.countDisruptor;

/**
 * 对象工厂
 * */
public class EventFactory implements com.lmax.disruptor.EventFactory<Event> {
    @Override
    public Event newInstance() {
        return new Event();
    }
}