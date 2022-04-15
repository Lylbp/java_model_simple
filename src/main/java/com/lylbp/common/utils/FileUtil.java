package com.lylbp.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * FileUtil
 *
 * @author weiwenbin
 * @date 2020-03-31 09:53
 */
@Slf4j
public class FileUtil {
    /**
     * 通过文件名获取contentType
     *
     * @param fileName 文件名
     * @return String
     */
    public static String getContentType(String fileName) {
        String filenameExtension = fileName.substring(fileName.lastIndexOf('.'));

        if (".bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(filenameExtension) || ".jpg".equalsIgnoreCase(filenameExtension)) {
            return "image/jpeg";
        }
        if (".png".equalsIgnoreCase(filenameExtension)) {
            return "image/png";
        }
        if (".html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(filenameExtension) || ".ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(filenameExtension) || ".doc".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if (".xla".equalsIgnoreCase(filenameExtension) || ".xlc".equalsIgnoreCase(filenameExtension)
                || ".xlm".equalsIgnoreCase(filenameExtension) || ".xls".equalsIgnoreCase(filenameExtension)
                || ".xlt".equalsIgnoreCase(filenameExtension) || ".xlw".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-excel";
        }
        if (".xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        if (".pdf".equalsIgnoreCase(filenameExtension)) {
            return "application/pdf";
        }
        if (".zip".equalsIgnoreCase(filenameExtension)) {
            return "application/zip";
        }
        if (".tar".equalsIgnoreCase(filenameExtension)) {
            return "application/x-tar";
        }
        if (".avi".equalsIgnoreCase(filenameExtension)) {
            return "video/avi";
        }
        if (".mp4".equalsIgnoreCase(filenameExtension)) {
            return "video/mpeg4";
        }
        if (".mp3".equalsIgnoreCase(filenameExtension)) {
            return "audio/mp3";
        }
        if (".mp2".equalsIgnoreCase(filenameExtension)) {
            return "audio/mp2";
        }

        return "application/octet-stream";
    }

    /**
     * 以流形式打印文件
     *
     * @param inputStream io
     * @param contentType contentType
     * @param fileName    文件名
     * @param fileSize    大小
     * @param response    响应
     */
    public static void outByStream(InputStream inputStream, String contentType, String fileName, long fileSize,
                                   HttpServletResponse response) {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);
            //非图片下载
            if (!contentType.contains("image")) {
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            }

            //如果是视频或音频
            if (contentType.contains("video") || contentType.contains("audio")) {
                response.setContentType("application/octet-stream");
                response.setHeader("Pragma", "public");
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Cache-Control", "public");
                response.setHeader("Content-Transfer-Encoding", "binary");
                response.addHeader("Accept-Ranges", "bytes");
                response.addHeader("Content-Length", fileSize + "");
                response.addHeader("Content-Range", "bytes " + 0 + "-" + fileSize + "/" + fileSize);
            }

            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            IoUtil.copy(bufferedInputStream, bufferedOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 远程下载文件到本地
     *
     * @param fileUrl  url
     * @param savePath 保存地址
     * @return boolean
     */
    public static boolean downloadFile(String fileUrl, String savePath) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(savePath));
            IoUtil.copy(in, out);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 远程获取文件大小
     *
     * @param downloadUrl 下载地址
     * @return long
     */
    public static long getFileLength(String downloadUrl) throws IOException {
        Assert.notNull(downloadUrl, "downloadUrl not null");
        URL url = new URL(downloadUrl);
        URLConnection urlConnection = url.openConnection();
        return getFileLength(urlConnection);
    }

    /**
     * 远程获取文件大小
     *
     * @param urlConnection urlConnection
     * @return long
     */
    public static long getFileLength(URLConnection urlConnection) throws IOException {
        Assert.notNull(urlConnection, "urlConnection not null");

        urlConnection.setConnectTimeout(200 * 1000);
        urlConnection.setReadTimeout(200 * 1000);
        urlConnection.connect();
        return urlConnection.getContentLengthLong();
    }

    /**
     * 生成存储目录
     *
     * @param prefix 前缀
     * @param suffix 后追
     * @return String
     */
    public static String generateSavePath(String prefix, String suffix) {
        Assert.notNull(prefix, "prefix not null");
        Assert.notNull(suffix, "suffix not null");

        //格式日期
        String formatDateStr = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        //文件存储名称(带后缀)
        String saveFileName = IdUtil.simpleUUID() + "." + suffix;
        //文件存储目录
        return prefix + "/" + formatDateStr + "/" + saveFileName;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return String
     */
    public static String getSuffix(String fileName) {
        Assert.notNull(fileName, "fileName not null");
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 获取文件后缀名
     *
     * @param file MultipartFile
     * @return String
     */
    public static String getSuffix(MultipartFile file) {
        Assert.notNull(file, "file not null");
        return getSuffix(file.getOriginalFilename());
    }

    /**
     * 获取文件目录截取文件名
     *
     * @param filePath 文件路径
     * @return String
     */
    public static String getFileNameByFilePath(String filePath) {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }
}
