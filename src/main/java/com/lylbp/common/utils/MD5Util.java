package com.lylbp.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	private static final String saltStr = "1a2b3c";

	public static String MD5(String src) {
		return DigestUtils.md5Hex(src);
	}

	
	public static String MD5(String formPass, String salt) {
		if (null == salt || "".equals(salt) || salt.length() < 4){
			salt = saltStr;
		}
		String str = ""+salt.charAt(0)+salt.charAt(1) + formPass +salt.charAt(2) + salt.charAt(3);
		return MD5(str);
	}

}
