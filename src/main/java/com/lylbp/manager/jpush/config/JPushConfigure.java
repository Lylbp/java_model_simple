package com.lylbp.manager.jpush.config;


import com.lylbp.manager.jpush.service.JPUshService;
import com.lylbp.manager.jpush.service.imp.JPUshServiceImp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * JPushConfigure
 *
 * @author alex
 */
@Data
@Component
@Slf4j
@ConditionalOnClass(JPUshService.class)
@ConditionalOnProperty(prefix = "jpush", name = "enabled", havingValue = "true")
public class JPushConfigure {
    @Resource
    private JPushConfig jPushConfig;

    @Bean
    @ConditionalOnMissingBean
    public JPUshService jpUshService() {
        JPUshService jpUshService = new JPUshServiceImp();
        jpUshService.setJPushConfig(jPushConfig);
        return jpUshService;
    }
}