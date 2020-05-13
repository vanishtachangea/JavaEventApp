package com.afri.eventmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventsUtils {

	public enum SessionTime {
		AM("Morning session"), PM("Afternoon session");

		private String session;

		SessionTime(String session) {
			this.session = session;
		}

		public String getSessionTime() {
			return session;
		}
	}
	public enum TimeFormat {
		HH("HH"), hh("hh");

		private String timeFormat;

		TimeFormat(String timeFormat) {
			this.timeFormat = timeFormat;
		}

		public String getTimeFormat() {
			return this.timeFormat;
		}
	}

	public static Date calcEndTimeFromTimeStr(String startTime, long duration) {
		SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");
		Date d = null;
		try {
			d = dfTime.parse(startTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calcEndTimeFromTime(
				 d,  duration);
	}
	public static Date calcEndTimeFromTime(
			Date d, long duration) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, (int)duration);

		return cal.getTime();
	}
	public static String timeInStr(Date time)
	{
		SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");
		return dfTime.format(time);
		
	}
	public static String calcEndTimeStrFromTimeStr(String startTime, long duration) {
		SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");
		String endTime = dfTime.format(calcEndTimeFromTimeStr( startTime,  duration));
		return endTime;
	}
	public static Date getDateFromStrHH(String tSTR) {
		SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");
		Date t = null;
		try {
			t = dfTime.parse(tSTR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return t;
	}
	public static Date getNetworkingStart(Date date1, Date dateEarliest, Date dateLatest)
	{
		if(date1.compareTo(dateEarliest) <=0) //Before 4:30pm
		{
			return dateEarliest;
		}
		else //After 4:30pm 
		{
			if(date1.compareTo(dateLatest)<=0)// Before 5:30pm
				return date1;
			else 
				return null;//After 5:30pm
		}
	}
	
	public static Date getNetworkingStartDate(Date date1, String dateEarliest, String dateLatest )
	{
		return getNetworkingStart (date1, getDateFromStrHH(dateEarliest),getDateFromStrHH(dateLatest));
	}
	
	public static long calcDuration(String startTime, String endTime)
	{
		SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");
		Date t1 = null;
		Date t2=null; 
		long diff=0;
		try {
			
			if(startTime!=null && endTime !=null)
			{
				t1 = dfTime.parse(startTime);
				t2 = dfTime.parse(endTime);
				diff = t2.getTime()-t1.getTime();
				diff= diff / (60 * 1000) ;
			}
			


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
	}
}
