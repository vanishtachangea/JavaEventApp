package com.afri.eventmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.afri.eventmanager.EventsUtils.SessionTime;
import com.afri.eventmanager.EventsUtils.TimeFormat;

public class ProcessEvents {
	private static final String strPechaKucha = "PechaKucha";
	private static final String strPechaKuchaTime = "5";
	private static final String strMin = "min";
	private static final String regexStr = "[a-zA-Z]";
	private static long amDuration = 210;
	private static long pmDuration = 150;
	private static String eventsStart = "09:00";
	private static String eventsEnd = "16:30";
	private static String amStart = "09:00";
	private static String amEnd = "12:30";
	private static String pmStart = "14:00";
	private static String pmEnd = "16:30";
	private static String dayEnd = "17:30";
	private static List<Event> inputEvents;
	private static long extendedPMduration = 55;

//	public static ProcessEvents(String inputCSVFilePath)
//	{
//		this.inputEvents = ProcessEvents.ParseCSVFile(inputCSVFilePath);
//		//reOrderEvents();
//	}
	public static List<EventTime> reOrderEvents(String inputCSVFilePath) {
		inputEvents = ProcessEvents.ParseCSVFile(inputCSVFilePath);

		amDuration = EventsUtils.calcDuration(amStart, amEnd);
		pmDuration = EventsUtils.calcDuration(pmStart, pmEnd);
		long dayDuration = amDuration + pmDuration;
		long totalDuration = 0;
		long currAmDuration = 0;
		long currPmDuration = 0;
		long noDays = 0;
		Date currTime = null;
		// long amMinDuration = amDuration/2;
		totalDuration = inputEvents.stream().map(x -> x.getDurationMins())
				.collect(Collectors.summingLong((Long::longValue)));

		noDays = (long) Math.ceil(totalDuration / (double) dayDuration);
		List<EventTime> calendarArray = new ArrayList<EventTime>();
		for (int d = 0; d < noDays; d++) {
			currAmDuration = amDuration;
			currPmDuration = pmDuration;
			Collections.sort(inputEvents, Collections.reverseOrder());
			// System.out.println("size" + inputEvents.size());
			currTime = EventsUtils.getDateFromStrHH(amStart);
			for (Event evt : inputEvents) {

				if (evt.getDurationMins() == 0)
					continue;
				else if (evt.getDurationMins() <= currAmDuration) {
					EventTime eventT = new EventTime(evt);
					updateEventTime(eventT, d + 1, SessionTime.AM, currTime);
					currTime = eventT.getEventEndTime();
					calendarArray.add(eventT);
					currAmDuration -= evt.getDurationMins();
					Event modifiedEvent = new Event(evt);
					modifiedEvent.setDurationMins(0);
					inputEvents.set(inputEvents.indexOf(evt), modifiedEvent);
				} else if (currAmDuration == 0) {
					break;
				}
			}
			// Insert the Lunch Break
			EventTime break1 = new EventTime("Lunch", EventsUtils.calcDuration(amEnd, pmStart), "");
			updateEventTime(break1, d + 1, SessionTime.PM, currTime);
			currTime = break1.getEventEndTime();
			calendarArray.add(break1);

			currTime = EventsUtils.getDateFromStrHH(pmStart);
			Collections.sort(inputEvents);
			for (Event evtP : inputEvents) {
				if (evtP.getDurationMins() == 0)
					continue;
				else if (evtP.getDurationMins() <= currPmDuration) {
					EventTime eventT = new EventTime(evtP);
					updateEventTime(eventT, d + 1, SessionTime.PM, currTime);
					currTime = eventT.getEventEndTime();

					calendarArray.add(eventT);
					currPmDuration -= evtP.getDurationMins();
					Event modifiedEvent = new Event(evtP);
					modifiedEvent.setDurationMins(0);
					inputEvents.set(inputEvents.indexOf(evtP), modifiedEvent);
				} else if (currPmDuration == 0) {
					break;
				}
			}

//			if(currPmDuration != 0)
//				System.out.println("not 0:"+d + " "+currPmDuration);
//			else
//				System.out.println("0>"+d+ " "+currPmDuration);
			if (currPmDuration != 0) {
				currPmDuration += extendedPMduration;
				for (Event evtP : inputEvents) {
					if (evtP.getDurationMins() == 0)
						continue;
					else if (evtP.getDurationMins() <= currPmDuration) {
						EventTime eventT = new EventTime(evtP);
						updateEventTime(eventT, d + 1, SessionTime.PM, currTime);
						currTime = eventT.getEventEndTime();

						calendarArray.add(eventT);
						currPmDuration -= evtP.getDurationMins();
						Event modifiedEvent = new Event(evtP);
						modifiedEvent.setDurationMins(0);
						inputEvents.set(inputEvents.indexOf(evtP), modifiedEvent);
					} else if (currPmDuration == 0) {
						break;
					}
				}

			}
 
			// Insert Networking
			// EventTime netWorking = new EventTime("Networking",
			// EventsUtils.calcDuration(EventsUtils.timeInStr(currTime), dayEnd), "");
			currTime = EventsUtils.getNetworkingStartDate(currTime, pmEnd, dayEnd);
			EventTime netWorking = new EventTime("Networking",
					EventsUtils.calcDuration(EventsUtils.timeInStr(currTime), null), "");
			updateEventTime(netWorking, d + 1, SessionTime.PM, currTime);
			currTime = netWorking.getEventEndTime();
			calendarArray.add(netWorking);
		}

		System.out.println("Calendar: ");
		for (int i = 0; i < calendarArray.size(); i++) {
			System.out.println("Day: " + calendarArray.get(i).getDay() + " " + calendarArray.get(i).getSessionTime()
					+ " " + calendarArray.get(i).getEventStartTime(TimeFormat.hh) + " "
					+ calendarArray.get(i).getEventEndTime(TimeFormat.hh) + ": " + calendarArray.get(i).getTitle()
					+ (calendarArray.get(i).getSpeaker().equals("") ? ""
							: " (" + calendarArray.get(i).getSpeaker() + ")")
					+ " " + (calendarArray.get(i).getDurationMins() == (long) 0 ? " "
							: " - " + calendarArray.get(i).getDurationMins() + " Mins"));
		}

		return calendarArray;
	}

	public static List<Event> ParseCSVFile(String filePath) {
		String splitChar = ";";
		List<Event> inputEvents = new ArrayList<>();
		Path pathToFile = Paths.get(filePath);
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

			// read the first line from the text file
			String line = br.readLine();

			// loop until all lines are read
			while (line != null) {
				// use string.split to load a string array with the values from
				// each line of
				// the file, using a comma as the delimiter
				String[] eventRow = line.split(splitChar);

				Event event = createEvent(eventRow);

				// adding event into ArrayList
				inputEvents.add(event);

				// read next line before looping
				// if end of file reached, line would be null
				line = br.readLine();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();

		}

		return inputEvents;

	}

	public static Event createEvent(String[] data) {
		String title = data[0];
		String duration = data[1].replace(strPechaKucha, strPechaKuchaTime);
		duration = duration.replaceAll(regexStr, "");
		long durationMins = Long.parseLong(duration);
		String speaker = data[2];

		return new Event(title, durationMins, speaker);
	}

	public static void updateEventTime(EventTime eventT, int d, SessionTime s, Date currTime) {
		eventT.setDay(d);
		eventT.setSessionTime(s);
		eventT.setEventStartTime(currTime);
		if (eventT.getDurationMins() != 0)
			eventT.setEventEndTime(EventsUtils.calcEndTimeFromTime(currTime, eventT.getDurationMins()));
		else
			eventT.setEventEndTime(null);

	}

}
