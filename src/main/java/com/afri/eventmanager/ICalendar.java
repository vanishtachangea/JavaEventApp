package com.afri.eventmanager;

import java.util.List;

public interface ICalendar {
	public List<EventTime> CreateCalendar(String inputCSVFilePath);
	public List<EventTime> getCalendar();
}
