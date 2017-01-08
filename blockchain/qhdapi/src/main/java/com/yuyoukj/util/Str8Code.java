package com.yuyoukj.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class Str8Code {
	/**
	 * 用URLDecoder解码UTF-8
	 * 
	 * @param str
	 * @return
	 */
	public static String de8Code(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用URLEncoder编码UTF-8
	 * 
	 * @param str
	 * @return
	 */
	public static String en8Code(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) {
		try {
			// String str = URLEncoder.encode("测好asf", "UTF-8");
			// System.out.println(str);
			System.out.println(URLDecoder.decode("Str8Code.java", "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
