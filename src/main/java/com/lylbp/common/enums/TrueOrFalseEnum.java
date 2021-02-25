package com.lylbp.common.enums;


import lombok.Getter;

/**
 * @author weiwenbin
 * @date 2020/6/30 上午8:49
 */
@Getter
public enum TrueOrFalseEnum {
    /**
     * 是
     */
    FALSE("0", "否"),

    /**
     * 否
     */
    TRUE("1", "是");

    private final String code;

    private final String name;

    TrueOrFalseEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
