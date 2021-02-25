package com.lylbp.project.service;

import com.lylbp.project.entity.Menu;
import com.lylbp.project.vo.MenuNodeVO;
import com.lylbp.project.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * <p>
 * 权限管理-菜单表  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface MenuService extends IService<Menu> {
    /**
     * 新增或编辑
     *
     * @param entity
     * @return Boolean
     */
    Boolean edit(Menu entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<MenuVO>
     */
    List<MenuVO> getMenuVOListByParams(Page<MenuVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个MenuVO对象
     *
     * @param params 参数
     * @return MenuVO
     */
    MenuVO getOneMenuVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个MenuVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return MenuVO
     */
    MenuVO getOneMenuVOBy(String columnName, Object columnValue);

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
    Boolean columnHasRepeat(String notId, SFunction<Menu, ?> columnName, Object columnValue);


    /**
     * 递归处理菜单
     *
     * @param menuVOS
     * @return
     */
    List<MenuNodeVO> recursionHandleMenuVOList(List<MenuVO> menuVOS, String menuPid);
}
