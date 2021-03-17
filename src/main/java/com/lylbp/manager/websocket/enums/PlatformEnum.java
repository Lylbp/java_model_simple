package com.lylbp.manager.websocket.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * websocket推送平台枚举
 *
 * @Author weiwenbin
 * @Date 2020/7/27 下午3:10
 */
@Getter
public enum PlatformEnum {
    /**
     * web平台
     */
    WEB("web", "web平台"),
    /**
     * app平台
     */
    APP("app", "app平台"),
    ;

    private final String code;

    private final String name;

    PlatformEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getByCode(String pCode) {
        PlatformEnum enums = PlatformEnum.getEnumByCode(pCode);
        return null != enums ? enums.getName() : "";
    }

    public static PlatformEnum getEnumByCode(String pCode) {
        return Arrays.stream(PlatformEnum.values()).
                filter(item -> item.getCode().equals(pCode)).findFirst().orElse(null);
    }
}
