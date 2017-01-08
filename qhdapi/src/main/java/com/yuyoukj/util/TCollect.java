package com.yuyoukj.util;

public class TCollect {

	public static boolean isNextMonth(Long unixtime) {

		if (unixtime == null || unixtime.longValue() <= 0) {
			return false;
		}

		try {
			String now = DateUtil.long2DateString(unixtime, "yyyyMMdd");
			String perDay = DateUtil.strAddDay(now, -1, "yyyyMMdd");
			perDay = perDay.substring(4, 6);
			now = now.substring(4, 6);

			if (now.equals(perDay)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static void main(String args[]) {

		Long unixtime = System.currentTimeMillis();
		String now = DateUtil.long2DateString(unixtime, "yyyyMMdd");
		String perDay = DateUtil.strAddDay(now, 1, "yyyyMMdd");
		System.out.println(now);
		System.out.println(perDay);

	}
}
