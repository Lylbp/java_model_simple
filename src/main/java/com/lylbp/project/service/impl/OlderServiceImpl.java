package com.lylbp.project.service.impl;

import com.lylbp.project.entity.Older;
import com.lylbp.project.vo.OlderVO;
import com.lylbp.project.mapper.OlderMapper;
import com.lylbp.project.service.OlderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import java.util.HashMap;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lylbp.common.enums.TrueOrFalseEnum;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
@Service
public class OlderServiceImpl extends ServiceImpl<OlderMapper, Older> implements OlderService {
        @Override
        public Boolean edit(Older entity) {
            //TODO 新增或编辑之前需要进行的逻辑
            return saveOrUpdate(entity);
        }

        @Override
        public List<OlderVO> getOlderVOListByParams(Page<OlderVO> page, Map<String, Object> params) {
            return getBaseMapper().queryOlderVOByParams(page, params);
        }

        @Override
        public OlderVO getOneOlderVOByParams(Map<String, Object> params) {
            OlderVO entityVO = null;
            List<OlderVO> list = getOlderVOListByParams(null,params);
            if (ObjectUtil.isNotEmpty(list)){
                entityVO = list.get(0);
            }

            return entityVO;
        }

        @Override
        public OlderVO getOneOlderVOBy(String columnName, Object columnValue){
            HashMap<String, Object> params = new HashMap<>(1);
            params.put(columnName, columnValue);

            return getOneOlderVOByParams(params);
        }

        @Override
        public Boolean isExist(String id) {
            return ObjectUtil.isNotEmpty(this.getById(id));
        }

        @Override
        public Boolean columnHasRepeat(String notId, SFunction<Older, ?> columnName, Object columnValue) {
            LambdaQueryWrapper<Older> wrapper = new QueryWrapper<Older>().lambda().eq(columnName, columnValue);
                        wrapper.eq(Older::getIsValid,TrueOrFalseEnum.TRUE.getCode());

            if (StrUtil.isNotEmpty(notId)) {
                            wrapper.ne(Older::getId, notId);
            }
            List<Older> entities = getBaseMapper().selectList(wrapper);

            return ObjectUtil.isNotEmpty(entities);
        }
}
