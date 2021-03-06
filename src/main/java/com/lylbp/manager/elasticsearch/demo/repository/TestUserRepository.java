package com.lylbp.manager.elasticsearch.demo.repository;

import com.lylbp.manager.elasticsearch.BaseRepository;
import com.lylbp.manager.elasticsearch.demo.entity.ESTestUser;
import org.springframework.data.elasticsearch.annotations.Query;

/**
 * @author weiwenbin
 * @date 2020/11/13 上午9:31
 */
public interface TestUserRepository extends BaseRepository<ESTestUser, String> {
    /**
     * 简单的带条件查询可以直接拼接提示出来方法名查询，此处不能随意命名
     *
     * @param phone 手机号
     * @return TestUser
     */
    ESTestUser findByPhone(String phone);

    /**
     * 手写查询语句
     *
     * @param name 名称
     * @return TestUser
     */
    @Query("{\"bool\":{\"must\":{\"term\":{\"name\":\"?0\"}}}}")
    ESTestUser selectByName(String name);
}
