package com.auto.client.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

	public static Date convert(long value) {
		Calendar calendar = Calendar.getInstance();
		int offset = calendar.get(Calendar.ZONE_OFFSET);
		int dst = calendar.get(Calendar.DST_OFFSET);
		calendar.setTimeInMillis(value);
		calendar.add(Calendar.MILLISECOND, -(offset + dst));
		return calendar.getTime();
	}
	
	public static long convert(Date date) {
		return date.getTime();
	}
	
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date parse(String dateStr, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
}
