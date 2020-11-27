package com.lylbp.manger.jush.config;

import cn.jpush.api.JPushClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author weiwenbin
 * @Date 2020/5/28 上午10:03
 */
@Component
public class JPushClientFactory {
    @Resource
    private JPushConfig jPushConfig;

    @Bean
    public JPushClient getJPushClient() {
        return new JPushClient(jPushConfig.getMasterKey(), jPushConfig.getAppKey());
    }
}
