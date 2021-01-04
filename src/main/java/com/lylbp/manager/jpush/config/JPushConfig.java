package com.lylbp.manager.jpush.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author weiwenbin
 * @date 2020/5/28 上午9:32
 */
@Data
@Configuration
public class JPushConfig {
    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.masterKey}")
    private String masterKey;
}
