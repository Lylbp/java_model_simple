package com.lylbp.project.vo;

import com.lylbp.project.entity.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 后台管理员表VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminVO对象")
public class AdminVO extends Admin {
}