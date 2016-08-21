package Calendar;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {

	protected static List<CalendarEntry> events = new ArrayList<CalendarEntry>();
	
	public static List<String> forTime(int day, int month, int year){
		List<String> list = new ArrayList<String>();
		for(CalendarEntry entry : events){
			if(entry.isInTime(day, month, year)) list.add(entry.getEvent());
		}
		return list;
	}
	
	public static List<String> forTime(int day, int month){
		return forTime(day, month, Year.now().getValue());
	}
	
	public static List<CalendarEntry> getAllEntriesWithName(String name, boolean ignoreCase){
		List<CalendarEntry> entries = new ArrayList<CalendarEntry>();
		for(CalendarEntry entry : events){
			String event = entry.getEvent();
			boolean b = ignoreCase ? event.equalsIgnoreCase(name) : event.equals(name);
			if(b)entries.add(entry);
		}
		return entries;
	}
	
	public static List<CalendarEntry> getAllEntries(){
		return events;
	}
	
}
