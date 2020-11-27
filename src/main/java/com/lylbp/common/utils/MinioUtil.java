package com.lylbp.common.utils;

import com.lylbp.core.exception.ResResultException;
import com.lylbp.common.enums.ResResultEnum;
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
 * @Author weiwenbin
 * @Date 2020-03-23 14:32
 */
@Slf4j
public class MinioUtil {

    /**
     * 获取链接
     *
     * @param endpoint
     * @param accessKey
     * @param secretKey
     * @return
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
     * @param file
     * @param bucketName
     * @param fileName
     * @param saveFilePath
     * @param client
     * @return
     */
    public static String uploadFile(MultipartFile file, String bucketName, String fileName, String saveFilePath, MinioClient client) {
        InputStream inputStream = null;//文件流
        try {
            inputStream = file.getInputStream();
            String contentType = StringUtil.getContentType(fileName);//文件contentType
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
     * @param file
     * @param bucketName
     * @param saveFilePath
     * @param client
     * @return
     */
    public static String uploadFile(File file, String bucketName, String saveFilePath, MinioClient client) {
        InputStream inputStream = null;//文件流
        try {
            inputStream = new FileInputStream(file);
            String fileName = file.getName();
            String contentType = StringUtil.getContentType(fileName);//文件contentType
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
     * @param client
     * @param bucketName
     * @param saveFilePath
     * @return
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
     * @param client
     * @param bucketName
     * @param fileName
     * @return
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
     * @param client
     * @param bucketName
     * @param filePath
     * @param response
     */
    public static void outImgByStream(MinioClient client, String bucketName, String filePath, HttpServletResponse response) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String contentType = StringUtil.getContentType(fileName);//文件contentType
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
        } catch (IOException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | InvalidKeyException | ErrorResponseException | NoResponseException | InternalException e) {
            throw new ResResultException(ResResultEnum.RESOURCE_NOT_EXIT);
        } catch (XmlPullParserException e) {
            throw new ResResultException(ResResultEnum.RESOURCE_NOT_EXIT);
        }
    }

}
