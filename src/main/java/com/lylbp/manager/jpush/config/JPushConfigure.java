package com.lylbp.manager.jpush.config;


import com.lylbp.manager.jpush.service.JPUshService;
import com.lylbp.manager.jpush.service.imp.JPUshServiceImp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@ConditionalOnClass(JPUshService.class)
@Slf4j
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