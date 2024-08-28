package com.lylbp.core.handler;


import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.common.utils.ResResultUtil;
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
                return ResResultUtil.makeRsp(ResResultEnum.PARAM_VALIDATE_FAILED.getCode(),
                        field + fieldError.getDefaultMessage());
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
        log.error(ex.getMessage(), ex);
        return ResResultUtil.makeRsp(ResResultEnum.SYSTEM_ERR.getCode(), ex.getMessage());
    }

//    /**
//     * 处理 SpringMVC 请求地址不存在
//     * <p>
//     * 注意，它需要设置如下两个配置项：
//     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
//     * 2.spring.resources.add-mappings 为false
//     * <p>
//     * 配置完spring.resources.add-mappings 为false后spring.resources.add-mapping.static-locations、spring.mvc.static-path-pattern 失效
//     * 重写WebMvcConfigurer.addResourceHandlers
//     *
//     * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
//     * registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//     * //下面集成swagger需要处理
//     * registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
//     * registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//     * registry.addResourceHandler("/swagger-resources/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//     * }
//     */
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Object noHandlerFoundExceptionHandler() {
//        return ResResultUtil.makeRsp(ResResultEnum.NOT_FOUND);
//    }
}
