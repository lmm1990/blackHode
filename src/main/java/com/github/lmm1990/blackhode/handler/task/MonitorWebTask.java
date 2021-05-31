package com.github.lmm1990.blackhode.handler.task;

import com.github.lmm1990.blackhode.handler.MonitorWebVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监控web
 */
@Component
public class MonitorWebTask implements ITask {

    @Autowired
    MonitorWebVerticle monitorWebVerticle;

    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(monitorWebVerticle);
    }
}
