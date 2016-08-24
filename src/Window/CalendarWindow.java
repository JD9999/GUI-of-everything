package Window;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Calendar.CalendarEntry;
import Calendar.Scheduler;
import Settings.GUISetting;

public class CalendarWindow implements Window {

	private final Calendar cal = Calendar.getInstance();
	private JLabel calHeading;
	private JLabel monthHeading;
	private JTable table;
	private boolean repaint = false;
	private String month;
	
	@Override
	public String getName() {
		return "Calendar";
	}

	@Override
	public JInternalFrame getInsideFrame() {
		JInternalFrame retFrame = new JInternalFrame();
		JSplitPane overseerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		int monthKey = cal.get(Calendar.MONTH);
		month = switchMonth(monthKey);
		System.out.println("The month is: " + month);
		JTable table = new JTable(buildTable(monthKey));
		this.table = table;
		JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightSplit.setLeftComponent(this.table);	
		rightSplit.setRightComponent(getAdditionComponent());
		overseerPanel.setLeftComponent(getLabelPanel(month));
		overseerPanel.setRightComponent(rightSplit);
		retFrame.add(overseerPanel);
		System.out.println("Table rows: " + this.table.getRowCount());
		System.out.println("Table columns: " + this.table.getColumnCount());
		
		if(repaint){
			System.out.println("Repaint done!");
		}
		
		//retFrame.pack();
		return retFrame;
	}

	private DefaultTableModel buildTable(int monthKey) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		System.out.println("The date is: " + day);
		boolean leapYear = isLeapYear(cal.get(Calendar.YEAR));
		int incrementor = 0;
		String zeroGapField = getField(day, incrementor, monthKey, leapYear);
		if(zeroGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else{
				monthKey = 0;
				month = "January";
				day = incrementor = 1;
				incrementor --;
			}
		}
		incrementor ++;
		String oneGapField = getField(day, incrementor, monthKey, leapYear);
		if(oneGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else monthKey = 0;
			month = switchMonth(monthKey);
			day = incrementor = 1;
			incrementor --;
		}
		incrementor ++;
		String twoGapField = getField(day, incrementor, monthKey, leapYear);
		if(twoGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else monthKey = 0;
			month = switchMonth(monthKey);
			day = incrementor = 1;
			incrementor --;
		}
		incrementor++;
		String threeGapField = getField(day, incrementor, monthKey, leapYear);
		if(threeGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else monthKey = 0;
			month = switchMonth(monthKey);
			day = incrementor = 1;
			incrementor --;
		}
		incrementor ++;
		String fourGapField = getField(day, incrementor, monthKey, leapYear);
		if(fourGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else monthKey = 0;
			month = switchMonth(monthKey);
			day = incrementor = 1;
			incrementor --;
		}
		incrementor ++;
		String fiveGapField = getField(day, incrementor, monthKey, leapYear);
		if(fiveGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else monthKey = 0;
			month = switchMonth(monthKey);
			day = incrementor = 1;
			incrementor --;
		}
		incrementor ++;
		String sixGapField = getField(day, incrementor, monthKey, leapYear);
		if(sixGapField.split("/")[0].equals("1")){
			if(monthKey !=11) monthKey++;
			else monthKey = 0;
			month = switchMonth(monthKey);
			day = incrementor = 1;
			incrementor --;
		}
		System.out.println("Creating table model");
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(zeroGapField);
		model.addColumn(oneGapField);
		model.addColumn(twoGapField);
		model.addColumn(threeGapField);
		model.addColumn(fourGapField);
		model.addColumn(fiveGapField);
		model.addColumn(sixGapField);
		model.addRow(new String[]{zeroGapField, oneGapField, twoGapField, threeGapField, fourGapField, fiveGapField, sixGapField});
		List<String> zeroGapList = getAllEvents(zeroGapField.split("/"));
		List<String> oneGapList = getAllEvents(oneGapField.split("/"));
		List<String> twoGapList = getAllEvents(twoGapField.split("/"));
		List<String> threeGapList = getAllEvents(threeGapField.split("/"));
		List<String> fourGapList = getAllEvents(fourGapField.split("/"));
		List<String> fiveGapList = getAllEvents(fiveGapField.split("/"));
		List<String> sixGapList = getAllEvents(sixGapField.split("/"));
		String zeroGapEvent;
		String oneGapEvent;
		String twoGapEvent;
		String threeGapEvent;
		String fourGapEvent;
		String fiveGapEvent;
		String sixGapEvent;
		int max = Math.max(zeroGapList.size(), oneGapList.size());
		max = Math.max(max, twoGapList.size());
		max = Math.max(max, threeGapList.size());
		max = Math.max(max, fourGapList.size());
		max = Math.max(max, fiveGapList.size());
		max = Math.max(max, sixGapList.size());
		for(int i = 0; i < max; i++){
			if(zeroGapList.size() > i) zeroGapEvent = zeroGapList.get(i);
			else zeroGapEvent = "";
			if(oneGapList.size() > i) oneGapEvent = oneGapList.get(i);
			else oneGapEvent = "";
			if(twoGapList.size() > i) twoGapEvent = twoGapList.get(i);
			else twoGapEvent = "";
			if(threeGapList.size() > i) threeGapEvent = threeGapList.get(i);
			else threeGapEvent = "";
			if(fourGapList.size() > i) fourGapEvent = fourGapList.get(i);
			else fourGapEvent = "";
			if(fiveGapList.size() > i) fiveGapEvent = fiveGapList.get(i);
			else fiveGapEvent = "";
			if(sixGapList.size() > i) sixGapEvent = sixGapList.get(i);
			else sixGapEvent = "";
			model.addRow(new String[]{zeroGapEvent, oneGapEvent, twoGapEvent, threeGapEvent, fourGapEvent, fiveGapEvent, sixGapEvent});
		}
		return model;
	}

	private List<String> getAllEvents(String[] split) {
		int day = Integer.parseInt(split[0]);
		int month = Integer.parseInt(split[1]);
		return Scheduler.forTime(day, month);
	}

	private boolean isLeapYear(int year) {
		  if (year % 4 != 0) {
		    return false;
		  } else if (year % 400 == 0) {
		    return true;
		  } else if (year % 100 == 0) {
		    return false;
		  } else {
		    return true;
		  }
		}
	
	private JPanel getAdditionComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JTextArea area = new JTextArea();
		area.setText("Event description");
		JPanel additionPanel = getAdditionPanel();
		panel.add(area);
		panel.add(additionPanel);
		return panel;
	}

	private JPanel getAdditionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		JTextField event = new JTextField();
		event.setText("Event name");
		JTextField description = new JTextField();
		description.setText("Event description");
		JPanel dateField = getDatePanel();
		JButton button = new JButton("add event");
		button.addActionListener(getAdditionListener(dateField, event));
		panel.add(event);
		panel.add(description);
		panel.add(dateField);
		panel.add(button);
		JButton printButton = new JButton();
		printButton.setText("Print all events");
		printButton.addActionListener(getPrintListener());
		panel.add(printButton);
		return panel;
	}

	private ActionListener getPrintListener() {
		return listener -> {
			List<CalendarEntry> events = Scheduler.getAllEntries();
			for(CalendarEntry e : events) System.out.println(e.toString());
		};
	}

	private ActionListener getAdditionListener(JPanel panel, JTextField box) {
		return listener ->{
			String event = box.getText();
			int year = 0;
			int month = 0;
			int day = 0;
			for(Component c : panel.getComponents()){
				if(c instanceof JTextField){
					switch(c.getName()){
					case "year":
						year = Integer.parseInt(((JTextField) c).getText());
						break;
					case "month":
						month = Integer.parseInt(((JTextField)c).getText());
						break;
					case "day":
						day = Integer.parseInt(((JTextField)c).getText());
						break;
					case "slash":
						break;
					default:
						System.out.println("Unknown operation for name: " + c.getName() + " isEditable: " + String.valueOf(((JTextField) c).isEditable() + " and text: " + ((JTextField) c).getText()));
					}
				}
			}
			CalendarEntry entry = new CalendarEntry(event, day, month, year);
			entry.addToCalender();
			repaint = true;
			//Entry.requestRepaint(this);
			table.setModel(buildTable(cal.get(Calendar.MONTH)));
		};
	}

	private JPanel getDatePanel() {
		Date date = cal.getTime();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,5));
		JTextField dayField = new JTextField();
		JTextField slashFieldOne = new JTextField();
		JTextField monthField = new JTextField();
		JTextField slashFieldTwo = new JTextField();
		JTextField yearField = new JTextField();
		
		dayField.setText(String.valueOf(cal.get(Calendar.DAY_OF_WEEK)));
		slashFieldOne.setText("/");
		monthField.setText(String.valueOf(date.getMonth() + 1));
		slashFieldTwo.setText("/");
		yearField.setText(String.valueOf(date.getYear() + 1900));
		
		dayField.setName("day");
		slashFieldOne.setName("slash");
		monthField.setName("month");
		slashFieldTwo.setName("slash");
		yearField.setName("year");
		
		slashFieldOne.setEditable(false);
		slashFieldTwo.setEditable(false);
		
		panel.add(dayField);
		panel.add(slashFieldOne);
		panel.add(monthField);
		panel.add(slashFieldTwo);
		panel.add(yearField);
		
		return panel;
	}

	private JPanel getLabelPanel(String month) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 20 - month.length(); i++){
			builder.append(" ");
		}
		JLabel monthHeading = new JLabel();
		monthHeading.setText(builder.toString() + month);
		monthHeading.setForeground(Color.RED);
		monthHeading.setHorizontalTextPosition(SwingConstants.CENTER);
		monthHeading.setFont(monthHeading.getFont().deriveFont((float) 20));
		this.monthHeading = monthHeading;
		JLabel frameHeading = new JLabel();
		frameHeading.setText("         " + getName());
		frameHeading.setForeground(Color.RED);
		frameHeading.setHorizontalTextPosition(SwingConstants.CENTER);
		frameHeading.setFont(frameHeading.getFont().deriveFont((float) 24));
		this.calHeading = frameHeading;
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(2,5));
		JLabel blankLabel11 = new JLabel();
		JLabel blankLabel12 = new JLabel();
		JLabel blankLabel14 = new JLabel();
		JLabel blankLabel15 = new JLabel();
		JLabel blankLabel21 = new JLabel();
		JLabel blankLabel22 = new JLabel();
		JLabel blankLabel24 = new JLabel();
		JLabel blankLabel25 = new JLabel();
		blankLabel11.setText("");
		blankLabel12.setText("");
		blankLabel14.setText("");
		blankLabel15.setText("");
		blankLabel21.setText("");
		blankLabel22.setText("");
		blankLabel24.setText("");
		blankLabel25.setText("");
		labelPanel.add(blankLabel11);
		labelPanel.add(blankLabel12);
		labelPanel.add(this.calHeading);
		labelPanel.add(blankLabel14);
		labelPanel.add(blankLabel15);
		labelPanel.add(blankLabel21);
		labelPanel.add(blankLabel22);
		labelPanel.add(this.monthHeading);
		labelPanel.add(blankLabel24);
		labelPanel.add(blankLabel25);
		return labelPanel;
	}

	private String getField(int day, int i, int monthKey, boolean isLeapYear) {
		int textKey = day + i;
		int month = monthKey + 1;
		if(textKey < 29){
			System.out.println("Time: " + i + " operation: 1");	
			return String.valueOf(textKey + "/" + month);
		}
		else if(textKey == 29 || textKey == 30){
			System.out.println("Time: " + i + " operation: 2");
			if(monthKey == 1){
				int monthTwo = month++;
				return(textKey == 29 && isLeapYear) ? "29"  + "/" + month : "1"  + "/" + monthTwo;
			}else return String.valueOf(textKey + "/" + month);
		}else if(textKey == 31){
			System.out.println("Time: " + i + " operation: 3");
			return "1"  + "/" + String.valueOf(month + 1);
		}else{
			System.out.println("Time: " + i + " operation: 4");
			int exitKey = 0;
			if(monthKey > 9){
				if(day > 9){
					exitKey = (textKey * 100000) + (day * 1000) + (i * 100) + monthKey;
				}else{
					exitKey = (textKey * 10000) + (day * 1000) + (i * 100) + monthKey;
				}
				
			}else{
				if(day > 9){
					exitKey = (textKey * 10000) + (day * 100) + (i * 10) + monthKey;
				}else{
					exitKey = (textKey * 1000) + (day * 100) + (i * 10) + monthKey;
				}
			}
			System.out.println(exitKey);
			System.exit(exitKey);
		} 
		return "1"  + "/" + monthKey + 1;
	}

	private String switchMonth(int monthKey) {
		switch(monthKey){
		case 0: return "January";
		case 1: return "February";
		case 2: return "March";
		case 3: return "April";
		case 4: return "May";
		case 5: return "June";
		case 6: return "July";
		case 7: return "August";
		case 8: return "September";
		case 9: return "October";
		case 10: return "November";
		case 11: return "December";
		default: return "";
		}
			
	}

	@Override
	public GUISetting[] getSettings() {
		GUISetting setting = new GUISetting("One colour", getName());
		return new GUISetting[]{setting};
	}

	@Override
	public void setColour(Color c) {
		calHeading.setForeground(c);
		monthHeading.setForeground(c);
		table.setForeground(c);
	}

	@Override
	public Color getCurrentColour() {
		return calHeading.getForeground();
	}

	public Color getBackgroundColour(){
		return calHeading.getBackground();
	}
	
	public void setBackgroundColor(Color c){
		calHeading.setBackground(c);
		monthHeading.setBackground(c);
		table.setBackground(c);
	}

	@Override
	public String getDescription(){
		return "A built-in calendar. On the right-hand side, type the date, event name and event description. Then click add event to add your event. If your event is within the next week, you can pull down the divider to see your event.";
	}
	
}
