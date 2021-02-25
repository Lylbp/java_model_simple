package com.lylbp.project.vo;

import com.lylbp.project.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 权限管理-菜单表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MenuVO对象")
public class MenuVO extends Menu {
}