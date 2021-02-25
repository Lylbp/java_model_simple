package com.lylbp.project.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lylbp.common.interfaces.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;


/**
 * 定时任务状态枚举
 *
 * @author weiwenbin
 * @date 2020/6/4 上午8:49
 */
@Getter
public enum TaskStatusEnum {
    /**
     * 暂停
     */
    SUSPEND("0", "暂停"),
    /**
     * 启用
     */
    ENABLE("1", "启用")
    ;

    private final String code;

    private final String name;


    TaskStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getByCode(String pCode) {
        TaskStatusEnum enumByCode = getEnumByCode(pCode);
        return null != enumByCode ? enumByCode.getName() : "";
    }

    public static TaskStatusEnum getEnumByCode(String pCode) {
        return Arrays.stream(TaskStatusEnum.values()).filter(item -> {
            return item.getCode().equals(pCode);
        }).findFirst().orElse(null);
    }
}
