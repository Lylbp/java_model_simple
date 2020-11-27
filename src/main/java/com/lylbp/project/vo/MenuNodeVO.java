package com.lylbp.project.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author weiwenbin
 * @Date 2020/7/1 下午7:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminVO对象")
public class MenuNodeVO extends MenuVO{
    @ApiModelProperty(value = "子菜单信息")
    private List<MenuNodeVO> sonMenuVOS;
}
