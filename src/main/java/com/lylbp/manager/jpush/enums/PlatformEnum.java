package com.lylbp.manager.jpush.enums;

import lombok.Getter;

/**
 * jpush平台枚举
 *
 * @author weiwenbin
 * @date 2020/5/28 下午2:45
 */
@Getter
public enum PlatformEnum {
    /**
     * jpush平台枚举
     */
    PLATFORM_ALL("1", "所有平台"),
    PLATFORM_IOS("2", "ios平台"),
    PLATFORM_ANDROID("3", "安卓平台"),
    PLATFORM_IOS_ANDROID("4", "安卓与ios平台");

    /**
     * code
     */
    private String code;

    /**
     * msg
     */
    private String msg;

    PlatformEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
