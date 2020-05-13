package com.afri.eventmanager;

import java.util.List;

public class TestEventManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		EventCalendar calendar = new EventCalendar();
//		List<EventTime> c = calendar.getCalendar();
		
		ICalendar calendar2=new EventCalendar();
		List<EventTime> c2 = calendar2.getCalendar();

	}

}
