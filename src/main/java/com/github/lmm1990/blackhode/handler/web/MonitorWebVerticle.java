package com.github.lmm1990.blackhode.handler.web;

import com.github.lmm1990.blackhode.handler.web.action.IAction;
import com.github.lmm1990.blackhode.utils.LogUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 监控web模块
 */
@Component
public class MonitorWebVerticle extends AbstractVerticle {

    private static final int serverPort = 9999;

    private Map<String, IAction> actionMap = new HashMap<>();

    @Autowired
    public MonitorWebVerticle(Map<String, IAction> baseActionMap) {
        baseActionMap.forEach((k, item) -> {
            actionMap.put(item.getMappingUrl(), item);
        });
    }

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer().requestHandler(req -> {
            String url = req.uri();
            if (!actionMap.containsKey(url)) {
                url = "";
            }
            req.response()
                    .putHeader("content-type", "text/html;charset=utf-8")
                    .end(actionMap.get(url).execute(req));
        }).exceptionHandler((e) -> {
            e.printStackTrace();
            LogUtil.error("vertx error", e);
        }).listen(serverPort, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println(String.format("HTTP server started on port %d", serverPort));
            } else {
                startPromise.fail(http.cause());
            }
        });
    }
}