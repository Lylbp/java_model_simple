package com.lylbp.manager.hbase.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zookeeper的配置项
 *
 * @author weiwenbin
 * @date 2020/9/1 上午11:28
 */
@Data
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    /**
     * Znode配置
     */
    private Znode znode = new Znode();

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


