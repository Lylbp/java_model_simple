package com.lylbp.manager.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author weiwenbin
 * @date 2020/9/1 上午11:28
 */
@ConfigurationProperties(prefix = "swagger")
@Component
@Data
public class SwaggerProperties {
    private Boolean enabled;
}
