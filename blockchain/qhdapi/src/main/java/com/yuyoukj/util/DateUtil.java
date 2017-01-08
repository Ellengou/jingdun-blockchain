/**
 *
 */
package com.yuyoukj.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
	 * 
	 * 
	 * @param
	 * @return
	 * @exception
	 * @throws
	 */
	public static Long _DAY = new Long(24 * 60 * 60 * 1000);

	public static Long _MINUTE = new Long(60 * 1000);

	public static Date addMinuteFromNow(Integer m) {
		try {
			return new Date(System.currentTimeMillis() + m * _MINUTE);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date convertIntTime(long time) {
		return new Date(time);
	}

	public static long dataString2long(String dataString) {
		return dataString2long(dataString, "yyyy-MM-dd HH:mm:ss");
	}

	public static long dataString2long(String dataString, String format) {
		long lMofifyTime = 0;
		if (dataString.trim().length() == 0) {
			return 0;
		}
		DateFormat Gmt = new SimpleDateFormat(format, Locale.CHINA);
		try {
			lMofifyTime = Gmt.parse(dataString).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return lMofifyTime;
	}

	public static long dataString2long2(String dataString) {
		return dataString2long(dataString, "yyyy-MM-dd");
	}

	public static String getCurrentDate(String fmt) {
		DateFormat d = new SimpleDateFormat(fmt);
		return d.format(new Date());
	}

	public static String getCustomTime(Long time) {
		return time.toString().substring(0, 4) + "-" + time.toString().substring(4, 6) + "-" + time.toString().substring(6, 8);
	}

	public static Long getCustomTime(Long time, Integer day) {
		try {
			return Long.valueOf(strAddDay(DateUtil.long2DateString(time, "yyyy-MM-dd"), day).replace("-", "") + "000000");
		} catch (Exception e) {
			return 0L;
		}
	}

	public static Long getCustomTime(String time) {
		try {
			return Long.valueOf(time.replaceAll("-", "") + "000000");
		} catch (Exception e) {
			return new Long(0);
		}
	}

	public static String getCustomTime2(Long time, Integer day) {
		try {
			return strAddDay(DateUtil.long2DateString(time, "yyyy-MM-dd"), day) + " 00:00:00";
		} catch (Exception e) {
			return null;
		}
	}

	public static Long getCustomTime3(Long time) {
		try {
			return Long.valueOf(strAddDay(DateUtil.long2DateString(time, "yyyy-MM-dd"), -1).replace("-", ""));
		} catch (Exception e) {
			return 0L;
		}
	}

	public static Date getNowDate() {
		return new Date();
	}

	public static String getShortDate() {
		DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		return d.format(new Date());
	}

	public static String getSomedayDate(Integer day, String fmt) {
		return strAddDay(getCurrentDate(fmt), day, fmt);
	}

	public static String long2DateString(long time) {
		return long2DateString(time, "yyyy-MM-dd HH:mm:ss");
	}

	public static String long2DateString(long time, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date(time));
	}

	public static String long2DateString2(long time) {
		return long2DateString(time, "yyyy-MM-dd");
	}

	public static String long2DateString3(long time) {
		return long2DateString(time, "yyyy-MM-dd HH:mm");
	}

	public static void main(String[] args) {
		// System.out.println(DateUtil.dataString2long(DateUtil.getCustomTime2(System.currentTimeMillis(),
		// -7)));
		// System.out.println(System.currentTimeMillis());
		// System.out.println(DateUtil.getTimeStamp(DateUtil.getCustomTime2(System.currentTimeMillis(),
		// 0)));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(DateUtil.strAddDay(-20).replaceAll("-", ""));
	}

	public static String strAddDay(Integer day) {
		return strAddDay(getShortDate(), day, "yyyy-MM-dd");
	}

	public static String strAddDay(String time, Integer day) {
		return strAddDay(time, day, "yyyy-MM-dd");
	}

	public static String strAddDay(String time, Integer day, String fmt) {
		try {
			DateFormat d = new SimpleDateFormat(fmt);
			Date date = new Date(d.parse(time).getTime() + day * _DAY);
			return d.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static int DayInterval(String time1, String time2, String fmt) {
		if (time1 == null || time1.length() == 0 || time2 == null || time2.length() == 0) {
			return 0;
		}

		try {
			DateFormat d = new SimpleDateFormat(fmt);
			Long L = (d.parse(time2).getTime() - d.parse(time1).getTime()) / _DAY;
			return L.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
}
