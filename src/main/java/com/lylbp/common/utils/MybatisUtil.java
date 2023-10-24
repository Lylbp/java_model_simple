package com.lylbp.common.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lylbp.common.entity.PageQueryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2023/10/23 下午5:20
 */
public class MybatisUtil {

    /**
     * 转化成mybatis plus中的Page
     *
     * @param pageQueryDTO 查询条件
     * @return IPage
     */
    public static <T> Page<T> getMybatisPlusPage(PageQueryDTO pageQueryDTO, Class<T> clazz) {
        long current = Optional.ofNullable(pageQueryDTO.getCurrent()).orElse(0L);
        long size = Optional.ofNullable(pageQueryDTO.getSize()).orElse(0L);
        List<String> ascColumnList = Optional.ofNullable(pageQueryDTO.getAscColumnList()).orElse(new ArrayList<>());
        List<String> descColumnList = Optional.ofNullable(pageQueryDTO.getDescColumnList()).orElse(new ArrayList<>());

        Page<T> page = new Page<>(current, size);
        for (String asc : ascColumnList) {
            page.addOrder(OrderItem.asc(asc));
        }

        for (String desc : descColumnList) {
            page.addOrder(OrderItem.desc(desc));
        }

        return page;
    }
}
