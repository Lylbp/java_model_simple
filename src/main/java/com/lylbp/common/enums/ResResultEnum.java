package com.lylbp.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 响应码枚举
 *
 * @author weiwenbin
 */

@Getter
public enum ResResultEnum {
    /**
     * ===========================系统相关===============================
     */
    SUCCESS(200, "success"),
    FAIL(400, "fail"),
    NOT_FOUND(404, "接口不存在"),
    SYSTEM_ERR(1001, "系统繁忙,请稍后重试"),
    ILLEGAL_REQUEST(1002, "您正在进行非法请求"),
    DATA_NOT_EXIST(1003, "对不起,您查询的数据不存在,请刷新页面"),
    DATA_HAVE_EXIST(1004, "数据已存在"),
    PARAM_ERROR(1005, "参数错误,请检查参数"),
    PARAM_VALIDATE_FAILED(1006, "参数验证失败,请检查参数"),
    UPLOAD_FAIL(1007, "上传失败"),
    UPLOAD_RESOURCE_SUFFIX_ERROR(1008, "不允许上传文件类型"),
    RESOURCE_NOT_EXIT(1009, "资源不存在"),

    /**
     * ===========================登录相关===============================
     */
    ACCOUNT_ERR(2001, "当前登录账号异常"),
    NO_LOGIN(2002, "登录凭证已失效"),
    ACCOUNT_DISABLE(2003, "您的账号已被禁用"),
    ACCOUNT_LOGIN_ERR(2004, "账号或密码错误"),
    CODE_LOGIN_ERR(2005, "验证码错误"),
    USER_PASSWORD_ERROR(2006, "您的密码不能少于六位"),
    USER_PHONE_ERROR(2007, "您的手机号格式不正确"),
    CONFIRM_PASSWORD_ERR(2007, "两次密码输入不一致"),
    /**
     * ===========================RBAC相关===============================
     */
    NO_AUTHENTICATION(3001, "您没有权限进行此次操作,请联系管理员分配权限!"),
    NO_ROLE_EXIT(3002, "角色不存在或已被删除,请刷新页面"),
    ROLE_NAME_EXIT(3003, "角色名称已存在"),
    NO_PERMISSION_EXIT(3004, "权限不存在或已被删除,请刷新页面"),
    NO_MENU_EXIT(3005, "菜单不存在或已被删除,请刷新页面"),
    ROLE_PERMISSION_EXIT(3006, "该角色已添加该权限,请勿重复添加"),
    USER_ROLE_EXIT(3007, "该用户已添加该角色,请勿重复添加"),
    MENU_HAS_SON_CAN_NOT_DELETE(3008, "菜单下含有子级菜单,请先删除子级菜单"),
    PARENT_MENU_NOT_EXIT(3009, "父级菜单不存在或已被删除,请刷新页面"),
    NO_ANY_AUTHENTICATION(3010, "您没有被分配任何权限,请联系管理员"),
    ROLE_MENU_EXIT(3006, "该角色已添加该菜单,请勿重复添加"),
    /**
     * ===========================后台用户相关===============================
     */
    ADMIN_USER_NO_EXIST(3008, "用户不存在"),
    ACTION_ADMIN_USER_IS_SUPPER(3009, "您不可以对超级管理员进行本次操作"),
    ACTION_ADMIN_PHONE_EXIT(3010, "对不起,账号已存在"),


    /**
     * =========================上传资源相关==================================
     */
    UPLOAD_RESOURCE_IS_CODE(4001, "不允许上传的资源类型"),
    UPLOAD_IMG_NOT_ALLOW(4002, "不允许上传的图片类型(目前支持jpg,gif,bmp,png,jpeg)"),
    UPLOAD_IMG_VIDEO_ALLOW(4003, "不允许上传的视频类型(目前支持mp4,flv,wmv,avi)"),
    UPLOAD_IMG_AUDIO_ALLOW(4003, "不允许上传的音频类型(目前支持mp3)"),

    /**
     * =========================定时任务相关==================================
     */
    EXECUTION_CLASS_NAME_EXIT(5001, "该执行类名称已存在"),
    ;

    private final Integer code;

    private final String msg;

    ResResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValueByCode(Integer pCode) {
        ResResultEnum resultEnum = Arrays.stream(ResResultEnum.values()).filter(resResultEnum -> resResultEnum.getCode().equals(pCode)).findFirst().orElse(null);

        return null != resultEnum ? resultEnum.getMsg() : "";
    }
}
