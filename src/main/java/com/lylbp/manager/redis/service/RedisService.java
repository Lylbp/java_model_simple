package com.lylbp.manager.redis.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author weiwenbin
 * @date 2020/8/31 上午10:25
 */
public interface RedisService {
    RedisTemplate<String, Object> getRedisTemplate();

    StringRedisTemplate getStringRedisTemplate();
    ////////////////////////////////////针对key的操作////////////////////////////////////////////////////////////////////

    /**
     * 判断key是否存在
     *
     * @param key key值
     * @return Boolean
     */
    Boolean hasKey(String key);

    /**
     * 指定key的过期时间
     *
     * @param key     key值
     * @param seconds 秒值
     * @return Boolean
     */
    Boolean setKeyExpire(String key, Long seconds);

    /**
     * key模糊查询
     *
     * @param key key值
     * @return Set<String>
     */
    Set<String> keysLikeSearch(String key);

    /**
     * 删除key
     *
     * @param key key值
     * @return Boolean
     */
    Boolean delete(String key);

    /**
     * 根据key模糊查询并删除
     *
     * @param pattern 模糊查询
     * @return Long
     */
    Long delKeys(String pattern);

    /**
     * 清空所有的key
     *
     * @return Long
     */
    Long delAllKey();
    ////////////////////////////////////对string操作////////////////////////////////////////////////////////////////////

    /**
     * 设置String值与过期秒数
     *
     * @param key        key值
     * @param value      存储值
     * @param expSeconds 过期秒数
     * @return boolean
     */
    boolean strSet(String key, String value, Long expSeconds);

    /**
     * 获取string值
     *
     * @param key key值
     * @return String
     */
    String strGet(String key);
    ////////////////////////////////////对Set操作////////////////////////////////////////////////////////////////////

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    Boolean sHasValue(String key, Object value);

    /**
     * 将元素加入到set集合key中
     *
     * @param key    key值
     * @param values 存储的值
     * @return boolean
     */
    Long sSet(String key, Object... values);

    /**
     * 将元素加入到set集合key中
     *
     * @param key        key值
     * @param expSeconds 过期秒数
     * @param values     存储的值
     * @return Long
     */
    Long sSet(String key, Long expSeconds, Object... values);

    /**
     * 返回Set集合中的所有成员
     *
     * @param key key值
     * @return Set<Object>
     */
    Set<Object> sGet(String key);

    /**
     * 获取set缓存的长度
     *
     * @param key key值
     * @return
     */
    Long sSize(String key);

    /**
     * key的set中移除值为val的元素
     *
     * @param key    key值
     * @param values 要删除的值
     * @return Boolean
     */
    Long sRemove(String key, Object... values);


    ////////////////////////////////////对list操作////////////////////////////////////////////////////////////////////

    /**
     * 在key对应的list中右插入一条数据
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    Long lRightSet(String key, Object value, Long expSeconds);

    /**
     * 在key对应的list中左插入一条数据
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    Long lLeftSet(String key, Object value, Long expSeconds);


    /**
     * 在key对应的list中右插入一个list
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    Long lRightSetAll(String key, List<Object> value, Long expSeconds);

    /**
     * 在key对应的list中左插一个list
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    Long lLeftSetAll(String key, List<Object> value, Long expSeconds);

    /**
     * 获取对应key的list
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return List<Object>
     */
    List<Object> lGetRange(String key, Long start, Long end);

    /**
     * 获取key对应list下标为index的元素
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return Object
     */
    Object lGetIndex(String key, Long index);

    /**
     * 获取key对应list的长度
     *
     * @param key key值
     * @return Long
     */
    Long lGetSize(String key);


    /**
     * 修改key对应list中的对应索引数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return Boolean
     */
    Boolean lUpdateIndex(String key, Long index, Object value);

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    Long lRemove(String key, Long count, Object value);


    ////////////////////////////////////对hash表操作////////////////////////////////////////////////////////////////////

    /**
     * hash表中存入map
     *
     * @param key 键
     * @param map 对应多个键值
     * @return Boolean
     */
    Boolean hmset(String key, Map<String, Object> map, Long expSeconds);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key        键
     * @param item       项
     * @param value      值
     * @param expSeconds 过期秒数 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return Boolean
     */
    Boolean hset(String key, String item, Object value, Long expSeconds);

    /**
     * 获取hash中对应key
     *
     * @param key     键
     * @param hashKey hash表中的key
     * @return Object
     */
    Object hget(String key, Object hashKey);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return Map<Object, Object>
     */
    Map<Object, Object> hmget(String key);

    /**
     * 删除hash表中的值
     *
     * @param key  键
     * @param item 项
     */
    void hdel(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键
     * @param item 项
     * @return Boolean
     */
    Boolean hHasKey(String key, String item);

    ////////////////////////////对整数和浮点数操作//////////////////////////////////////////////////////////////////


    ////////////////////////////对地理坐标操作//////////////////////////////////////////////////////////////////
}
