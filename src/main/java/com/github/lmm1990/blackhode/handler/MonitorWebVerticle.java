package com.github.lmm1990.blackhode.handler;

import com.alibaba.fastjson.JSON;
import com.github.lmm1990.blackhode.utils.DateUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 监控web模块
 */
@Component
public class MonitorWebVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {

        vertx.createHttpServer().requestHandler(req -> {
            System.out.println("ss");
            req.response()
                    .putHeader("content-type", "text/html;charset=utf-8")
                            .end(writeMonitorInfo());
        }).exceptionHandler((e) -> {
            e.printStackTrace();
        }).listen(9999, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    /**
     * 输出监控信息
     * */
    private String writeMonitorInfo() {
        int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
        Date startStatisticsTime = new Date(MonitorDataHandler.startStatisticsTime);
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><title>监控</title>");
        html.append("<style type=\"text/css\">table,th,td {border-collapse:collapse;border:1px solid #ddd;}table{width:100%;margin:0 auto;}td,th{padding:5px 10px;width:33.33%;}</style></head><body>");
        html.append(String.format("<h3>启动时间：%s</h3>", DateUtil.formatToString(startStatisticsTime, "yyyy-MM-dd HH:mm:ss")));
        html.append(String.format("<h3>待入库数量：%d</h3>",DataHandler.countDataList.get(0).size()+DataHandler.countDataList.get(1).size()));
        html.append(String.format("<h3>待统计消息数：%d</h3>",MonitorDataHandler.kafkaConsumeCount.getOrDefault(day,new AtomicLong()).get()-MonitorDataHandler.statisticsCountRowCount.getOrDefault(day,new AtomicLong()).get()));
        html.append("<h3>kafka消费数量</h3>");
        html.append(getTableInfo(MonitorDataHandler.kafkaConsumeCount));
        html.append("<h3>统计行数量</h3>");
        html.append(getTableInfo(MonitorDataHandler.statisticsCountRowCount));
        html.append("<h3>统计数量</h3>");
        html.append(getTableInfo(MonitorDataHandler.statisticsCount));
        html.append("</body></html>");
        return html.toString();
    }

    /**
     * 查询统计表格数据
     *
     * @param dataMap 数据
     * */
    private String getTableInfo(ConcurrentHashMap<Integer, AtomicLong> dataMap){
        StringBuilder html = new StringBuilder();
        html.append("<table><tr><th>日期</th><th>总数量</th><th>qps</th></tr><tr>");
        List<Integer> dayList = dataMap.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        boolean firstRow = true;
        int qps;
        Date date;
        for (int day: dayList){
            if(firstRow){
                qps = (int)((System.currentTimeMillis() - MonitorDataHandler.startStatisticsTime)/1000);
                qps = (int)(dataMap.get(day).get()/qps);
                firstRow = false;
            }else {
                qps = (int)(dataMap.get(day).get()/AppConfig.dayMilliseconds/1000);
            }
            date = new Date((long) day*AppConfig.dayMilliseconds);
            html.append(String.format("<td>%s</td>",DateUtil.formatToString(date,"yyyy-MM-dd")));
            html.append(String.format("<td>%d</td>",dataMap.get(day).get()));
            html.append(String.format("<td>%d</td>",qps));
        }
        html.append("</tr></table>");
        return html.toString();
    }
}