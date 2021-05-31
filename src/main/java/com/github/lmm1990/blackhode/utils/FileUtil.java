package com.github.lmm1990.blackhode.utils;

import java.io.*;
import java.util.List;

/**
 * Created by lmm on 2015/11/9.
 */
public class FileUtil {

    /**
     * 读取文件所有内容
     *
     * @param fileName 文件名
     * @return String 文件内容
     */
    public static String readAllText(String fileName) {
        if (!exists(fileName, false)) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                str.append(line);
                str.append("\r\n");
            }
        } catch (Exception ex) {
            LogUtil.error(String.format("加截文件失败：%s", fileName), ex);
            return "";
        }
        if (str.indexOf("\r\n") > -1) {
            int index = str.lastIndexOf("\r\n");
            str.delete(index, index + 4);
        }
        return str.toString();
    }

    /**
     * 读取文件所有内容
     *
     * @param inputStream 文件流
     * @return String 文件内容
     */
    public static String readAllText(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            int length = sb.length();
            if (length > 0) {
                sb.delete(length - 2, length - 1);
            }
        } catch (IOException e) {

        }
        return sb.toString();
    }

    /**
     * 写文件
     *
     * @param fileName 文件名
     * @return String 文件内容
     */
    public static boolean writeAllText(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            writer.write(content);
            return true;
        } catch (Exception ex) {
            LogUtil.error(String.format("写入文本到文件失败：%s", fileName), ex);
            return false;
        }
    }

    /**
     * 验证文件是否存在
     *
     * @param fileName    文件名
     * @param isDirectory 是否是目录，默认值：false
     * @return Boolean
     */
    public static Boolean exists(String fileName, Boolean isDirectory) {
        if (isDirectory == null) {
            isDirectory = false;
        }
        if (isDirectory) {
            File file = new File(fileName);
            return file.exists() && file.isDirectory();
        }
        return new File(fileName).exists();
    }

    /**
     * 未找到目录则创建
     *
     * @param fileName 文件名
     */
    public static void notExistsDirectoryCreate(String fileName) {
        if (!exists(fileName, true)) {
            new File(fileName).mkdirs();
        }
    }

    /**
     * 获得文件后缀名，包含点
     *
     * @param fileName 文件名
     * @return String 文件后缀名
     */
    public static String getExtension(String fileName) {
        if (fileName == null || fileName.length() < 1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    public static boolean delete(String fileName) {
        return new File(fileName).delete();
    }

    /**
     * 写文件
     *
     * @param fileName 文件名
     * @return byteList 文件内容
     */
    public static boolean writeAllByteList(String fileName, byte[] byteList) {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileName))) {
            writer.write(byteList);
            return true;
        } catch (Exception ex) {
            LogUtil.error(String.format("写入文件失败：%s", fileName).toString(), ex);
            return false;
        }
    }

    /**
     * 读取文件所有内容
     *
     * @param fileName 文件名
     * @return String 文件内容
     */
    public static byte[] readAllByteList(String fileName) {
        try (FileInputStream stream = new FileInputStream(fileName)) {
            return Util.streamToByteList(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * 递归读取目录所有文件
     *
     * @param file 目录
     * @param filePathList 文件路径列表
     * */
    public static List<String> readAllFileNamePath(File file, List<String> filePathList) {
        File[] files = file.listFiles();
        if (files == null) {
            return filePathList;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                readAllFileNamePath(f, filePathList);
            } else {
                filePathList.add(f.getPath());
            }
        }
        return filePathList;
    }

    /**
     * 获得文件后缀名，包含点
     *
     * @param fileName 文件名
     * @return String 文件后缀名
     */
    public static String getFileName(String fileName) {
        if (fileName == null || fileName.length() < 1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("/")+1);
    }
}
