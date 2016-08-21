package Calendar;

import java.time.Year;

public class CalendarEntry {

	private String event;
	private int day;
	private int month;
	private int year;
	
	public CalendarEntry(String event, int day, int month){
		this(event, day, month, Year.now().getValue());
	}

	public CalendarEntry(String event, int day, int month, int year) {
		this.event = event;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String getEvent(){
		return event;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}

	public boolean isInTime(int day, int month, int year) {
		return this.day == day && this.month == month && this.year == year;
	}
	
	public void addToCalender(){
		Scheduler.events.add(this);
	}
	
	@Override
	public String toString(){
		return event + ": " +day + "/" + month + "/" + year;
	}
	
}
