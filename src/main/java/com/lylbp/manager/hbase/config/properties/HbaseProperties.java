package com.lylbp.manager.hbase.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * habse的配置项
 *
 * @author weiwenbin
 * @date 2020/9/1 上午11:28
 */
@ConfigurationProperties(prefix = "hbase")
@Data
public class HbaseProperties {
    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * zookeeper配置
     */
    private HbaseProperties.Zookeeper zookeeper = new HbaseProperties.Zookeeper();

    @Data
    public static class Zookeeper {
        /**
         * zookeeper地址
         */
        private String quorum;

        /**
         * zookeeper的Property配置
         */
        private HbaseProperties.Property property = new HbaseProperties.Property();
    }

    @Data
    public static class Property {
        /**
         * zookeeper端口号
         */
        private String clientPort;
    }
}


