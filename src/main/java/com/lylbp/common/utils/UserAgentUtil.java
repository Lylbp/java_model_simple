package com.lylbp.common.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取客户端设备信息
 *
 * @author weiwenbin
 */
public class UserAgentUtil {
    private final UserAgent userAgent;
    private final String userAgentString;
    private final HttpServletRequest request;

    public UserAgentUtil(HttpServletRequest request) {
        this.request = request;
        userAgentString = request.getHeader("User-Agent");
        userAgent = UserAgent.parseUserAgentString(userAgentString);
    }

    /**
     * 获取浏览器
     */
    public Browser getBrowser() {
        return userAgent.getBrowser();
    }

    public Version getBrowserVersion() {
        return userAgent.getBrowserVersion();
    }

    /**
     * 获取操作系统
     */
    public String getOS() {
        if (null == userAgent) {
            return "未知设备";
        }
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 获取设备型号
     */
    public String getDevice() {
        if (null == userAgentString) {
            return "未知设备";
        }
        if (userAgentString.contains("Android")) {
            String[] str = userAgentString.split("[()]+");
            str = str[1].split("[;]");
            String[] res = str[str.length - 1].split("Build/");
            return res[0].trim();
        } else if (userAgentString.contains("iPhone")) {
            String[] str = userAgentString.split("[()]+");
            String res = "iphone" + str[1].split("OS")[1].split("like")[0];
            return res.trim();
        } else if (userAgentString.contains("iPad")) {
            return "iPad";
        } else {
            return getOS().trim();
        }
    }

    /**
     * 获取ip地址
     */
    public String getIpAddr() {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (isBlankIp(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (isBlankIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (isBlankIp(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (isBlankIp(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (isBlankIp(ip)) {
                ip = request.getRemoteAddr();
            }
            // 使用代理，则获取第一个IP地址
            if (!isBlankIp(ip)) {
                if (ip.length() > 15) {
                    ip = ip.split(",")[0];
                } else if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                    //根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ip = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    private boolean isBlankIp(String str) {
        return str == null || str.trim().isEmpty() || "unknown".equalsIgnoreCase(str);
    }
}
