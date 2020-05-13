package com.afri.eventmanager;

public class Event implements Comparable<Event>{
	private String title;
	private long durationMins;
	private String speaker;
	public Event(String t, long d, String s)
	{
		this.title=t;
		this.durationMins=d;
		this.speaker=s;
	}
	public Event(Event evt) {
		// TODO Auto-generated constructor stub
		this.title=evt.title;
		this.durationMins=evt.durationMins;
		this.speaker=evt.speaker;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getDurationMins() {
		return durationMins;
	}
	public void setDurationMins(long durationMins) {
		this.durationMins = durationMins;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	@Override
	public int compareTo(Event evnt) {
	    if (getDurationMins() == 0L || evnt.getDurationMins() == 0L) {
	        return 0;
	      }
	      return Long.compare(getDurationMins(), evnt.getDurationMins());
	}
	

}
