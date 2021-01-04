package com.lylbp.manager.minio.service;

import com.lylbp.manager.minio.config.MinioProperties;
import com.lylbp.manager.minio.enums.FileEnum;
import io.minio.MinioClient;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author weiwenbin
 * @date 2020/12/11 下午3:02
 */
public interface MinioService {
    /**
     * 获取 MinioClient
     *
     * @return MinioClient
     */
    MinioClient getMinioClient();

    /**
     * 设置MinioClient
     *
     * @param minioClient minioClient
     */
    void setMinioClient(MinioClient minioClient);

    /**
     * 获取MinioProperties
     *
     * @return MinioProperties
     */
    MinioProperties getMinioProperties();

    /**
     * 设置MinioProperties
     *
     * @param minioProperties minioProperties
     */
    void setMinioProperties(MinioProperties minioProperties);


    /**
     * 上传文件
     *
     * @param inputStream io流水
     * @param contentType 文件contentType
     * @param suffix      后缀 例: 1.jpg 后缀为jpg
     * @param bucketName  桶名
     * @param savePath    保存在minio桶中的文件目录
     * @return String
     */
    String uploadFile(InputStream inputStream, String contentType, String suffix, String bucketName, String savePath);

    /**
     * 上传文件
     *
     * @param file        文件
     * @param contentType 文件contentType
     * @param bucketName  桶名
     * @return String
     */
    String uploadFile(File file, String contentType, String bucketName) throws IOException;

    /**
     * 上传文件
     *
     * @param file        文件
     * @param contentType 文件contentType
     * @param bucketName  桶名
     * @return String
     */
    String uploadFile(MultipartFile file, String contentType, String bucketName) throws IOException;

    /**
     * 获取文件流
     *
     * @param bucketName   桶名
     * @param saveFilePath 保存地址
     * @return InputStream
     */
    InputStream getFileStream(String bucketName, String saveFilePath);

    /**
     * 获取图片真实访问地址(有效1天)
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     * @param expires    过期时长(秒)
     * @return String
     */
    String presignedGetObject(String bucketName, String fileName, Integer expires);

    /**
     * 向浏览器输出流
     *
     * @param bucketName 桶名
     * @param filePath   文件在minio中的存储地址
     * @param response   响应
     */
    void outImgByStream(String bucketName, String filePath, HttpServletResponse response);

    /**
     * 检测是否允许上传
     *
     * @param suffix      后缀 例: 1.jpg 后缀为jpg
     * @param contentType 文件contentType
     * @return Boolean
     */
    Boolean checkAllowUpload(String suffix, String contentType);

    /**
     * 通过文件contentType获取文件类型
     *
     * @param contentType 文件contentType
     * @return FileEnum
     */
    FileEnum getFileEnumByContentType(String contentType);

    /**
     * 通过文件contentType生成存储地址
     *
     * @param contentType 文件contentType
     * @param suffix      后缀 例: 1.jpg 后缀为jpg
     * @return String
     */
    String generateSavePath(String contentType, String suffix);
}
