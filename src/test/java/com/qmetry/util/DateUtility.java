package com.qmetry.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class defines DateUtility functionality.
 * 
 * @author yogesh.pathrabe
 */
public class DateUtility {

	/**
	 * This is the protected constructor.
	 * 
	 * @author yogesh.pathrabe
	 */
	protected DateUtility() {

	}

	/**
	 * This method will return the current date.
	 * 
	 * @return Date
	 * @author yogesh.pathrabe
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * This method will return the current date in specified format.
	 * 
	 * @param dateFormat
	 *            as String (refer
	 *            {@link DateProperties.DATE_FORMAT_MM_DASH_DD_DASH_YYYY})
	 * @return String
	 * @author yogesh.pathrabe
	 */
	public static String getDate(String dateFormat) {
		DateFormat currentFormat = new SimpleDateFormat(dateFormat);
		return currentFormat.format(getDate());
	}

	/**
	 * This method will return the date according to mentioned days.
	 * 
	 * @param numberOfDays
	 *            as Integer (like 2, -2, etc.)
	 * @return Date
	 * @author yogesh.pathrabe
	 */
	public static Date getDate(int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
		return calendar.getTime();
	}

	/**
	 * This method will return the date according to mentioned days in specified
	 * format.
	 * 
	 * @param numberOfDays
	 *            as Integer (like 2, -2, etc.)
	 * @param dateFormat
	 *            as String (refer
	 *            {@link DateProperties.DATE_FORMAT_MM_DASH_DD_DASH_YYYY})
	 * @return String
	 * @author yogesh.pathrabe
	 */
	public static String getDate(int numberOfDays, String dateFormat) {
		DateFormat currentFormat = new SimpleDateFormat(dateFormat);
		return currentFormat.format(getDate(numberOfDays));
	}
}
