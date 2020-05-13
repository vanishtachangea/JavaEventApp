package com.afri.eventmanager;

import java.util.List;

public class EventCalendar implements ICalendar{
	private List<EventTime> calendar;
	private String inputFilePath="Uploads/Events01.csv";
	public EventCalendar(String inputFilePath)
	{
		this.inputFilePath=inputFilePath;	
		calendar = ProcessEvents.reOrderEvents(inputFilePath);
	}
	public EventCalendar() {
		// TODO Auto-generated constructor stub
		calendar = ProcessEvents.reOrderEvents(this.inputFilePath);
	}
	public List<EventTime> CreateCalendar(String inputCSVFilePath)
	{
		this.inputFilePath=inputCSVFilePath;
		calendar = ProcessEvents.reOrderEvents(inputFilePath);
		return calendar;
		
	}
	public List<EventTime> getCalendar() {
		return calendar;
	}

	public void setCalendar(List<EventTime> calendar) {
		this.calendar = calendar;
	}
}
