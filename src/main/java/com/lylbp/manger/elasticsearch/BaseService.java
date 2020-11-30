package com.lylbp.manger.elasticsearch;

import com.lylbp.core.entity.DataPage;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.Optional;

/**
 * 基础服务类接口
 *
 * @Author weiwenbin
 * @Date: 2020/11/13 上午9:45
 */
public interface BaseService<T, ID> {
    /**
     * 保存
     *
     * @param t 实体
     * @return T
     */
    T save(T t);

    /**
     * 批量保存
     *
     * @param entities 实体集合
     */
    void saveAll(Iterable<T> entities);

    //////////////删////////////

    /**
     * 根据实体删除
     *
     * @param t 实体
     */
    void delete(T t);

    /**
     * 删除所有
     */
    void deleteAll();

    /**
     * 批量删除
     *
     * @param iterable 实体集合
     */
    void deleteAll(Iterable<? extends T> iterable);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(ID id);

    //////////////查////////////

    /**
     * 查找所有
     *
     * @return List<T>
     */
    List<T> findAll();

    /**
     * 根据id集合查找所有
     *
     * @return List<T>
     */
    List<T> findAll(Iterable<ID> ids);

    /**
     * 根据id查找
     *
     * @param id id
     * @return Optional<T>
     */
    Optional<T> findById(ID id);

    /**
     * 查找所有并排序
     *
     * @param sort 排序
     * @return List<T>
     */
    List<T> findAll(Sort sort);

    /**
     * 查找所有并分页
     *
     * @return Page<T>
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 根据id判断是否存在
     *
     * @param id id
     * @return boolean
     */
    boolean existsById(ID id);

    /**
     * 统计
     *
     * @return long
     */
    long count();

    /**
     * 自定义：深分页与浅分页联合使用
     * 缺点:es必须配置max_result_window,且数据量必须小于max_result_window
     * 优点:分页查询快
     *
     * @param nativeSearchQueryBuilder nativeSearchQueryBuilder
     * @param dataPage                   分页
     * @param clazz                    clazz
     * @return Object
     */
    Object selectSearchHitsByScrollAndFrom(NativeSearchQueryBuilder nativeSearchQueryBuilder, DataPage<T> dataPage, Class<T> clazz);

    /**
     * 自定义：深分页与浅分页联合使用
     * 缺点:es必须配置max_result_window,且数据量必须小于max_result_window
     * 优点:分页查询快
     *
     * @param queryBuilders 查询条件
     * @param sortBuilders  sortBuilders
     * @param dataPage        分页
     * @param clazz         clazz
     * @return List<SearchHit < T>>
     */
    List<T> selectSearchHitsByScrollAndFrom(List<QueryBuilder> queryBuilders, List<SortBuilder<?>> sortBuilders, DataPage<T> dataPage, Class<T> clazz);

    /**
     * 自定义 深分页查询
     * 缺点:分页慢
     * 优点:无数据量限制
     *
     * @param nativeSearchQueryBuilder nativeSearchQueryBuilder
     * @param dataPage                   分页
     * @param clazz                    clazz
     * @return List<SearchHit < T>>
     */
    Object selectSearchHitsByScroll(NativeSearchQueryBuilder nativeSearchQueryBuilder, DataPage<T> dataPage, Class<T> clazz);

    /**
     * 自定义 深分页查询
     * 缺点:分页慢
     * 优点:无数据量限制
     *
     * @param queryBuilders 查询条件
     * @param sortBuilders  排序条件
     * @param clazz         clazz
     * @return List<SearchHit < T>>
     */
    List<T> selectSearchHitsByScroll(List<QueryBuilder> queryBuilders, List<SortBuilder<?>> sortBuilders, DataPage<T> dataPage, Class<T> clazz);

    /**
     * 获取NativeSearchQueryBuilder
     *
     * @param queryBuilders 查询条件
     * @param sortBuilders  排序条件
     * @return NativeSearchQueryBuilder
     */
    NativeSearchQueryBuilder getNativeSearchQueryBuilder(List<QueryBuilder> queryBuilders, List<SortBuilder<?>> sortBuilders);
}
