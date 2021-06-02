package com.github.lmm1990.blackhode.handler.web.action;

import io.vertx.core.http.HttpServerRequest;

public interface IAction {

    public String getMappingUrl();

    public String execute(HttpServerRequest request);
}
