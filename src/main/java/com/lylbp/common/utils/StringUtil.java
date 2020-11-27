package com.lylbp.common.utils;

import cn.hutool.core.util.ObjectUtil;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {
    public static final String getContentType(String fileName) {
        String FilenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/png";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xla") ||
                FilenameExtension.equalsIgnoreCase(".xlc") ||
                FilenameExtension.equalsIgnoreCase(".xlm") ||
                FilenameExtension.equalsIgnoreCase(".xls") ||
                FilenameExtension.equalsIgnoreCase(".xlt") ||
                FilenameExtension.equalsIgnoreCase(".xlw")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase(".tar")) {
            return "application/x-tar";
        }
        if (FilenameExtension.equalsIgnoreCase(".avi")) {
            return "video/avi";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp2")) {
            return "audio/mp2";
        }
        return "application/octet-stream";
    }

    public static String addHost(String filePath, HttpServletRequest request, String apiUrl) {
        String httpTag = "http";
        String refererURL = request.getHeader("https-tag");
        if (ObjectUtil.isNotEmpty(refererURL)) {
            httpTag = refererURL.toLowerCase();
        }

        String url = httpTag + "://" + request.getServerName() + ":" + request.getServerPort() + apiUrl;
        return url + filePath;
    }

    public static String getRemoteIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }
}
