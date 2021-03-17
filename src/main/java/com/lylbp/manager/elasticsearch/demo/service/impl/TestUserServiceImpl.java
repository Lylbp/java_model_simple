package com.lylbp.manager.elasticsearch.demo.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.lylbp.manager.elasticsearch.BaseServiceImpl;
import com.lylbp.common.entity.DataPage;
import com.lylbp.manager.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manager.elasticsearch.demo.repository.TestUserRepository;
import com.lylbp.manager.elasticsearch.demo.service.TestUserService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author weiwenbin
 * @date 2020/11/12 下午5:23
 */
@Service
public class TestUserServiceImpl extends BaseServiceImpl<TestUserRepository, ESTestUser, String> implements TestUserService {
    @Override
    public List<ESTestUser> selectSearchHitsByScroll(Map<String, Object> params, DataPage<ESTestUser> dataPage) {
        ArrayList<QueryBuilder> queryBuilders = new ArrayList<>(10);
        BoolQueryBuilder booQueryBuilder = QueryBuilders.boolQuery();
        if (params.containsKey("nameLike") && ObjectUtil.isNotEmpty(params.get("nameLike"))) {
            String nameLike = (String) params.get("nameLike");
            booQueryBuilder.must(QueryBuilders.wildcardQuery("name", "*" + nameLike + "*"));
        }

        if (params.containsKey("createTimeGt") && ObjectUtil.isNotEmpty(params.get("createTimeGt"))) {
            long createTimeGt = Convert.toDate(params.get("createTimeGt")).getTime();
            booQueryBuilder.must(QueryBuilders.boolQuery().
                    filter(QueryBuilders.rangeQuery("createTime").gt(createTimeGt)));
        }

        queryBuilders.add(booQueryBuilder);

        List<SortBuilder<?>> sortBuilders = new ArrayList<>(10);
        sortBuilders.add(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));

        return selectSearchHitsByScroll(queryBuilders, sortBuilders, dataPage, ESTestUser.class);
    }

    @Override
    public List<ESTestUser> selectSearchHitsByFrom(Map<String, Object> params, DataPage<ESTestUser> dataPage) {
        List<QueryBuilder> queryBuilders = new ArrayList<>(10);
        BoolQueryBuilder booQueryBuilder = QueryBuilders.boolQuery();
        if (params.containsKey("nameLike") && ObjectUtil.isNotEmpty(params.get("nameLike"))) {
            String nameLike = (String) params.get("nameLike");
            booQueryBuilder.must(QueryBuilders.wildcardQuery("name", "*" + nameLike + "*"));
        }

        if (params.containsKey("createTimeGt") && ObjectUtil.isNotEmpty(params.get("createTimeGt"))) {
            long createTimeGt = Convert.toDate(params.get("createTimeGt")).getTime();
            booQueryBuilder.must(QueryBuilders.boolQuery().
                    filter(QueryBuilders.rangeQuery("createTime").gt(createTimeGt)));
        }
        queryBuilders.add(booQueryBuilder);

        List<SortBuilder<?>> sortBuilders = new ArrayList<>(10);
        sortBuilders.add(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));

        return selectSearchHitsByFrom(queryBuilders, sortBuilders, dataPage, ESTestUser.class);
    }

    @Override
    public ESTestUser findByPhone(String phone) {
        return this.getBaseRepository().findByPhone(phone);
    }

    @Override
    public ESTestUser selectByName(String name) {
        return this.getBaseRepository().selectByName(name);
    }
}
