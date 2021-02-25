package com.lylbp.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lylbp.project.entity.Menu;
import com.lylbp.project.vo.MenuNodeVO;
import com.lylbp.project.vo.MenuVO;
import com.lylbp.project.mapper.MenuMapper;
import com.lylbp.project.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * <p>
 * 权限管理-菜单表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public Boolean edit(Menu entity) {
        if (ObjectUtil.isEmpty(entity.getMenuPid())){
            entity.setMenuPid("0");
        }

        return saveOrUpdate(entity);
    }

    @Override
    public List<MenuVO> getMenuVOListByParams(Page<MenuVO> page, Map<String, Object> params) {
        return getBaseMapper().queryMenuVOByParams(page, params);
    }

    @Override
    public MenuVO getOneMenuVOByParams(Map<String, Object> params) {
        MenuVO entityVO = null;
        List<MenuVO> list = getMenuVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public MenuVO getOneMenuVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneMenuVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<Menu, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<Menu> wrapper = new QueryWrapper<Menu>().lambda().eq(columnName, columnValue);

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(Menu::getMenuId, notId);
        }
        List<Menu> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public List<MenuNodeVO> recursionHandleMenuVOList(List<MenuVO> menuVOS, String menuPid) {
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
        for (MenuVO menuVO : menuVOS) {
            String currentPid = menuVO.getMenuPid();
            String currentId = menuVO.getMenuId();
            if (menuPid.equals(currentPid)) {
                MenuNodeVO menuNodeVO = new MenuNodeVO();
                BeanUtil.copyProperties(menuVO, menuNodeVO);

                List<MenuNodeVO> sonMenuVOS = recursionHandleMenuVOList(menuVOS, currentId);
                menuNodeVO.setSonMenuVOS(sonMenuVOS);

                menuNodeVOS.add(menuNodeVO);
            }
        }

        return menuNodeVOS;
    }
}
