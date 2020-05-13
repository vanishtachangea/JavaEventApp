package com.afri.eventmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.afri.eventmanager.EventsUtils.SessionTime;
import com.afri.eventmanager.EventsUtils.TimeFormat;

public class EventTime extends Event {
	EventTime(String t, long d, String s) {
		super(t, d, s);
		// TODO Auto-generated constructor stub
	}

	EventTime(Event e) {
		super(e.getTitle(), e.getDurationMins(), e.getSpeaker());
	}

	int day;
	Date dayStartTime;
	Date dayEndTime;

	SessionTime sessionTime;
	Date eventStartTime;
	Date eventEndTime;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public SessionTime getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(SessionTime sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date startTime) {
		this.eventStartTime = startTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date endTime) {
		this.eventEndTime = endTime;
	}

	public String getEventEndTime(TimeFormat tf) {
		if (eventEndTime != null) {
			SimpleDateFormat dfTime = new SimpleDateFormat(tf.getTimeFormat() + ":mm");
			return dfTime.format(eventEndTime);
		} else
			return "";
	} 

	public String getEventEndTimeStr() {
		if (eventEndTime != null) {
			SimpleDateFormat dfTime = new SimpleDateFormat(TimeFormat.hh + ":mm");
			return dfTime.format(eventEndTime);
		} else
			return "";
	}

	public String getEventStartTime(TimeFormat tf) {
		SimpleDateFormat dfTime = new SimpleDateFormat(tf.getTimeFormat() + ":mm");
		return dfTime.format(eventStartTime);
	}

	public String getEventStartTimeStr() {
		SimpleDateFormat dfTime = new SimpleDateFormat(TimeFormat.hh + ":mm");
		return dfTime.format(eventStartTime);
	}
}
