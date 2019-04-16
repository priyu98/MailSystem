package com.mail.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	public static String getDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(new Date());
	}
	public static String getDatetime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}
	public static String getDatetimems() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sf.format(new Date());
	}
	
	public static String getDatetimemslong() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sf.format(new Date());
	}
}
