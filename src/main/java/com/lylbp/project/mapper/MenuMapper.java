package com.lylbp.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lylbp.project.entity.Menu;
import com.lylbp.project.vo.MenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限管理-菜单表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
    * 条件查询List<MenuVO>
    *
    * @param params 查询参数
    * @return List<MenuVO>
    */
    List<MenuVO> queryMenuVOByParams(@Param("page") Page<MenuVO> page, @Param("params") Map<String, Object> params);
}
