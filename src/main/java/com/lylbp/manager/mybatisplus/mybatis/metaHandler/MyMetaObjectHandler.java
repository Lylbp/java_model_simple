package com.lylbp.manager.mybatisplus.mybatis.metaHandler;//package com.lylbp.manager.mybatisplus.mybatis.metaHandler;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import com.lylbp.common.constant.ProjectConstant;
//import com.lylbp.common.enums.TrueOrFalseEnum;
//import com.lylbp.core.properties.ProjectProperties;
//import com.lylbp.project.service.AuthService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//import java.util.Objects;
//import java.util.function.Supplier;
//
///**
// * @author weiwenbin
// * @date 2020/7/9 上午10:42
// */
//@Component
//@Slf4j
//public class MyMetaObjectHandler implements MetaObjectHandler {
//    @Resource
//    private AuthService authService;
//
//    @Resource
//    private HttpServletRequest request;
//
//    @Resource
//    private ProjectProperties projectProperties;
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        this.strictInsertFill(metaObject, "createAt", Date.class, new Date());
//        this.strictInsertFill(metaObject, "isValid", String.class, TrueOrFalseEnum.TRUE.getCode());
//
//        String actionId;
//        try {
//            request.getHeader(ProjectConstant.AUTHENTICATION);
//            actionId = getActionId();
//        } catch (Exception exception) {
//            actionId = projectProperties.getSuperAdminId();
//        }
//        this.strictInsertFill(metaObject, "createBy", String.class, actionId);
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, "updateAt", Date.class, new Date());
//        String actionId;
//        try {
//            request.getHeader(ProjectConstant.AUTHENTICATION);
//            actionId = getActionId();
//        } catch (Exception exception) {
//            actionId = projectProperties.getSuperAdminId();
//        }
//        this.strictUpdateFill(metaObject, "updateBy", String.class, actionId);
//    }
//
//    /**
//     * 获取当前操作人对象
//     *
//     * @return String
//     */
//    private String getActionId() {
////        SecurityUser securityUser = authService.getUserFromRequest(request);
////        String adminId = securityUser.getSysUserVO().getId();
////
////        return "".equals(adminId) || null == adminId ? "" : adminId;
//
//        return "";
//    }
//
//    @Override
//    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<Object> fieldVal) {
//        Object obj = fieldVal.get();
//        if (Objects.nonNull(obj)) {
//            metaObject.setValue(fieldName, obj);
//        }
//        return this;
//    }
//}
