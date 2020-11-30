package com.lylbp.manger.elasticsearch.demo.service;

import com.lylbp.manger.elasticsearch.BaseService;
import com.lylbp.core.entity.DataPage;
import com.lylbp.manger.elasticsearch.demo.entity.ESTestUser;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwenbin
 * @Date: 2020/11/13 上午9:39
 */
public interface TestUserService extends BaseService<ESTestUser, String> {
    /**
     * 查询
     *
     * @param params 查询参数
     * @param dataPage 分页参数
     * @return List<TestUser>
     */
    List<ESTestUser> selectSearchHitsByScroll(Map<String, Object> params, DataPage<ESTestUser> dataPage);

    /**
     * 查询
     *
     * @param params 查询参数
     * @param dataPage 分页参数
     * @return List<TestUser>
     */
    List<ESTestUser> selectSearchHitsByScrollAndFrom(Map<String, Object> params, DataPage<ESTestUser> dataPage);
    /**
     * 通过手机查询
     *
     * @param phone 手机号
     * @return TestUser
     */
    ESTestUser findByPhone(String phone);

    /**
     * 通过名称查询
     *
     * @param name 名称
     * @return TestUser
     */
    ESTestUser selectByName(String name);
}
