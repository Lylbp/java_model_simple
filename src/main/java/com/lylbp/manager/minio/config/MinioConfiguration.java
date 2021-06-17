package com.lylbp.manager.minio.config;

import com.lylbp.manager.minio.service.MinioService;
import com.lylbp.manager.minio.service.impl.MinioServiceImpl;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * minio配置
 *
 * @author weiwenbin
 * @date 2020/12/11 下午3:23
 */
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "minio", name = "enabled")
public class MinioConfiguration {
    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioService initMinioService() throws InvalidPortException, InvalidEndpointException {

        MinioService minioService = new MinioServiceImpl();
        minioService.setMinioProperties(minioProperties);

        MinioClient minioClient = new MinioClient(
                minioProperties.getEndpoint(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
        minioService.setMinioClient(minioClient);

        return minioService;
    }
}
