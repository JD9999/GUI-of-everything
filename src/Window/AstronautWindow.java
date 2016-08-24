package Window;

import Date.Astronomical;

import Entry.Entry;

import Settings.GUISetting;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AstronautWindow implements Window{
	private JTextField hourField;
	private JTextField dayField;
	private JTextField weekField;
	private JTextField monthField;
	private JButton hourButton;
	private JButton dayButton;
	private JButton weekButton;
	private JButton monthButton;
	private JLabel questionLabel;
	private JButton answerButton;
	private JTextArea answerField;
  
	public String getName(){
		return "Astronaut";
	}
  
	public JInternalFrame getInsideFrame(){
		JInternalFrame frame = new JInternalFrame();
		frame.getContentPane().add(getSplitPane());
		return frame;
	}
  
	private JSplitPane getSplitPane(){
		JSplitPane pane = new JSplitPane();
		pane.setDividerLocation(400);
		JPanel panel = new JPanel();
		JPanel hourPanel = new JPanel();
		JPanel dayPanel = new JPanel();
		JPanel weekPanel = new JPanel();
		JPanel monthPanel = new JPanel();
		panel.setSize(400, Entry.INTERNAL_FRAME_HEIGHT);
		panel.setLayout(new GridLayout(0, 1));
		JButton hour = new JButton(), day = new JButton(), week = new JButton(), month = new JButton();
		JTextField fieldH = new JTextField(),fieldD = new JTextField(), fieldW = new JTextField(), fieldM = new JTextField();
		fieldH.setText("0");
		fieldD.setText("0");
		fieldW.setText("0");
		fieldM.setText("0");
    
		this.hourField = fieldH;
		this.dayField = fieldD;
		this.weekField = fieldW;
		this.monthField = fieldM;
    
		this.hourButton = hour;
		this.dayButton = day;
		this.weekButton = week;
		this.monthButton = month;
    
		this.hourButton.setSize(300, 200);
		this.hourButton.addActionListener(getActionListener(this.hourField));
		this.hourButton.setText("Increment hour");
		hourPanel.setLayout(new GridLayout(1, 0));
		hourPanel.add(this.hourButton);
		hourPanel.add(this.hourField);
    
		this.dayButton.setSize(300, 200);
		this.dayButton.addActionListener(getActionListener(this.dayField));
		this.dayButton.setText("Increment day");
		dayPanel.setLayout(new GridLayout(1, 0));
    	dayPanel.add(this.dayButton);
    	dayPanel.add(this.dayField);
    
    	this.weekButton.setSize(300, 200);
    	this.weekButton.addActionListener(getActionListener(this.weekField));
    	this.weekButton.setText("Increment week");
    	weekPanel.setLayout(new GridLayout(1, 0));
    	weekPanel.add(this.weekButton);
    	weekPanel.add(this.weekField);
    
    	this.monthButton.setSize(300, 200);
    	this.monthButton.addActionListener(getActionListener(this.monthField));
    	this.monthButton.setText("Increment month");
    	monthPanel.setLayout(new GridLayout(1, 0));
    	monthPanel.add(this.monthButton);
    	monthPanel.add(this.monthField);
    
    	panel.add(hourPanel);
    	panel.add(dayPanel);
    	panel.add(weekPanel);
    	panel.add(monthPanel);
    	pane.setLeftComponent(panel);
    
    	JSplitPane rightPane = new JSplitPane(0);
    	JPanel top = new JPanel();
    	top.setLayout(new GridLayout(2, 1));
    	JPanel bottom = new JPanel();
    	JLabel label = new JLabel();
    	this.questionLabel = label;
    	this.questionLabel.setHorizontalAlignment(0);
    	this.questionLabel.setVerticalAlignment(0);
    	this.questionLabel.setText("How long is the astronaut behind us?");
    	JButton button = new JButton();
    	this.answerField = new JTextArea();
    	button.addActionListener(getActionListener(this.answerField, this.hourField, this.dayField, this.weekField, this.monthField));
    	button.setText("Work it out!");
    	this.answerButton = button;
    	top.add(this.questionLabel);
    	top.add(this.answerButton);
    	bottom.add(this.answerField);
    	rightPane.setLeftComponent(top);
    	rightPane.setRightComponent(bottom);
    	pane.setRightComponent(rightPane);   
    	return pane;
	}
  
	private ActionListener getActionListener(final JTextArea answer, final JTextField fieldH, final JTextField fieldD, final JTextField fieldW, final JTextField fieldM){
		return new ActionListener(){
      
			public void actionPerformed(ActionEvent e){
				String hrs = fieldH.getText();
				String dys = fieldD.getText();
				String wks = fieldW.getText();
				String mns = fieldM.getText();
				int hours = Integer.parseInt(hrs);
				int days = Integer.parseInt(dys);
				int weeks = Integer.parseInt(wks);
				int months = Integer.parseInt(mns);
				Astronomical astro = new Astronomical();
    			astro.passHours(hours);
    			astro.passDays(days);
    			astro.passWeeks(weeks);
    			astro.passMonths(months);
    			answer.setText("They are behind us by " + astro.getDifferenceInMilliseconds() + " milliseconds.");
    			fieldH.setText("0");
    			fieldD.setText("0");
    			fieldW.setText("0");
    			fieldM.setText("0");
			}
		};
	}
  
	private ActionListener getActionListener(final JTextField field){
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				String text = field.getText();
				int n = Integer.parseInt(text);
				n++;
				text = String.valueOf(n);
				field.setText(text);
			}
		};
	}
  
	public GUISetting[] getSettings(){
		return null;
	}
  
	public void setColour(Color c){
		this.hourField.setForeground(c);
		this.dayField.setForeground(c);
		this.weekField.setForeground(c);
		this.monthField.setForeground(c);
		this.hourButton.setForeground(c);
		this.dayButton.setForeground(c);
		this.weekButton.setForeground(c);
		this.monthButton.setForeground(c);
		this.questionLabel.setForeground(c);
		this.answerButton.setForeground(c);
		this.answerField.setForeground(c);
	}
  
	public Color getCurrentColour(){
		return this.hourField.getForeground();
	}
  
	public String getDescription(){
		return "This window gets the time difference between space and Earth. Press the incrementor buttons to increase the hours, day, weeks or months by 1, or type the value in the box. Then click the \"Work it out!\" button to show the time difference.";
	}
}


