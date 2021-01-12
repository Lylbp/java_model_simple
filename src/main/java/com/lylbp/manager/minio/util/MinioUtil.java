package com.lylbp.manager.minio.util;

import com.lylbp.common.exception.ResResultException;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.FileUtil;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MinioUtil
 *
 * @author weiwenbin
 * @date 2020-03-23 14:32
 */
@Slf4j
public class MinioUtil {

    /**
     * 获取链接
     *
     * @param endpoint  地址
     * @param accessKey accessKey
     * @param secretKey secretKey
     * @return MinioClient
     */
    public static MinioClient getClient(String endpoint, String accessKey, String secretKey) {
        MinioClient minioClient;
        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException | InvalidPortException e) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage());
        }

        return minioClient;
    }

    /**
     * 上传文件
     *
     * @param file         文件
     * @param bucketName   桶名
     * @param fileName     文件名
     * @param saveFilePath 保存地址
     * @param client       client
     * @return String
     */
    public static String uploadFile(MultipartFile file, String bucketName, String fileName, String saveFilePath, MinioClient client) {
        //文件流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //文件contentType
            String contentType = FileUtil.getContentType(fileName);
            // 检查存储桶是否已经存在
            boolean isExist = client.bucketExists(bucketName);
            if (!isExist) {
                client.makeBucket(bucketName);
            }

            client.putObject(bucketName, saveFilePath, inputStream, inputStream.available(), contentType);

            return saveFilePath;
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.UPLOAD_FAIL);
        }
    }


    /**
     * 上传文件
     *
     * @param file         文件
     * @param bucketName   桶名
     * @param saveFilePath 保存地址
     * @param client       client
     * @return String
     */
    public static String uploadFile(File file, String bucketName, String saveFilePath, MinioClient client) {
        //文件流
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            String fileName = file.getName();
            //文件contentType
            String contentType = FileUtil.getContentType(fileName);
            // 检查存储桶是否已经存在
            boolean isExist = client.bucketExists(bucketName);
            if (!isExist) {
                client.makeBucket(bucketName);
            }

            client.putObject(bucketName, saveFilePath, inputStream, inputStream.available(), contentType);

            return saveFilePath;
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.UPLOAD_FAIL);
        }
    }

    /**
     * 获取文件流
     *
     * @param client       client
     * @param bucketName   桶名
     * @param saveFilePath 保存地址
     * @return InputStream
     */
    public static InputStream getFileStream(MinioClient client, String bucketName, String saveFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = client.getObject(bucketName, saveFilePath);
            return inputStream;
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage());
        }
    }

    /**
     * 获取图片真实访问地址(有效1天)
     *
     * @param client     client
     * @param bucketName 桶名
     * @param fileName   文件名
     * @return String
     */
    public static String presignedGetObject(MinioClient client, String bucketName, String fileName) {
        String objectUrl;
        try {
            objectUrl = client.presignedGetObject(bucketName, fileName, 60 * 60 * 24);
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage());
        }

        return objectUrl;
    }

    /**
     * 向浏览器输出流
     *
     * @param client     client
     * @param bucketName 桶名
     * @param filePath   文件名
     * @param response   响应
     */
    public static void outImgByStream(MinioClient client, String bucketName, String filePath, HttpServletResponse response) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        //文件contentType
        String contentType = FileUtil.getContentType(fileName);
        try {
            client.statObject(bucketName, filePath);
            String objUrl = presignedGetObject(client, bucketName, filePath);
            URL url = new URL(objUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(200 * 1000);
            urlConnection.setReadTimeout(200 * 1000);
            urlConnection.connect();
            long fileSize = urlConnection.getContentLengthLong();

            FileUtil.outByStream(urlConnection.getInputStream(), contentType, fileName, fileSize, response);
        } catch (IOException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | InvalidKeyException | ErrorResponseException | NoResponseException | InternalException | XmlPullParserException e) {
            throw new ResResultException(ResResultEnum.RESOURCE_NOT_EXIT);
        }
    }

}
