package com.lylbp.manger.hbase.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Zookeeper的配置项
 * @Author weiwenbin
 * @Date 2020/9/1 上午11:28
 */
@ConfigurationProperties(prefix = "zookeeper")
@Component
@Data
public class ZookeeperProperties {
    /**
     * Znode配置
     */
    private ZookeeperProperties.Znode znode = new ZookeeperProperties.Znode();

    public static class Znode {
        /**
         * 节点的parent
         */
        private String parent;

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }
    }

}


