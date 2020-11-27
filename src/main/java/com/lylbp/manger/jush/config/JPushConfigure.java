package com.lylbp.manger.jush.config;

import com.lylbp.manger.jush.service.JPUshService;
import com.lylbp.manger.jush.service.imp.JPUshServiceImp;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
@Component
@ConditionalOnClass(JPUshService.class)
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
