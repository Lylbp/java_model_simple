package com.lylbp.manager.hbase.config;

import com.lylbp.manager.hbase.config.properties.HbaseProperties;
import com.lylbp.manager.hbase.config.properties.ZookeeperProperties;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * habse的配置文件
 *
 * @author weiwenbin
 * @date 2020/11/2 上午10:49
 */
@Configuration
@EnableConfigurationProperties({HbaseProperties.class, ZookeeperProperties.class})
@ConditionalOnProperty(prefix = "hbase", name = "enabled", havingValue = "true")
public class HBaseConfig {
    @Resource
    private HbaseProperties hbaseProperties;

    @Resource
    private ZookeeperProperties zookeeperProperties;

    @Bean
    public org.apache.hadoop.conf.Configuration configuration() {
        String quorum = hbaseProperties.getZookeeper().getQuorum();
        String clientPort = hbaseProperties.getZookeeper().getProperty().getClientPort();
        String parent = zookeeperProperties.getZnode().getParent();

        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", quorum);
        conf.set("hbase.zookeeper.port", clientPort);
        conf.set("zookeeper.znode.parent", parent);
        return conf;
    }

    @Bean
    public Admin createAdmin() {
        try {
            Connection connection = createConnection();
            return connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Connection createConnection() {
        try {
            return ConnectionFactory.createConnection(configuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
