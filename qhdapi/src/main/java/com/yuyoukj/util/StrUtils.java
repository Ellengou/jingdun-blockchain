package com.yuyoukj.util;

public class StrUtils {
	public static boolean isBlank(String s) {
		if (s == null || s.length() == 0) {
			return true;
		} else
			return false;
	}
}
