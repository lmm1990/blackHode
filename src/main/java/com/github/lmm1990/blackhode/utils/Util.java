package com.github.lmm1990.blackhode.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 工具类
 * */
public class Util {

    /**
     * 判断是否是Windows系统
     *
     * @return Boolean
     */
    public static Boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }

    /**
     * 左补位，右对齐
     *
     * @param str         原字符串
     * @param length      目标字符串长度
     * @param paddingChar 补位字符
     * @return 目标字符串
     */
    public static String padLeft(String str, int length, char paddingChar) {
        StringBuilder result = new StringBuilder();
        for (int i = str.length(); i < length; i++) {
            result.append(paddingChar);
        }
        result.append(str);
        return result.toString();
    }

    /**
     * InputStream转换成byte数组
     *
     * @param stream InputStream
     * @return byte[]
     */
    public static byte[] streamToByteList(InputStream stream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int index;
        try {
            while ((index = stream.read(buff, 0, 100)) > 0) {
                outputStream.write(buff, 0, index);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
        return outputStream.toByteArray();
    }

    /**
     * url编码
     *
     * @param message 待编码的内容
     */
    public static String urlEncode(String message) {
        return URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    /**
     * url编码
     *
     * @param message 待编码的内容
     */
    public static String urlDecode(String message) {
        try {
            return URLDecoder.decode(message, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }
}
