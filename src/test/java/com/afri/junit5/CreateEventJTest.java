package com.afri.junit5;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import com.afri.eventmanager.Event;
import com.afri.eventmanager.EventsUtils;
import com.afri.eventmanager.ProcessEvents;

class CreateEventJTest {

	@Test
	void testDataFromCSV() {
		String[] data = { "Deployathon", "90min", "David" };
		Event expectedResult = new Event("Deployathon", 90, "David");
		Event actualResult = ProcessEvents.createEvent(data);
		assertEquals(expectedResult.getSpeaker(), actualResult.getSpeaker());
		assertEquals(expectedResult.getDurationMins(), actualResult.getDurationMins());
		assertEquals(expectedResult.getTitle(), actualResult.getTitle());

		String[] data2 = { "Deployathon2", "dfasf90minasdfsdf", "David2" };
		expectedResult = new Event("Deployathon2", 90, "David2");
		actualResult = ProcessEvents.createEvent(data2);
		assertEquals(expectedResult.getSpeaker(), actualResult.getSpeaker());
		assertEquals(expectedResult.getDurationMins(), actualResult.getDurationMins());
		assertEquals(expectedResult.getTitle(), actualResult.getTitle());

		String[] data3 = { "Deployathon3", "PechaKucha", "David3" };
		expectedResult = new Event("Deployathon3", 5, "David3");
		actualResult = ProcessEvents.createEvent(data3);
		assertEquals(expectedResult.getSpeaker(), actualResult.getSpeaker());
		assertEquals(expectedResult.getDurationMins(), actualResult.getDurationMins());
		assertEquals(expectedResult.getTitle(), actualResult.getTitle());

		String[] data4 = { "Deployathon4", "garbage64garbage", "David4", "text1", "text2" };
		expectedResult = new Event("Deployathon4", 64, "David4");
		actualResult = ProcessEvents.createEvent(data4);
		assertEquals(expectedResult.getSpeaker(), actualResult.getSpeaker());
		assertEquals(expectedResult.getDurationMins(), actualResult.getDurationMins());
		assertEquals(expectedResult.getTitle(), actualResult.getTitle());

		String[] data5 = { "Deployathon5", "nonumber", "David5", "text1", "text2" };
		assertThrows(NumberFormatException.class, () -> {
			ProcessEvents.createEvent(data5);
		});

	}

	@Test
	void testGetNetworkingStartDate() {
		Date date1 = EventsUtils.getDateFromStrHH("16:10");
		Date date2 = EventsUtils.getDateFromStrHH("16:30");
		Date date3 = EventsUtils.getDateFromStrHH("17:30");
		Date expectedResult = date2;
		Date actualResult = EventsUtils.getNetworkingStart(date1, date2, date3);
		System.out.println("1: "+actualResult);
		assertEquals(expectedResult, actualResult);

		date1 = EventsUtils.getDateFromStrHH("16:25");
		expectedResult = date2;
		actualResult = EventsUtils.getNetworkingStart(date1, date2, date3);
		System.out.println("2: "+actualResult);
		assertEquals(expectedResult, actualResult);
		
		date1 = EventsUtils.getDateFromStrHH("16:35");
		expectedResult = date1;
		actualResult = EventsUtils.getNetworkingStart(date1, date2, date3);
		System.out.println("3: "+actualResult);
		assertEquals(expectedResult, actualResult);

		date1 = EventsUtils.getDateFromStrHH("17:00");
		expectedResult = date1;
		actualResult = EventsUtils.getNetworkingStart(date1, date2, date3);
		System.out.println("4: "+actualResult);
		assertEquals(expectedResult, actualResult);
		
		date1 = EventsUtils.getDateFromStrHH("17:30");
		expectedResult = date1;
		actualResult = EventsUtils.getNetworkingStart(date1, date2, date3);
		System.out.println("5: "+actualResult);
		assertEquals(expectedResult, actualResult);
		
		date1 = EventsUtils.getDateFromStrHH("17:35");
		expectedResult = null;
		actualResult = EventsUtils.getNetworkingStart(date1, date2, date3);
		System.out.println("6: "+actualResult);
		assertEquals(expectedResult, actualResult); 
	}
	
	

}
