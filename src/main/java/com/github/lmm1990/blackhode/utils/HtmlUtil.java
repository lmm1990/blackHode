package com.github.lmm1990.blackhode.utils;

/**
 * html工具类
 * */
public class HtmlUtil {

    /**
     * 获得html
     *
     * @param title 标题
     * @param headExtend head扩展字符串
     * @param body html body
     * */
    public static String getHtml(String title,String headExtend,StringBuilder body){
        StringBuilder html = new StringBuilder();
        html.append(String.format("<!DOCTYPE html><html><head><title>%s</title>",title));
        html.append(headExtend);
        html.append("</head><body>");
        html.append(body);
        html.append("</body></html>");
        return html.toString();
    }
}
