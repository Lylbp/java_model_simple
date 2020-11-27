package com.lylbp.manger.redis.service.impl;

import com.lylbp.manger.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redisService实现类
 *
 * @Author weiwenbin
 * @Date 2020/8/31 上午10:25
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
    @Override
    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    ////////////////////////////////////针对key的操作////////////////////////////////////////////////////////////////////

    /**
     * 判断key是否存在
     *
     * @param key key值
     * @return Boolean
     */
    @Override
    public Boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 指定key的过期时间
     *
     * @param key     key值
     * @param seconds 秒值
     * @return Boolean
     */
    @Override
    public Boolean setKeyExpire(String key, Long seconds) {
        try {
            if (hasKey(key)) {
                return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * key模糊查询
     *
     * @param key key值
     * @return Set<String>
     */
    @Override
    public Set<String> keysLikeSearch(String key) {
        try {
            return stringRedisTemplate.keys("*" + key + "*");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除key
     *
     * @param key key值
     * @return Boolean
     */
    @Override
    public Boolean delete(String key) {
        try {
            return stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.error("redis删除key异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 根据key模糊查询并删除
     *
     * @param pattern 模糊查询
     * @return Long
     */
    @Override
    public Long delKeys(String pattern) {
        Set<String> keys = stringRedisTemplate.keys("*" + pattern + "*");
        return stringRedisTemplate.delete(keys);
    }

    /**
     * 清空所有的key
     *
     * @return Long
     */
    @Override
    public Long delAllKey() {
        Set<String> keys = stringRedisTemplate.keys("*");
        try {
            assert keys != null;
            if (keys.size() > 0) {
                return stringRedisTemplate.delete(keys);
            }
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
    ////////////////////////////////////对string操作////////////////////////////////////////////////////////////////////

    /**
     * 设置String值与过期秒数
     *
     * @param key        key值
     * @param value      存储值
     * @param expSeconds 过期秒数
     * @return boolean
     */
    @Override
    public boolean strSet(String key, String value, Long expSeconds) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取string值
     *
     * @param key key值
     * @return String
     */
    @Override
    public String strGet(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    ////////////////////////////////////对Set操作////////////////////////////////////////////////////////////////////

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    @Override
    public Boolean sHasValue(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将元素加入到set集合key中
     *
     * @param key    key值
     * @param values 存储的值
     * @return boolean
     */
    @Override
    public Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 将元素加入到set集合key中
     *
     * @param key        key值
     * @param expSeconds 过期秒数
     * @param values     存储的值
     * @return boolean
     */
    @Override
    public Long sSet(String key, Long expSeconds, Object... values) {
        try {
            Long sadd = sSet(key, values);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return sadd;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 返回Set集合中的所有成员
     *
     * @param key key值
     * @return Set<Object>
     */
    @Override
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key key值
     * @return
     */
    @Override
    public Long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * key的set中移除值为val的元素
     *
     * @param key    key值
     * @param values 要删除的值
     * @return Boolean
     */
    @Override
    public Long sRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    ////////////////////////////////////对list操作////////////////////////////////////////////////////////////////////

    /**
     * 在key对应的list中右插入一条数据
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    @Override
    public Long lRightSet(String key, Object value, Long expSeconds) {
        try {
            Long count = redisTemplate.opsForList().rightPush(key, value);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 在key对应的list中左插入一条数据
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    @Override
    public Long lLeftSet(String key, Object value, Long expSeconds) {
        try {
            Long count = redisTemplate.opsForList().leftPush(key, value);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    /**
     * 在key对应的list中右插入一个list
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    @Override
    public Long lRightSetAll(String key, List<Object> value, Long expSeconds) {
        try {
            Long count = redisTemplate.opsForList().rightPushAll(key, value);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 在key对应的list中左插一个list
     *
     * @param key        键
     * @param value      值
     * @param expSeconds 过期秒数
     * @return Long
     */
    @Override
    public Long lLeftSetAll(String key, List value, Long expSeconds) {
        try {
            Long count = redisTemplate.opsForList().leftPushAll(key, value);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取对应key的list
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return List<Object>
     */
    @Override
    public List<Object> lGetRange(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取key对应list下标为index的元素
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return Object
     */
    @Override
    public Object lGetIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取key对应list的长度
     *
     * @param key key值
     * @return Long
     */
    @Override
    public Long lGetSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    /**
     * 修改key对应list中的对应索引数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return Boolean
     */
    @Override
    public Boolean lUpdateIndex(String key, Long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    @Override
    public Long lRemove(String key, Long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            if (null == remove) {
                return 0L;
            }
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    ////////////////////////////////////对hash表操作////////////////////////////////////////////////////////////////////

    /**
     * hash表中存入map
     *
     * @param key 键
     * @param map 对应多个键值
     * @return Boolean
     */
    @Override
    public Boolean hmset(String key, Map<String, Object> map, Long expSeconds) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key        键
     * @param item       项
     * @param value      值
     * @param expSeconds 过期秒数 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return Boolean
     */
    @Override
    public Boolean hset(String key, String item, Object value, Long expSeconds) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (expSeconds > 0) {
                setKeyExpire(key, expSeconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hash中对应key
     *
     * @param key     键
     * @param hashKey hash表中的key
     * @return Object
     */
    @Override
    public Object hget(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return Map<Object, Object>
     */
    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键
     * @param item 项
     */
    @Override
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键
     * @param item 项
     * @return Boolean
     */
    @Override
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    ////////////////////////////对整数和浮点数操作//////////////////////////////////////////////////////////////////


    ////////////////////////////对地理坐标操作//////////////////////////////////////////////////////////////////
}
