package com.lylbp.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 *
 * @Author weiwenbin
 * @Date 2020/9/1 上午11:28
 */
@ConfigurationProperties(prefix = "project")
@Component
@Data
public class ProjectProperties {
    /**
     * 日志存储地址
     */
    private String logSavePath = "";

    /**
     * 超级管理员id
     */
    private String superAdminId = "";
}
