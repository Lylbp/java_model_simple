package com.lylbp.common.exception;

import com.lylbp.common.enums.ResResultEnum;
import lombok.Getter;

/**
 * 自定义全局异常
 *
 * @author weiwenbin
 */
@Getter
public class ResResultException extends RuntimeException {
    /**
     * code
     */
    private final Integer code;

    public ResResultException(ResResultEnum resResultEnum) {
        super(resResultEnum.getMsg());
        this.code = resResultEnum.getCode();
    }

    public ResResultException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
