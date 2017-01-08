package com.yuyoukj.util;

public class ApiUtils {

	private static final String repc = "******";
	private static final String repc_inclass = "****";

	public static Integer getTotalPage(Integer totalcount, Integer pagesize) {
		if (totalcount <= 0 || pagesize == 0)
			return 0;

		Integer totalpage = 0;
		if (totalcount % pagesize == 0)
			totalpage = totalcount / pagesize;
		else
			totalpage = (totalcount / pagesize) + 1;
		return totalpage;
	}

	public static String hideUserName(String username) {
		if (username == null || username.length() <= 7) {
			return username;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(username.substring(0, 3));
			sb.append(repc);
			sb.append(username.substring(username.length() - 2, username.length()));
			return sb.toString();
		}
	}

	public static String hideUserName_inClass(String username) {
		if (username == null || username.length() <= 7) {
			return username;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(username.substring(0, 3));
			sb.append(repc_inclass);
			sb.append(username.substring(username.length() - 4, username.length()));
			return sb.toString();
		}
	}
}
