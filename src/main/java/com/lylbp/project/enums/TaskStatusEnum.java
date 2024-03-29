package com.lylbp.project.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


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

    public static Map<String, String> enumMap =
            Arrays.stream(TaskStatusEnum.values()).collect(Collectors.toMap(TaskStatusEnum::getCode, TaskStatusEnum::getName));


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
