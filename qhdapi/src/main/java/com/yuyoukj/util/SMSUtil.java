package com.yuyoukj.util;

public class SMSUtil {
	public static String scodeGet() {
		String ret = null;

		int sscode = (int) (Math.random() * 9000 + 1000);
		ret = String.valueOf(sscode);

		//ret = "1234";

		return ret;
	}

	public static String scodeSend(String token, String username, String scode, String customid) {
		// 调用短信api发送短信；
		//		return SMSSend.smssend_token(token, username, scode, customid, 3);
		return null;
	}
}
