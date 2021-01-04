package com.lylbp.manager.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weiwenbin
 * @date 2020/9/1 上午11:28
 */
@ConfigurationProperties(prefix = "security")
@Component
@Data
public class SecurityProperties {
    /**
     * 是否开启
     */
    private Boolean enabled = false;

    /**
     * 不需要权限验证的静态资源
     */
    private List<String> allowStatic = new ArrayList<>();

    /**
     * 不需要权限验证的api
     */
    private List<String> allowApi = new ArrayList<>();
}
