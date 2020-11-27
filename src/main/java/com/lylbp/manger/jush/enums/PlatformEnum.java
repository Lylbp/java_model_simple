package com.lylbp.manger.jush.enums;

import lombok.Getter;

/**
 * @Author weiwenbin
 * @Date 2020/5/28 下午2:45
 */
@Getter
public enum PlatformEnum {
    PLATFORM_ALL("1","所有平台"),
    PLATFORM_IOS("2","ios平台"),
    PLATFORM_ANDROID("3", "安卓平台"),
    PLATFORM_IOS_ANDROID("4", "安卓平台"),

    ;

    private String code;

    private String msg;

    PlatformEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
