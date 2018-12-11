package com.hzxmkuar.wumeihui.base.util;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {

	private static Calendar mCalendar = Calendar.getInstance(); // 当前时间
	private static SimpleDateFormat mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	private static SimpleDateFormat mFormatDateTimeShort = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	private static SimpleDateFormat mDateTimeFormat ;
	public final static String mTimeFormat ="HH:mm:ss";
	public final static String mDateFormatString = "yyyy.MM.dd";
	public final static String mDateFormatYearString = "yyyy年";
	public final static String mDateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
	public final static String mDateTimeFormatStringPhoto = "yyyy-MM-dd HH:mm:ss";
	public final static String mFormatDateString = "yyyy-MM-dd";
	public final static String mFormatDateString2 = "yyyy.MM.dd HH:mm";
	public final static String mFormatDateTimeString = "yyyy.MM.dd HH:mm:ss";
	public final static String mDateTimeFormatStringNoSecond = "yyyy.MM.dd HH:mm";
	public final static String mDateTimeFormatStringNoSecond2 = "yyyy-MM-dd HH:00";
	public final static String mDateTimeFormatStringNoSecondSign = "yyyy年MM月dd日 HH:mm";
	public final static String mDateTimeFormatStringVote = "yyyy年MM月dd日 HH:mm:ss";
	public final static String mWorkDate = "yyyy年MM月dd日";
	public final static String mFormatTimeShort = "HH:mm";
	public final static String mFormatDateShort ="MM月dd日";
	public final static String mFormatDateShort1 ="MM-dd";
	public final static String mBillDateFormatString = "yyyy/MM/dd";
	public final static String shortDateFormatString = "yyyy-MM";
	public final static String mFormatTimeCamara = "HH:mm";
	public final static String mFormatTimeCamaraDetail = "HH:mm:ss";
	public final static String serviceTime = "MM月dd日HH";

	/**
	 * 获取当前日期
	 *
	 * @return
	 */

	public  static String getTime(){
		long time= System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
		String str= String.valueOf(time);
		return str;
	}

	/**
	 * 获取指定日期时间戳
	 *
	 * @return
	 */

	public  static String getLongDateTime(long time){

		String str= String.valueOf(time/1000);
		return str;
	}

	public static String getFormatTime(String format) {
		TimeZone timeZone = TimeZone.getDefault();
		// Calendar calendar = Calendar.getInstance(Locale.CHINA);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(timeZone);
		String time = sdf.format(new Date());
		return time;
	}
	public static Date getDate() {
		mCalendar = Calendar.getInstance(); // 当前时间
		Date mNowDate = mCalendar.getTime();
		return mNowDate;
	}

	/**
	 * 日期格式化成字符串
	 * @param date
	 * @return
	 */
	public static String getDateTimeFormat(String s , Date date){
		mDateTimeFormat = new SimpleDateFormat(s , Locale.CHINA);
		return mDateTimeFormat.format(date);
	}



	public static Date getFormatDateLongTime(String date){

		mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

		Date mDate = null;
		try {
			mDate =  mFormatDateTime.parse(date );
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return mDate ;

	}

	public static Date getFormatDateExpectTime(String date){

		mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

		Date mDate = null;
		try {
			mDate =  mFormatDateTime.parse(date );
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return mDate ;

	}
	public static Date getFormatDateLongTimePhoto(String date){

		mFormatDateTime = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.ENGLISH);
		Date mDate = null;
		try {
			mDate =  mFormatDateTime.parse(date );
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return mDate ;

	}



	/**
	 * 获取当前日期String类型
	 *
	 * @return
	 */
	public static String getDateString() {
		mCalendar = Calendar.getInstance(); // 当前时间
		Date mNowDate = mCalendar.getTime();
		mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return mFormatDateTime.format(mNowDate);
	}

	public static String getDateStringByCalendar(Calendar cal) {
		Date mNowDate = cal.getTime();
		mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return mFormatDateTime.format(mNowDate);
	}

	public static String getDateStringByCalendar(Calendar cal, String format) {
		mFormatDateTime = new SimpleDateFormat(format, Locale.CHINA);
		Date mNowDate = cal.getTime();
		return mFormatDateTime.format(mNowDate);
	}

	/**
	 * 获取当前日期String类型
	 *
	 * @param format
	 * @return
	 */
	public static String getDateString(String format) {
		mFormatDateTime = new SimpleDateFormat(format, Locale.CHINA);
		mCalendar = Calendar.getInstance(); // 当前时间
		Date mNowDate = mCalendar.getTime();
		return mFormatDateTime.format(mNowDate);
	}

	public static String getDateString(Date date, String format) {
		mFormatDateTime = new SimpleDateFormat(format, Locale.CHINA);
		mCalendar = Calendar.getInstance(); // 当前时间
		return mFormatDateTime.format(date);
	}

	/**
	 * 获取当前日期String类型
	 *
	 * @return
	 */
	public static String getDateStringShort() {
		mCalendar = Calendar.getInstance(); // 当前时间
		Date mNowDate = mCalendar.getTime();
		return mFormatDateTimeShort.format(mNowDate);
	}

	/**
	 * 格式化MySql日期
	 *
	 * @param newString
	 * @return
	 */
	public static String getDateFromMySql(String newString) {
		Date mNowDate = new Date(Long.parseLong(newString) * 1000);
		return mFormatDateTime.format(mNowDate);

	}

	public static String getDateStringByAddDays(int days) {
		mCalendar = Calendar.getInstance(); // 当前时间
		mCalendar.add(Calendar.DAY_OF_MONTH, days);
		Date mNowDate = mCalendar.getTime();
		return mFormatDateTimeShort.format(mNowDate);
	}

	public static String getDateStringByAddDays(Date sDate, int days) {

		// 将开始时间赋给日历实例
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTime(sDate);
		// 计算结束时间
		mCalendar.add(Calendar.DATE, days);
		// 返回Date类型结束时间
		Date mNowDate = mCalendar.getTime();
		return mFormatDateTimeShort.format(mNowDate);

	}

	/**
	 * 格式化Json日期【待修改】
	 *
	 * @param json
	 * @return
	 */
	public static String getDateFromJson(String json) {

		String newString = json.replace("/Date(", "").replace(")/", "");
		long now = Long.parseLong(newString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		Date mNowDate = calendar.getTime();
		return mFormatDateTime.format(mNowDate);

	}

	/**
	 * 格式化日期字符串
	 *
	 * @param format
	 * @param time
	 * @return
	 */
	public static String formatDateString(String format, String time) {
		String str = time;
 		SimpleDateFormat formatDate = new SimpleDateFormat(format, Locale.CHINA); // "MM-dd HH:mm"
		Date date = null;
		try {
			date = formatDate.parse(time);
			str = formatDate.format(date);
		} catch (ParseException e) {

		}

		return str;

	}

	/**
	 * 格式化日期字符串
	 *
	 * @param format
	 * @param time
	 * @return
	 */
	public static String formatDateString2(String format, String time)  {
		Date str = null;
		try {
			str = stringToDate(time,mDateTimeFormatString);
		} catch (ParseException e) {
			e.printStackTrace();
		}


		return getDateString(str,format);

	}

	public static String formatDateStringByF1ToF2(String format, String fromat2, String time) {
		String str = time;
		SimpleDateFormat formatDate = new SimpleDateFormat(format, Locale.CHINA); // "MM-dd HH:mm"
		SimpleDateFormat formatDate2 = new SimpleDateFormat(fromat2, Locale.CHINA); // "MM-dd HH:mm"
		Date date = null;
		try {
			str = formatDate2.format(formatDate.parse(time));
		} catch (ParseException e) {
			// e.printStackTrace();
		}

		return str;

	}

	/**
	 * 取得两个时间段的时间间隔 return t2 与t1的间隔分钟 throws ParseException
	 * 如果输入的日期格式不是0000-00-00 格式抛出异常
	 */
	public static int getBetweenMinutes(String t1, String t2) {
		// DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
		int iBetweenMinutes = 0;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			Date d1 = format.parse(t1);
			Date d2 = format.parse(t2);

			long iBetween = d2.getTime() - d1.getTime();

			iBetweenMinutes = (int) (iBetween / 1000 / 60);

		} catch (ParseException ex) {
			iBetweenMinutes = 0;
		}
		return iBetweenMinutes;
	}
	public static int getBetweenMinutesByFormat(String t1, String t2, String format) {
		// DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
		int iBetweenMinutes = 0;
		try {
			DateFormat f = new SimpleDateFormat(format);
			Date d1 = f.parse(t1);
			Date d2 = f.parse(t2);

			long iBetween = d2.getTime() - d1.getTime();

			iBetweenMinutes = (int) (iBetween / 1000 / 60);

		} catch (ParseException ex) {
			iBetweenMinutes = 0;
		}
		return iBetweenMinutes;
	}

	public static int getBetweenMill(String t1, String t2) {
		// DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
		int iBetweenMinutes = 0;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			Date d1 = format.parse(t1);
			Date d2 = format.parse(t2);

			long iBetween = d2.getTime() - d1.getTime();

			iBetweenMinutes = (int) (iBetween);

		} catch (ParseException ex) {
			iBetweenMinutes = 0;
		}
		return iBetweenMinutes;
	}

	/**
	 * 格式化日期
	 *
	 * @param minutes
	 * @return
	 */
	public static String GetDayHourMinutes(String minutes) {
		String result = "";
		long nowMinutes = Long.parseLong(minutes);

		// 按照传入的格式生成一个simpledateformate对象
		long nd = 24 * 60;// 一天的毫秒数
		long nh = 60;// 一小时的毫秒数

		try {
			// 获得两个时间的毫秒时间差异<br />
			long day = 0;
			long hour = 0;
			long min = 0;
			if ((nowMinutes / nd) != 0) {
				day = nowMinutes / nd;
				nowMinutes = nowMinutes - day * nd;
			}

			if (nowMinutes != 0 && (nowMinutes / nh) != 0) {
				hour = nowMinutes / nh;

				nowMinutes = nowMinutes - hour * nh;
			}

			if (nowMinutes != 0) {
				min = nowMinutes;
			}

			if (day != 0) {
				result = String.valueOf(day) + "天";
			}

			if (day != 0) {
				result += String.valueOf(hour) + "小时";
			} else {
				result = String.valueOf(hour) + "小时";
			}
			result += String.valueOf(min) + "分钟";

		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}

		return result;

	}

	/**
	 * 字符串转日期
	 *
	 * @param date
	 * @return
	 */
	public static Date setStringToDate(String date) {
		Date formatDate = getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		try {
			formatDate = sdf.parse(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return formatDate;

	}
	public static Date setStringToDate(String date, String format) {
		Date formatDate = getDate();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			formatDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;

	}

	public static boolean isBeforeDate(String date1, String date2) {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date mDate1, mDate2;
		try {
			mDate1 = sdf.parse(date1);
			mDate2 = sdf.parse(date2);
			flag = mDate1.before(mDate2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;

	}

	public static boolean isBeforeDateSecond(String date1, String date2) {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Date mDate1, mDate2;
		try {
			mDate1 = sdf.parse(date1);
			mDate2 = sdf.parse(date2);
			flag = mDate1.before(mDate2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;

	}

	public static long DateMillis(String date_str, String format){
		if (format==null){
			format="yyyy-MM-dd HH:mm:ss";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date_str).getTime()/1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	public static long DateMillislalal(String date_str, String format){
		if (format==null){
			format="yyyy-MM-dd HH:mm:ss";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date_str).getTime()/1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}







	public static String time_start(long mills, String start){

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		Log.i("===","time_start+"+sdf.format(new Date(mills))+" 08:00:00");
		return sdf.format(new Date(mills))+" "+start;
	}
	public static String time_end(long mills, String start){

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Log.i("===","time_end+"+sdf.format(new Date(mills))+"18:00:00");
		return sdf.format(new Date(mills))+" "+start;
	}

	public static String timelalal(long mills, String start){

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Log.i("===","time_end+"+sdf.format(new Date(mills))+"18:00:00");
		return sdf.format(new Date(mills))+" "+start;
	}

	public static Map<String, Date> getLastWeek(long netTime) {
		// TODO Auto-generated method stub
		Map<String, Date> map = new HashMap<String, Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(netTime);
		int n = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (n == 0) {
			n = 7;
		}
		cal.add(Calendar.DATE, -(7 + (n - 1)));// 上周一的日期
		Date monday = cal.getTime();
		map.put("monday", monday);

		cal.add(Calendar.DATE, 1);
		Date tuesday = cal.getTime();
		map.put("tuesday", tuesday);

		cal.add(Calendar.DATE, 1);
		Date wednesday = cal.getTime();
		map.put("wednesday", wednesday);

		cal.add(Calendar.DATE, 1);
		Date thursday = cal.getTime();
		map.put("thursday", thursday);

		cal.add(Calendar.DATE, 1);
		Date friday = cal.getTime();
		map.put("friday", friday);

		cal.add(Calendar.DATE, 1);
		Date saturday = cal.getTime();
		map.put("saturday", saturday);

		cal.add(Calendar.DATE, 1);
		Date sunday = cal.getTime();
		map.put("sunday", sunday);
		return map;
	}



	public static long cureentMillis(){

		return System.currentTimeMillis()/1000;
	}

	public static String getLastDayOfMonth(int year, int month, boolean select, long netTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(netTime);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-(select?1:0));
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		return  new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
	}
	public static String getFirstDayOfMonth(int year, int month, boolean select, long netTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(netTime);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-(select?1:0));
		cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
		return   new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
	}








	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	// long类型转换为String类型
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType) {
		Date date = null; // long类型转成Date类型
		try {
			date = longToDate(currentTime, formatType);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// long转换为Date类型
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	// string类型转换为long类型
	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType) {
		Date date = null; // String类型转成date类型
		try {
			date = stringToDate(strTime, formatType);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}
}
