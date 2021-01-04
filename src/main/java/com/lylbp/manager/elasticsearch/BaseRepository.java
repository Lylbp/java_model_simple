package com.lylbp.manager.elasticsearch;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 基础参考
 *
 * @author weiwenbin
 * @date 2020/11/12 下午3:26
 */
public interface BaseRepository<T, ID> extends ElasticsearchRepository<T, ID> {
}