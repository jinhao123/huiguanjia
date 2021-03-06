package com.ss.platform.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期实用类：用于将字符串转换为日期、时间戳
 * 
 */
public class DateUtil
{
	/***/
	private static Log log = LogFactory.getLog(DateUtil.class);
	/** format脚本变量 */
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final String DATE_PATTENT = "yyyy-MM-dd";
	public static final String DATETIME_PATTENT = "yyyy-MM-dd HH:mm:ss";
	public static final String YEAR_PATTENT = "yyyy";
	public static final String YEAR_MONTH_PATTENT = "yyyy-MM";
	public static final String YYYYMMDD_PATTERN = "yyyyMMdd";

	/**
	 * 私有构造方法
	 */
	private DateUtil()
	{
	}

	/**
	 * 读取系统日期格式在国际化资源文件里date.format（默认的日期格式（yyyy-MM-dd））.
	 * 
	 * @return 呈现在UI的日期String
	 */
	private static String getDatePattern()
	{
		return DATE_PATTENT;
	}

	/**
	 * 获取日期时间格式化默认格式 yyyy-MM-dd hh:mm:ss
	 * 
	 * @return string
	 */
	private static String getDateTimePattern()
	{
		return DateUtil.getDatePattern() + " " + TIME_PATTERN;
	}

	/**
	 * 获取当前时间 格式：hh:mm:ss
	 * 
	 * @return 返回当前时间 格式：hh:mm:ss
	 */
	public static String getTimeNow()
	{
		return DateTimeToString(TIME_PATTERN, new Date());
	}

	/**
	 * 获取当前日期 格式：yyyy-MM-dd
	 * 
	 * @return 格式：yyyy-MM-dd
	 */
	public static String getDateNow()
	{
		return DateTimeToString(getDatePattern(), new Date());
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return 返回Timestamp时间戳
	 */
	public static Timestamp getTimestampNow()
	{
		return dateToTimestamp(new Date());
	}

	/**
	 * 获取当前日期格式 yyyy-MM-dd hh:mm:ss
	 * 
	 * @return the current date/time
	 */
	public static String getDateTimeNow()
	{
		return DateTimeToString(getDateTimePattern(), new Date());
	}

	/**
	 * 取得当前日期Calendar
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Calendar getToday()
	{
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * 将任何时间转换成明天 00:00:00.0000 例如：toNextDay(stringToDateTime("2009-10-02
	 * 15:00:00") 结果：2009-10-03
	 * 
	 * @param date
	 *            需要util.Date 任何时间
	 * @return 下一天的util.Date类型数据
	 * @throws ParseException
	 *             ParseException
	 */
	public static Date toNextDay(Date date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		String todayAsString = dateToString(date);
		Date temp = stringToDate(todayAsString);
		return new Date(temp.getTime() + 86400000);
	}

	/**
	 * 日期转换成指定格式字符串,需要自己定义转换的格式Format
	 * 
	 * @param format
	 *            format脚本变量
	 * @param aDate
	 *            aDate
	 * @return a formatted string representation of the date
	 * @see java.text.SimpleDateFormat
	 */
	public static String DateTimeToString(String format, Date aDate)
	{
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null)
		{
			log.error("aDate is null!");
		}
		else
		{
			df = new SimpleDateFormat(format);
			returnValue = df.format(aDate);
		}
		return returnValue;
	}

	/**
	 * 把日期转换成字符串 Date -> String 用途说明：用于数据展现，用户习惯浏览。
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String dateToString(Date aDate)
	{
		return DateTimeToString(getDatePattern(), aDate);
	}

	/**
	 * 把日期转换成字符串 Date -> String 用途说明：用于数据展现，用户习惯浏览。
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String dateTimeToString(Date aDate)
	{
		return DateTimeToString(getDateTimePattern(), aDate);
	}

	/**
	 * 日期转换 String -> Date 用途说明：日期字符串转换成日期
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date stringToDate(String aMask, String strDate) throws ParseException
	{
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled())
		{
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}
		try
		{
			date = df.parse(strDate);
		}
		catch (ParseException pe)
		{
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return date;
	}

	/**
	 * 转换 String -> util.Date 格式yyyy-MM-dd 用途说明：一般用于数据存储或计算。
	 * 
	 * @param strDate
	 *            the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * @throws ParseException
	 *             异常向上抛出，需要处理需要自己另行捕获处理
	 */
	public static Date stringToDate(String strDate) throws ParseException
	{
		Date aDate = null;
		try
		{
			aDate = stringToDate(getDatePattern(), strDate);
		}
		catch (ParseException pe)
		{
			log.error("这个String不能够转换 '" + strDate + "' 到日期格式java.util.Date, throwing exception");
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return aDate;
	}

	/**
	 * 把日期字符串转换成日期对象
	 * 
	 * @param pattern
	 *            日期格式符
	 * @param source
	 *            日期字符串
	 * @return 日期对象
	 * @author fengjun
	 * @throws ParseException
	 *             ParseException
	 */
	public static Date string2Date(String pattern, String source) throws ParseException
	{
		Date date;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try
		{
			if (source != null && source.length() > 0)
			{
				date = df.parse(source);
			}
			else
			{
				return null;
			}
		}
		catch (ParseException pe)
		{
			throw pe;
		}
		return date;
	}

	/**
	 * @param pattern
	 *            pattern
	 * @param source
	 *            source
	 * @return string
	 * @throws ParseException
	 *             ParseException
	 */
	public static String date2String(String pattern, String source) throws ParseException
	{
		String date_string;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try
		{
			if (source != null && source.length() > 0)
			{
				if ("HH:mm:ss".equals(pattern) && source.length() >= 19)
				{
					source = source.substring(11);
				}
				Date date = df.parse(source);
				date_string = df.format(date);
			}
			else
			{
				return null;
			}
		}
		catch (ParseException pe)
		{
			throw pe;
		}
		return date_string;
	}

	/**
	 * Date2 string.
	 * 
	 * @param pattern
	 *            the pattern
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String date2String(String pattern, Date date)
	{
		String date_string;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		if (date != null)
		{
			date_string = df.format(date);
		}
		else
		{
			return null;
		}
		return date_string;
	}

	/**
	 * 转换 String -> util.Date 格式yyyy-MM-dd hh:mm:ss 用途说明：一般用于数据存储或计算。
	 * 
	 * @param strDate
	 *            the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * @throws ParseException
	 *             异常向上抛出，需要处理需要自己另行捕获处理
	 */
	public static Date stringToDateTime(String strDate) throws ParseException
	{
		Date aDate = null;
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("转换日期格式为: " + getDatePattern());
			}
			aDate = stringToDate(getDateTimePattern(), strDate);
		}
		catch (ParseException pe)
		{
			log.error("这个String不能够转换 '" + strDate + "' 到日期格式java.util.Date, throwing exception");
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return aDate;
	}

	/**
	 * 把日期转换成 Date -> Timestamp
	 * 
	 * @param date
	 *            date
	 * @return Timestamp
	 */
	public static Timestamp dateToTimestamp(Date date)
	{
		return new Timestamp(date.getTime());
	}

	/**
	 * 把日期转换成 String -> Timestamp
	 * 
	 * @param strDate
	 *            date
	 * @return Timestamp
	 * @throws ParseException
	 *             ParseException
	 */
	public static Timestamp stringToTimestamp(String strDate) throws ParseException
	{
		Date date = stringToDate(strDate);
		return dateToTimestamp(date);
	}

	/**
	 * 转换给定日期最大区间值
	 * 
	 * @param pattern
	 *            pattern
	 * @param source
	 *            source
	 * @return Date
	 */
	public static Date getRangeMaxDate(String pattern, String source)
	{
		Date date;
		try
		{
			if (source != null && source.length() > 0)
			{
				if (YEAR_PATTENT.equals(pattern))
				{
					source = source + "-12-31 23:59:59";
				}
				else if (YEAR_MONTH_PATTENT.equals(pattern))
				{
					SimpleDateFormat df = new SimpleDateFormat(pattern);
					Calendar calendar = Calendar.getInstance();
					date = df.parse(source);
					calendar.setTime(date);
					int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					source = source + "-" + day + " 23:59:59";
				}
				else if (DATE_PATTENT.equals(pattern))
				{
					source = source + " 23:59:59";
				}
				else if (TIME_PATTERN.equals(pattern))
				{
					source = "1970-01-01 23:59:59";
				}
				SimpleDateFormat df = new SimpleDateFormat(DATETIME_PATTENT);
				date = df.parse(source);
			}
			else
			{
				return null;
			}
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		return date;
	}

	/**
	 * 获取日期间间隔秒数 getIntervalDays
	 * 
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static int getIntervalSeconds(Date startday, Date endday)
	{

		if (startday.after(endday))
		{
			Date cal = startday;
			startday = endday;
			endday = cal;
		}

		long sl = startday.getTime();

		long el = endday.getTime();

		long ei = el - sl;

		return (int) (ei / 1000);
	}

	/**
	 * 作用：为指定的日期增加或减少天数
	 * 
	 * @param date
	 *            需要添加或减少天数的日期
	 * @param numDays
	 *            添加的天数为整数，减少的天数为负数
	 * @param Date
	 *            返回最终的日期
	 */
	public static Date addDaysToDate(Date date, int numDays)
	{
		if (date == null)
		{
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numDays);

		return c.getTime();
	}

	/**
	 * 作用：将日期格式转换为String类型
	 * 
	 * @param date
	 *            需要转换的日期
	 * @param formatStr
	 *            转换的日期格式
	 * @return String 转换为字符串格式的日期
	 */
	public static String parseDate(Date date, String formatStr)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

		if (date == null)
		{
			return null;
		}
		else
		{
			return dateFormat.format(date);
		}
	}

	/**
	 * 获取 yyyyMMddHHmmss 时间戳
	 * 
	 * @return
	 */
	public static String getTimestamp(String formatType)
	{
		SimpleDateFormat dat = new SimpleDateFormat(formatType);
		return dat.format(new Date()).toString();
	}

	static final String[] weekDays =
	{
		"7", "1", "2", "3", "4", "5", "6"
	};

	/** 某天是星期几 */
	public static String getWeekDay(Calendar cal)
	{
		int d = cal.get(Calendar.DAY_OF_WEEK);
		return weekDays[d - 1];
	}

	public static Date getYesterday()
	{
		Calendar calDate = Calendar.getInstance();
		calDate.add(Calendar.DATE, -1);
		return calDate.getTime();
	}

	public static Date getYesterday(Date date)
	{
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.add(Calendar.DATE, -1);
		return calDate.getTime();
	}

	public static String getYesterdayStr()
	{
		Calendar calDate = Calendar.getInstance();
		calDate.add(Calendar.DATE, -1);
		Date yesterday = calDate.getTime();
		return DateUtil.dateToString(yesterday);
	}

	public static String getYesterdayStr(String dateStr)
	{
		Date date;
		try
		{
			date = DateUtil.stringToDate(dateStr);
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(date);
			calDate.add(Calendar.DATE, -1);
			Date yesterday = calDate.getTime();
			return DateUtil.dateToString(yesterday);
		}
		catch (ParseException e)
		{
			log.error("解析日期出错", e);
		}
		return null;
	}

	public static Date parseStrToDate(String str, String parsePattern)
	{
		Date date = null;
		if (str != null && str.length() > 0 && (!str.equals("null")))
		{
			SimpleDateFormat dateFmt = new SimpleDateFormat(parsePattern);
			try
			{
				date = dateFmt.parse(str);
			}
			catch (ParseException e)
			{
				log.warn("时间解析失败:{}", e);
			}
		}
		return date;
	}
}
