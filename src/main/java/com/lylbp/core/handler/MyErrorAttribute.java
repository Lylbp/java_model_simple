package com.lylbp.core.handler;

import com.lylbp.common.enums.ResResultEnum;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2023/11/2 下午9:14
 */
@Component
public class MyErrorAttribute extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Object status = errorAttributes.get("status");
        Object message = errorAttributes.get("message");


        errorAttributes.put("code", status);
        errorAttributes.put("msg", message);
        errorAttributes.put("data", "");

        if (status instanceof Integer  && status.equals(404)){
            errorAttributes.put("msg", ResResultEnum.NOT_FOUND.getMsg());
        }


        return errorAttributes;
    }
}
