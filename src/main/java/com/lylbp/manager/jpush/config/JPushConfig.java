package com.lylbp.manager.jpush.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author weiwenbin
 * @date 2020/5/28 上午9:32
 */
@Data
@Configuration
public class JPushConfig {
    @Resource
    private JPushProperties jPushProperties;
}
