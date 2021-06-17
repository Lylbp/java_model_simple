package com.lylbp.manager.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;

/**
 * @author weiwenbin
 * @date 2020/11/13 下午2:39
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.lylbp.manager.elasticsearch.demo.repository")
@ConditionalOnProperty(prefix = "spring.data.elasticsearch.repositories", name = "enabled", havingValue = "true")
public class EsConfig extends AbstractElasticsearchConfiguration {
    @Resource
    ElasticsearchRestClientProperties esProperties;

    /**
     * elasticsearchTemplate 替换为 elasticsearchRestTemplate
     *
     * @param client client
     * @return ElasticsearchRestTemplate
     */
    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client) {
        return new ElasticsearchRestTemplate(client);
    }

    /**
     * transportClient替换为RestHighLevelClient
     *
     * @return RestHighLevelClient
     */
    @Override
    public RestHighLevelClient elasticsearchClient() {
        String[] hosts = esProperties.getUris().toArray(new String[0]);
        ClientConfiguration build = ClientConfiguration.builder()
                .connectedTo(hosts)
                .build();
        return RestClients.create(build).rest();
    }
}
