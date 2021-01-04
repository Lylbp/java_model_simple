package com.lylbp.manger.minio.enums;

import lombok.Getter;

/**
 * 上传文件类型枚举
 *
 * @author weiwenbin
 * @date 2020/12/11 下午4:16
 */
@Getter
public enum FileEnum {
    /**
     * 图片
     */
    IMG("1", "img"),
    /**
     * 视频
     */
    VIDEO("2", "video"),
    /**
     * 音频
     */
    AUDIO("3", "audio"),
    /**
     * 其他
     */
    OTHER("4", "other");

    private final String code;

    private final String name;

    FileEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
