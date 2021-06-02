package com.github.lmm1990.blackhode.handler.web.action;

import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import com.github.lmm1990.blackhode.utils.HtmlUtil;
import io.vertx.core.http.HttpServerRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StopRunningAction implements IAction {

    private static final String headExtend = "<script type=\"text/javascript\">window.location.href=\"/\";</script>";

    @Override
    public String getMappingUrl() {
        return "/stop_running";
    }

    @Override
    public String execute(HttpServerRequest request) {
        MonitorDataHandler.runingState = 2;
        return HtmlUtil.getHtml("停止运行",headExtend,new StringBuilder());
    }
}
