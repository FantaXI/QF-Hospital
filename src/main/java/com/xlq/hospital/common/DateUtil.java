package com.xlq.hospital.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	/**
	 * 获取两个日期之间的所有日期
	 *
	 * @param startTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @return
	 */
	public static List<Date> getDaysByDatePeriod(String startTime, String endTime) {

		// 返回的日期集合
		List<Date> days = new ArrayList<Date>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(tempStart.getTime());
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

	/**
	 * 获取当天日期 String
	 */

	public static String getNowDate(){
		String temp_str="";
		Date dt = new Date();
		//最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str=sdf.format(dt);
		return temp_str;
	}
}
