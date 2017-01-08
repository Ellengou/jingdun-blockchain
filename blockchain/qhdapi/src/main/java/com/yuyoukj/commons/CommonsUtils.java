package com.yuyoukj.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonsUtils {
	private static final String repc = "****";

	public static String getCurrentDate(String fmt) {
		DateFormat d = new SimpleDateFormat(fmt);
		return d.format(new Date());
	}

	public static String getSperMonth(String sdate, String fmt, Integer pos, Integer n) {
		DateFormat Gmt = new SimpleDateFormat(fmt);
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date(Gmt.parse(sdate).getTime()));
			c.add(pos, n);
			return Gmt.format(c.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

}
