package com.lylbp.manger.minio.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.FileUtil;
import com.lylbp.core.exception.ResResultException;
import com.lylbp.manger.minio.config.MinioProperties;
import com.lylbp.manger.minio.enums.FileEnum;
import com.lylbp.manger.minio.service.MinioService;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * minio上传service
 *
 * @author weiwenbin
 * @date 2020/12/12 下午3:02
 */
@Slf4j
public class MinioServiceImpl implements MinioService {
    /**
     * minio配置项目
     */
    private MinioProperties minioProperties;

    /**
     * client
     */
    private MinioClient minioClient;

    @Override
    public MinioClient getMinioClient() {
        return minioClient;
    }

    @Override
    public void setMinioClient(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public MinioProperties getMinioProperties() {
        return minioProperties;
    }

    @Override
    public void setMinioProperties(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }


    @Override
    public String uploadFile(InputStream inputStream, String contentType, String suffix, String bucketName, String savePath) {
        //检查是否能上传
        if (!checkAllowUpload(suffix, contentType)) {
            throw new ResResultException(ResResultEnum.UPLOAD_RESOURCE_SUFFIX_ERROR);
        }

        //文件流
        try {
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                minioClient.makeBucket(bucketName);
            }

            minioClient.putObject(bucketName, savePath, inputStream, inputStream.available(), contentType);

            return savePath;
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.UPLOAD_FAIL);
        }
    }

    @Override
    public String uploadFile(MultipartFile file, String contentType, String bucketName) throws IOException {
        String suffix = FileUtil.getSuffix(file);
        InputStream inputStream = file.getInputStream();

        return uploadFile(inputStream, contentType, suffix, bucketName, generateSavePath(contentType, suffix));
    }

    @Override
    public String uploadFile(File file, String contentType, String bucketName) throws IOException {
        String suffix = FileUtil.getSuffix(file.getName());
        InputStream inputStream = new FileInputStream(file);

        return uploadFile(inputStream, contentType, suffix, bucketName, generateSavePath(contentType, suffix));
    }

    @Override
    public InputStream getFileStream(String bucketName, String saveFilePath) {
        InputStream inputStream;
        try {
            inputStream = minioClient.getObject(bucketName, saveFilePath);
            return inputStream;
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage());
        }
    }

    @Override
    public String presignedGetObject(String bucketName, String filePath, Integer expires) {
        String objectUrl;
        try {
            minioClient.statObject(bucketName, filePath);
            objectUrl = minioClient.presignedGetObject(bucketName, filePath, expires);
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage());
        }

        return objectUrl;
    }

    @Override
    public void outImgByStream(String bucketName, String filePath, HttpServletResponse response) {
        String fileName = FileUtil.getFileNameByFilePath(filePath);
        //文件contentType
        String contentType = FileUtil.getContentType(fileName);
        try {
            String objUrl = presignedGetObject(bucketName, filePath, 60 * 60);
            URL url = new URL(objUrl);
            URLConnection urlConnection = url.openConnection();
            long fileSize = FileUtil.getFileLength(urlConnection);
            InputStream inputStream = urlConnection.getInputStream();
            //以流形式输出
            FileUtil.outByStream(inputStream, contentType, fileName, fileSize, response);
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.RESOURCE_NOT_EXIT);
        }
    }

    @Override
    public Boolean checkAllowUpload(String suffix, String contentType) {
        FileEnum fileEnum = getFileEnumByContentType(contentType);
        //检测是否时不允许上传的类型
        List<String> notAllowList = minioProperties.getNotAllow();
        if (notAllowList.contains(suffix)) {
            return false;
        }

        //检测是否是允许上传的文件类型
        List<String> allowContentType;
        switch (fileEnum) {
            case IMG:
                allowContentType = minioProperties.getAllowImgContentType();
                break;
            case VIDEO:
                allowContentType = minioProperties.getAllowVideoContentType();
                break;
            case AUDIO:
                allowContentType = minioProperties.getAllowAudioContentType();
                break;
            case OTHER:
                return true;
            default:
                throw new ResResultException(ResResultEnum.SYSTEM_ERR);
        }

        if (allowContentType.size() == 0) {
            return true;
        }

        return allowContentType.contains(contentType);
    }

    @Override
    public FileEnum getFileEnumByContentType(String contentType) {
        if (StrUtil.containsIgnoreCase(contentType, "image")) {
            return FileEnum.IMG;
        } else if (StrUtil.containsIgnoreCase(contentType, "video")) {
            return FileEnum.VIDEO;
        } else if (StrUtil.containsIgnoreCase(contentType, "audio")) {
            return FileEnum.AUDIO;
        } else {
            return FileEnum.OTHER;
        }
    }

    @Override
    public String generateSavePath(String contentType, String suffix) {
        FileEnum fileEnum = getFileEnumByContentType(contentType);
        return FileUtil.generateSavePath(fileEnum.getName(), suffix);
    }
}
