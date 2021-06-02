package com.github.lmm1990.blackhode.handler.web.action;

import com.github.lmm1990.blackhode.handler.AppConfig;
import com.github.lmm1990.blackhode.handler.DataHandler;
import com.github.lmm1990.blackhode.handler.MonitorDataHandler;
import com.github.lmm1990.blackhode.utils.DateUtil;
import com.github.lmm1990.blackhode.utils.HtmlUtil;
import io.vertx.core.http.HttpServerRequest;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 输出监控信息
 * */
@Component
public class WriteMonitorInfo implements IAction{

    private static final String headExtend = "<style type=\"text/css\">table,th,td {border-collapse:collapse;border:1px solid #ddd;}table{width:100%;margin:0 auto;text-alien:center;}td,th{padding:5px 10px;width:33.33%;}</style>";

    @Override
    public String getMappingUrl(){
        return "";
    }

    @Override
    public String execute(HttpServerRequest request) {
        int day = (int) (System.currentTimeMillis() / AppConfig.dayMilliseconds);
        Date startStatisticsTime = new Date(MonitorDataHandler.startStatisticsTime);
        StringBuilder body = new StringBuilder();
        body.append(String.format("<h3>启动时间：%s</h3>", DateUtil.formatToString(startStatisticsTime, "yyyy-MM-dd HH:mm:ss")));
        body.append(writeRuningState());
        body.append(String.format("<h3>待入库数量：%d</h3>", DataHandler.countDataList.get(0).size()+DataHandler.countDataList.get(1).size()));
        body.append(String.format("<h3>待统计消息数：%d</h3>",MonitorDataHandler.kafkaConsumeCount.getOrDefault(day,new AtomicLong()).get()-MonitorDataHandler.statisticsCountRowCount.getOrDefault(day,new AtomicLong()).get()));
        body.append("<h3>kafka消费数量</h3>");
        body.append(getTableInfo(MonitorDataHandler.kafkaConsumeCount));
        body.append("<h3>统计行数量</h3>");
        body.append(getTableInfo(MonitorDataHandler.statisticsCountRowCount));
        body.append("<h3>统计数量</h3>");
        body.append(getTableInfo(MonitorDataHandler.statisticsCount));
        return HtmlUtil.getHtml("监控",headExtend,body);
    }

    /**
     * 输出运行状态
     */
    private StringBuilder writeRuningState(){
        StringBuilder html = new StringBuilder();
        html.append(String.format("<h3>状态：%s",MonitorDataHandler.runingStateMap.getOrDefault(MonitorDataHandler.runingState,"")));
        if(MonitorDataHandler.runingState==1){
            html.append("<a style=\"margin-left:10px;\" href=\"/stop_running\">停止运行</a>");
        }
        html.append("</h3>");
        return html;
    }

    /**
     * 查询统计表格数据
     *
     * @param dataMap 数据
     * */
    private StringBuilder getTableInfo(ConcurrentHashMap<Integer, AtomicLong> dataMap){
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
        return html;
    }
}
