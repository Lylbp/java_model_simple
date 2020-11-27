package com.lylbp.project.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lylbp.common.entity.LabelVO;
import com.lylbp.common.interfaces.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;


/**
 * @author weiwenbin
 */
@Getter
public enum GenderEnum implements IBaseEnum<String> {
    /**
     * 男
     */
    MALE("1", "男"),
    /**
     * 女
     */
    WOMAN("2", "女");

    @EnumValue
    private final String code;

    private final String name;

    @JsonValue
    private final LabelVO labelVO;

    @Override
    public String getValue() {
        return code;
    }

    GenderEnum(String code, String name) {
        this.code = code;
        this.name = name;
        this.labelVO = new LabelVO(code, name);
    }

    public static String getByCode(String pCode) {
        GenderEnum genderEnum = getEnumByCode(pCode);
        return null != genderEnum ? genderEnum.getName() : "";
    }

    public static GenderEnum getEnumByCode(String pCode) {
        return Arrays.stream(GenderEnum.values()).filter(item -> {
            return item.getCode().equals(pCode);
        }).findFirst().orElse(null);
    }
}
