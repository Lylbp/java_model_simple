package com.lylbp.manger.activity.demo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询
 *
 * @author weiwenbin
 * @date 2020/12/14 下午3:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ModelQuery对象", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class ModelQueryQO {
    @ApiModelProperty(value = "key模糊查询传空串无效")
    private String key;

    @ApiModelProperty(value = "名称模糊查询 传空串无效")
    private String nameLike;
}
