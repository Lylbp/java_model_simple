package com.lylbp.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 管理员与角色关系批量编辑DTO对象
 * </p>
 *
 * @author weiwenbin
 * @date 2020/7/1 下午9:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminRoleBatchEditDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")

public class AdminRoleBatchEditDTO {
    @ApiModelProperty(value = "用户id", required = true)
    @NotBlank
    private String adminId;

    @ApiModelProperty(value = "角色集合", required = true)
    @NotNull
    @NotEmpty
    List<String> roleIdList;
}
