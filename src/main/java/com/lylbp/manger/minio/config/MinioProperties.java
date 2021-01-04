package com.lylbp.manger.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProperties {
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 终端地址
     */
    private String endpoint;

    /**
     * 允许上传的图片类型
     */
    private List<String> allowImgContentType = new ArrayList<>(10);

    /**
     * 允许上传的视频类型
     */
    private List<String> allowVideoContentType = new ArrayList<>(10);

    /**
     * 允许上传的音频类型
     */
    private List<String> allowAudioContentType = new ArrayList<>(10);

    /**
     * 一律不准上传的后缀
     */
    private List<String> notAllow = new ArrayList<>(10);
}
