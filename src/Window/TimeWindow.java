package Window;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Entry.Entry;

public class TimeWindow implements SmallLinkWindow {
	
	private static Calendar cal = new GregorianCalendar();
	private final List<JButton> timezoneButtons = new ArrayList<JButton>();
	private final Map<Double, String> timezoneMap = new LinkedHashMap<Double, String>();
	private TreeMap<Double, String> sortedTimezoneMap;
	private static TimeZone timeZone = TimeZone.getDefault();
	private static String text = toCalString();
	
	@Override
	public Color getCurrentColour() {
		if(timezoneButtons.size() < 0) return timezoneButtons.get(0).getForeground();
		else return Color.black;
	}

	@Override
	public JInternalFrame getInsideFrame() {
		JInternalFrame frame = new JInternalFrame();
		JPanel timeZonePanel = getTimeZonePanel();
		frame.add(timeZonePanel);
		return frame;
	}


	private JPanel getTimeZonePanel() {
		JPanel timezonePanel = new JPanel();
		timezonePanel.setLayout(new GridLayout(2,1));
		configureMap();
		timezonePanel.add(getImage());
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(12, timezoneMap.size() /2));
		sortedTimezoneMap = new TreeMap<Double, String>(timezoneMap);
		for(Double zone : sortedTimezoneMap.keySet()){
			JPanel subpanel = new JPanel();
			subpanel.setLayout(new GridLayout(2,1));
			subpanel.setName("Subpanel-" + zone);
			JLabel sublabel = new JLabel();
			String text = sortedTimezoneMap.get(zone);
			if(!text.contains("/") || text.contains("GMT")) continue;
			System.out.println(text);
			text = text.substring(text.indexOf('/') + 1);
			sublabel.setText(text);
			JButton subbutton = new JButton();
			subbutton.setText("GMT " + zone / 3600000);
			subbutton.addActionListener(getTimeZoneListener(text));
			subpanel.add(sublabel);
			subpanel.add(subbutton);
			gridPanel.add(subpanel);
		}
		timezonePanel.add(gridPanel);
		return timezonePanel;
	}

	private JLabel getImage() {
		try {
			BufferedImage image = ImageIO.read(new File("timezonemap.jpg"));
			JLabel label = new JLabel(new ImageIcon(image));
			return label;
		} catch (IOException e) {
			ErrorWindow.forException(e);
		}
		return null;
	}

	private void configureMap() {
		for(String key : TimeZone.getAvailableIDs()){
			TimeZone zone = TimeZone.getTimeZone(key);
			double offset = zone.getRawOffset();
			timezoneMap.putIfAbsent(offset, key);
		}
	}

	private ActionListener getTimeZoneListener(String zone) {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("1: " + zone);
				changeZone(zone);
			}
			
		};
	}

	protected void changeZone(String zone) {
		String zoneID = fromName(zone);
		TimeZone tZone = TimeZone.getTimeZone(zoneID);
		System.out.println("2: " + tZone.getRawOffset() /3600000);
		cal = new GregorianCalendar(tZone);
		System.out.println("3: " + cal.get(Calendar.HOUR));
		TimeWindow.timeZone = tZone;
		repaintText(toCalString());
	}

	private String fromName(String zone) {
		for(String id : TimeZone.getAvailableIDs()){
			if(id.contains(zone)){
				System.out.println("id: " + id);
				return id;
			}
		}
		System.out.println("Not found!");
		return TimeZone.getDefault().getID();
	}

	@Override
	public JButton getButton() {
		text = toCalString();
		JButton button = new JButton();
		button.setForeground(getCurrentColour());
		button.setText(text);
		Timer t = new Timer(2000, getActionListener());
		t.setRepeats(true);
		t.start();
		return button;
	}

	private ActionListener getActionListener() {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				String text = TimeWindow.toCalString();
				if(!text.equals(TimeWindow.text))repaintText(text);
			}
		};
	}

	protected static String toCalString() {
		cal = new GregorianCalendar(timeZone); //Recreate the object in order to update the calendar.
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		String sMinute;
		if(minute < 10) sMinute = "0" + String.valueOf(minute);
		else sMinute = String.valueOf(minute);
		String end = cal.get(Calendar.HOUR_OF_DAY) < 12 || cal.get(Calendar.HOUR_OF_DAY) == 24 ? "AM" : "PM";
		String ret = String.valueOf(hour) + ":" + sMinute + " " + end;
		return ret;
	}
	
	protected void repaintText(String string) {
		System.out.println("Changing time to: " + string);
		Entry.buttonMap.get(getName()).setText(string);
		TimeWindow.text = string;
	}

	@Override
	public void setColour(Color c) {
		for(JButton button : timezoneButtons){
			button.setForeground(c);
		}
	}

	@Override
	public String getName() {
		return "time";
	}
	
	@Override
	public String getButtonText(){
		return text;
	}

}
