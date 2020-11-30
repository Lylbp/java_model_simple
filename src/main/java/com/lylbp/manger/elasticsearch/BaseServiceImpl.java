package com.lylbp.manger.elasticsearch;

import cn.hutool.core.collection.ListUtil;
import com.lylbp.core.entity.DataPage;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 基础服务类实现
 *
 * @Author weiwenbin
 * @Date: 2020/11/13 上午9:45
 */
public class BaseServiceImpl<R extends BaseRepository<T, ID>, T, ID> implements BaseService<T, ID> {
    @Resource
    protected R baseRepository;

    @Resource(name = "elasticsearchTemplate")
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private ElasticsearchOperations operations;

    public ElasticsearchRestTemplate getElasticsearchTemplate() {
        return elasticsearchTemplate;
    }


    public R getBaseRepository() {
        return baseRepository;
    }

    //////////////增//////////
    @Override
    public T save(T t) {
        return baseRepository.save(t);
    }

    @Override
    public void saveAll(Iterable<T> entities) {
        List<List<T>> lists = groupList(ListUtil.toList(entities), 10000);
        for (List<T> list : lists) {
            baseRepository.saveAll(list);
        }
    }

    //////////////删////////////

    @Override
    public void delete(T t) {
        baseRepository.delete(t);
    }

    @Override
    public void deleteAll() {
        baseRepository.deleteAll();
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        baseRepository.deleteAll(iterable);
    }

    @Override
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }

    //////////////查////////////

    @Override
    public List<T> findAll() {
        return ListUtil.toList(baseRepository.findAll());
    }

    @Override
    public List<T> findAll(Iterable<ID> ids) {
        return (List<T>) baseRepository.findAllById(ids);
    }

    @Override
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return (List<T>) baseRepository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public boolean existsById(ID id) {
        return baseRepository.existsById(id);
    }

    @Override
    public long count() {
        return baseRepository.count();
    }


    @Override
    public Object selectSearchHitsByScrollAndFrom(
            NativeSearchQueryBuilder nativeSearchQueryBuilder, DataPage<T> dataPage, Class<T> clazz) {
        //获取index
        String indexName = clazz.getAnnotation(Document.class).indexName();
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);

        if (null == dataPage) {
            //构建查询query
            NativeSearchQuery query = nativeSearchQueryBuilder.build();
            //游标集合
            List<String> scrollIds = new ArrayList<>();
            //获取第一次游标
            SearchScrollHits<T> searchScrollHits = elasticsearchTemplate.searchScrollStart(
                    5000, query, clazz, indexCoordinates);
            //当前游标
            String scrollId = searchScrollHits.getScrollId();
            //数据
            List<SearchHit<T>> dataList = new ArrayList<>();
            while (searchScrollHits.hasSearchHits()) {
                List<SearchHit<T>> searchHits = searchScrollHits.getSearchHits();
                dataList.addAll(searchHits);
                searchScrollHits = elasticsearchTemplate.searchScrollContinue(scrollId, 5000, clazz, indexCoordinates);
                scrollId = searchScrollHits.getScrollId();
                scrollIds.add(scrollId);
            }
            //清除游标
            elasticsearchTemplate.searchScrollClear(scrollIds);

            return dataList;
        } else {
            long current = dataPage.getCurrent();
            long size = dataPage.getSize();
            if (current > 0) {
                current = current - 1;
            }
            //添加分页
            nativeSearchQueryBuilder.withPageable(PageRequest.of((int) current, (int) size));
            //构建查询query
            NativeSearchQuery query = nativeSearchQueryBuilder.build();
            //查询
            SearchHits<T> search = elasticsearchTemplate.search(query, clazz, indexCoordinates);
            dataPage.setTotal(operations.count(query, clazz));

            return search;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> selectSearchHitsByScrollAndFrom(List<QueryBuilder> queryBuilders, List<SortBuilder<?>> sortBuilders, DataPage<T> dataPage, Class<T> clazz) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = getNativeSearchQueryBuilder(queryBuilders, sortBuilders);
        Object result = selectSearchHitsByScrollAndFrom(nativeSearchQueryBuilder, dataPage, clazz);
        List<T> list = (List<T>) SearchHitSupport.unwrapSearchHits(result);

        //有分页设置数据
        if (null != dataPage) {
            dataPage.setRecords(list);
        }

        return list;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Object selectSearchHitsByScroll(
            NativeSearchQueryBuilder nativeSearchQueryBuilder, DataPage<T> dataPage, Class<T> clazz) {
        long size = 0;
        long current = 0;
        if (null != dataPage) {
            size = dataPage.getSize();
            current = dataPage.getCurrent();
        }
        //添加分页
        if (null != dataPage) {
            nativeSearchQueryBuilder.withPageable(PageRequest.of(0, (int) size));
        }
        //构建查询query
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        //获取index
        String indexName = clazz.getAnnotation(Document.class).indexName();
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);

        SearchScrollHits<T> searchScrollHits = elasticsearchTemplate.searchScrollStart(
                5000, query, clazz, indexCoordinates);
        //总数
        long totalHits = searchScrollHits.getTotalHits();
        //当前游标
        String scrollId = searchScrollHits.getScrollId();
        //当前页数超过最大页数直接返回
        if (null != dataPage) {
            long totalPage = 0;
            if (totalHits % size == 0) {
                totalPage = totalHits / size;
            } else {
                totalPage = totalHits / size + 1;
            }
            if (current > totalPage) {
                return new ArrayList<>();
            }
        }
        //游标集合
        List<String> scrollIds = new ArrayList<>();
        //数据
        List<SearchHit<T>> searchHits = null;
        int i = 0;
        while (searchScrollHits.hasSearchHits()) {
            searchHits = searchScrollHits.getSearchHits();
            searchScrollHits = elasticsearchTemplate.searchScrollContinue(scrollId, 5000, clazz, indexCoordinates);
            scrollId = searchScrollHits.getScrollId();
            scrollIds.add(scrollId);
            i++;
            if (dataPage != null && i == dataPage.getCurrent()) {
                break;
            }
        }
        //清除游标
        elasticsearchTemplate.searchScrollClear(scrollIds);
        //有分页设置总数
        if (null != dataPage) {
            dataPage.setTotal(totalHits);
        }

        return searchHits;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> selectSearchHitsByScroll(List<QueryBuilder> queryBuilders, List<SortBuilder<?>> sortBuilders, DataPage<T> dataPage, Class<T> clazz) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = getNativeSearchQueryBuilder(queryBuilders, sortBuilders);
        Object result = selectSearchHitsByScroll(nativeSearchQueryBuilder, dataPage, clazz);
        List<T> list = (List<T>) SearchHitSupport.unwrapSearchHits(result);

        //有分页设置数据
        if (null != dataPage) {
            dataPage.setRecords(list);
        }

        return list;
    }

    @Override
    public NativeSearchQueryBuilder getNativeSearchQueryBuilder(List<QueryBuilder> queryBuilders,
                                                                List<SortBuilder<?>> sortBuilders) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加withQuery
        if (null != queryBuilders && queryBuilders.size() > 0){
            for (QueryBuilder queryBuilder : queryBuilders) {
                nativeSearchQueryBuilder.withQuery(queryBuilder);
            }
        }
        //添加SortBuilder
        if (null != sortBuilders && sortBuilders.size() > 0){
            for (SortBuilder sort : sortBuilders) {
                nativeSearchQueryBuilder.withSort(sort);
            }
        }

        return nativeSearchQueryBuilder;
    }

    public static <T> List<List<T>> groupList(List<T> list, int capacity) {
        List<List<T>> listGroup = new ArrayList<>();
        int listSize = list.size();
        int start = 0;
        if (capacity == 0 || listSize == 0) {
            listGroup.add(list);
            return listGroup;
        }

        while (start < listSize) {
            int toIndex = start + capacity;
            if (toIndex > listSize) {
                toIndex = listSize;
            }

            List<T> group = list.subList(start, toIndex);
            listGroup.add(group);
            start += capacity;
        }

        return listGroup;
    }

}
