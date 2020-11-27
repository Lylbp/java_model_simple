package com.lylbp.manger.hbase.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * habse的配置项
 *
 * @Author weiwenbin
 * @Date 2020/9/1 上午11:28
 */
@ConfigurationProperties(prefix = "hbase")
@Component
@Data
public class HbaseProperties {
    /**
     * zookeeper配置
     */
    private HbaseProperties.Zookeeper zookeeper = new HbaseProperties.Zookeeper();

    public static class Zookeeper {
        /**
         * zookeeper地址
         */
        private String quorum;

        /**
         * zookeeper的Property配置
         */
        private HbaseProperties.Property property = new HbaseProperties.Property();

        public String getQuorum() {
            return quorum;
        }

        public void setQuorum(String quorum) {
            this.quorum = quorum;
        }

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }
    }

    public static class Property {
        /**
         * zookeeper端口号
         */
        private String clientPort;

        public String getClientPort() {
            return clientPort;
        }

        public void setClientPort(String clientPort) {
            this.clientPort = clientPort;
        }
    }
}


