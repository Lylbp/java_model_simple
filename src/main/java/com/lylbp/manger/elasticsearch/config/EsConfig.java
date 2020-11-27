package com.lylbp.manger.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author weiwenbin
 * @Date: 2020/11/13 下午2:39
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.lylbp.manger.elasticsearch.demo.repository")
public class EsConfig extends AbstractElasticsearchConfiguration {
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
        return RestClients.create(ClientConfiguration.localhost()).rest();
    }
}
