package com.lylbp.project.service;

import com.lylbp.project.entity.Older;
import com.lylbp.project.vo.OlderVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
public interface OlderService extends IService<Older> {
        /**
         * 新增或编辑
         *
         * @param entity 实体
         * @return Boolean
         */
        Boolean edit(Older entity);

        /**
         * 通过查询参数获取列表
         * @param page   分页对象,传null代表不分页
         * @param params 参数
         * @return List<OlderVO>
         */
        List<OlderVO> getOlderVOListByParams(Page<OlderVO> page, Map<String, Object> params);

        /**
         * 通过查询参数获取单个OlderVO对象
         *
         * @param params 参数
         * @return OlderVO
         */
        OlderVO getOneOlderVOByParams(Map<String, Object> params);

        /**
         * 通过字段名称与值获取单个OlderVO对象
         *
         * @param columnName 名称
         * @param columnValue 值
         * @return OlderVO
         */
        OlderVO getOneOlderVOBy(String columnName, Object columnValue);

        /**
         * 通过主键判断是否存在
         *
         * @param id 主键
         * @return Boolean
         */
        Boolean isExist(String id);

        /**
         * 判断字段是否重复 true:重复 false:不重复
         *
         * @param notId       id
         * @param columnName  字段名称
         * @param columnValue 字段值
         * @return Boolean
         */
        Boolean columnHasRepeat(String notId, SFunction<Older, ?> columnName, Object columnValue);
}
