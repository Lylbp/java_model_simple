package com.lylbp.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 * 菜单与角色关系DTO对象
 * </p>
 *
 * @Author weiwenbin
 * @Date 2020/7/1 下午9:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MenuRoleBatchEditDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")

public class MenuRoleBatchEditDTO {
    @ApiModelProperty(value = "角色id", required = true)
    @NotBlank
    @Length(max = 32)
    private String roleId;

    @ApiModelProperty(value = "菜单id集合", required = true)
    @NotNull
    @NotEmpty
    @Size(min = 1)
    List<String> menuIdList;
}
