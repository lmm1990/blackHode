package com.github.lmm1990.blackhode.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private final static Logger infoLog = LoggerFactory.getLogger("infoLog");
    private final static Logger errorLog = LoggerFactory.getLogger("errorLog");
    private final static Logger debugLog = LoggerFactory.getLogger("debugLog");

    public static void debug(String msg){
        debugLog.debug(msg);
    }

    public static void error(String remark, Throwable e) {
        StackTraceElement[] messageList = e.getStackTrace();
        StringBuilder errorStr = new StringBuilder();
        errorStr.append(String.format("%s\r\n", remark));
        for (StackTraceElement item : messageList) {
            errorStr.append(String.format("类名：%s\r\n", item.getClassName()));
            errorStr.append(String.format("文件名：%s\r\n", item.getFileName()));
            errorStr.append(String.format("行号：%s\r\n", item.getLineNumber()));
            errorStr.append(String.format("方法名：%s\r\n", item.getMethodName()));
            errorStr.append(String.format("信息：%s\r\n", item.toString()));
        }
        errorStr.append(e.toString());
        errorLog.error(errorStr.toString());
    }

    public static void error(String remark) {
        errorLog.error(remark);
    }

    public static void error(String remark,Object... args) {
        error(String.format(remark,args));
    }

    public static void info(String info) {
        infoLog.info(info);
    }

    public static void info(String info,Object... args) {
        info(String.format(info, args));
    }
}
