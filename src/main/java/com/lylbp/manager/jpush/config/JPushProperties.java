package com.lylbp.manager.jpush.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jpush")
@Component
@Data
public class JPushProperties {
    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * appKey
     */
    private String appKey;

    /**
     * masterKey
     */
    private String masterKey;
}
