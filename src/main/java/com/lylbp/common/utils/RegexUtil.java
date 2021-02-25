package com.lylbp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weiwenbin
 * @date 2019-12-04 10:43
 */
public class RegexUtil {
    // ------------------常量定义
    /**
     * Email正则表达式="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
     */

    public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
    /**
     * 电话号码正则表达式=
     * (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)
     */
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$";

    /**
     * 密码格式的正则
     */
    public static final String PASSWORD = "^[0-9a-zA-Z]{6,18}$";

    public static final String PRICE = "(^[1-9](\\d{1,8})?(\\.\\d{1,2})?$)|(^\\d\\.\\d{1,2}$)";
    /**
     * Integer正则表达式 ^-?(([1-9]\d*$)|0)
     */
    public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";

    /**
     * Integer正则表达式 ^[0-9]$
     */
    public static final String NUMBER_STR = "^0*\\d+";

    /**
     * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
     */
    public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";


    /***
     * 日期正则 支持： YYYY-MM-DD
     */
    public static final String DATE_FORMAT1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

    /**
     * URL正则表达式 匹配 http www ftp
     */
    public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?"
            + "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*"
            + "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" + "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";


    //// ------------------验证方法

    /**
     * 判断字段是否为Email 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isEmail(String str) {
        return regular(str, EMAIL);
    }

    /**
     * 判断是否为电话号码 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isPhone(String str) {
        return regular(str, PHONE);
    }

    /**
     * 判断是否为手机号码 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isMobile(String str) {
        return regular(str, MOBILE);
    }

    /**
     * 判断密码格式是否正确 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isPassword(String str) {
        return regular(str, PASSWORD);
    }


    /**
     * 判断字段是否为INTEGER 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isInteger(String str) {
        return regular(str, INTEGER);
    }

    /**
     * 判断字段是否为Email 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isNumberStr(String str) {
        return regular(str, NUMBER_STR);
    }

    /**
     * 判断字段是否为DOUBLE 符合返回ture
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isDouble(String str) {
        return regular(str, DOUBLE);
    }


    /**
     * 验证2010-12-10
     *
     * @param str the str
     * @return boolean
     */
    public static boolean isDate1(String str) {
        return regular(str, DATE_FORMAT1);
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param str     匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    private static boolean regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0) {
            return false;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Is blank boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() <= 0;
    }

}
