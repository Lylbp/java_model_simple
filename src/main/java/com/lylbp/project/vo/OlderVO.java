package com.lylbp.project.vo;

import com.lylbp.project.entity.Older;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OlderVO对象")
public class OlderVO extends Older {
    private String id;

    private String name;
}