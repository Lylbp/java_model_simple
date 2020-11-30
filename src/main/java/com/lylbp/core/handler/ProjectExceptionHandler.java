package com.lylbp.core.handler;


import com.lylbp.core.exception.ResResultException;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.enums.ResResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 项目全局异常处理
 *
 * @author weiwenbin
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ProjectExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            if (fieldError != null) {
                String field = fieldError.getField();
                return ResResultUtil.makeRsp(ResResultEnum.PARAM_VALIDATE_FAILED.getCode(), field + fieldError.getDefaultMessage());
            }
        }

        return ResResultUtil.makeRsp(ResResultEnum.PARAM_VALIDATE_FAILED);
    }

    @ExceptionHandler(ResResultException.class)
    public Object resResultExceptionHandler(ResResultException ex) {
        return ResResultUtil.makeRsp(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public Object securityAccessDeniedExceptionHandle(AccessDeniedException ex) {
        return ResResultUtil.makeRsp(ResResultEnum.NO_AUTHENTICATION);
    }

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandle(Exception ex) {
        log.info(ex.getMessage());
        return ResResultUtil.makeRsp(ResResultEnum.SYSTEM_ERR.getCode(), ex.getMessage());
    }

}
