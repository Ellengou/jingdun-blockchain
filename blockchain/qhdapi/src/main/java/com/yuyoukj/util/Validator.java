package com.yuyoukj.util;

import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class Validator {
	protected static final String EMAIL_PATTERN = "(([\\w][\\.\\-]?)+[\\w])@([\\w\\-]+\\.)+[\\w]+";

	protected static final String USERNAME_PATTERN = "[\\w]{5,26}";

	public final static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	public final static boolean isEmail(String email) {
		return Pattern.matches(EMAIL_PATTERN, email);
	}

	public final static boolean isEn(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < 1 || str.charAt(i) > 255) {
				return false;
			}
		}
		return true;
	}

	public final static boolean isPassword(String password) {
		if (!isBlank(password) && password.length() >= 6 && password.length() <= 26) {
			return isEn(password);
		}
		return false;
	}

	public final static boolean isUserName(String userName) {
		if (!isBlank(userName)) {
			return Pattern.matches(USERNAME_PATTERN, userName);
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isEmail("affdf@d-d.fdfd-fdf.ddcom"));
	}

	private Validator() {
	}
}
