package com.lylbp.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 * 后台管理员表DTO对象
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class AdminDTO {
    @ApiModelProperty("管理员id 编辑必传")
    @Length(max = 32)
    private String adminId;

    @ApiModelProperty("登录账号")
    @Length(max = 32)
    @NotBlank
    private String loginAccount;

    @ApiModelProperty("真实名称")
    @Length(max = 128)
    @NotBlank
    private String realName;

    @Size(min = 1)
    private List<String> adminIdList;
}