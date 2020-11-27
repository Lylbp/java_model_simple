//package com.lylbp.manger.mybatisPlus.mybatisPlus.metaHandler;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import ProjectConstant;
//import TokenUtil;
//import com.lylbp.project.enums.TrueOrFalseEnum;
//import SecurityUser;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// * @Author weiwenbin
// * @Date 2020/7/9 上午10:42
// */
//@Component
//public class MyMetaObjectHandler implements MetaObjectHandler {
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
//        this.strictInsertFill(metaObject, "createBy", String.class, getActionId());
//        this.strictInsertFill(metaObject, "isValid", String.class, TrueOrFalseEnum.TRUE.getCode());
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
//        this.strictUpdateFill(metaObject, "updateBy", String.class, getActionId());
//    }
//
//    /**
//     * 获取当前操作人对象
//     *
//     * @return String
//     */
//    private String getActionId() {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (null == requestAttributes) {
//            return "";
//        }
//        HttpServletRequest request = requestAttributes.getRequest();
//        SecurityUser securityUser = TokenUtil.getClazzFromHeader(request, ProjectConstant.AUTHENTICATION, SecurityUser.class);
//        String adminId = securityUser.getAdmin().getAdminId();
//        return "".equals(adminId) || null == adminId ? "" : adminId;
//    }
//}
