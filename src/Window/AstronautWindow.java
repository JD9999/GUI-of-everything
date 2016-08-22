package Window;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.SwingConstants;

import Date.Astronomical;
import Settings.GUISetting;

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
	
	@Override
	public String getName() {
		return "Astronaut";
	}

	@Override
	public JInternalFrame getInsideFrame() {
		JInternalFrame frame = new JInternalFrame();
	    frame.getContentPane().add(getSplitPane());
		return frame;
	}

	private Component getSplitPane() {
		JSplitPane pane = new JSplitPane();
		pane.setDividerLocation(400);
		JPanel panel = new JPanel();
		JPanel hourPanel = new JPanel();
		JPanel dayPanel = new JPanel();
		JPanel weekPanel = new JPanel();
		JPanel monthPanel = new JPanel();
		panel.setSize(400, 925);
		panel.setLayout(new GridLayout(0, 1));
		JButton hour = new JButton(), day = new JButton(), week = new JButton(), month = new JButton();
		JTextField fieldH = new JTextField(), fieldD = new JTextField(), fieldW = new JTextField(), fieldM = new JTextField();
		fieldH.setText("0");
		fieldD.setText("0");
		fieldW.setText("0");
		fieldM.setText("0");
		
		hourField = fieldH;
		dayField = fieldD;
		weekField = fieldW;
		monthField = fieldM;
		
		hourButton = hour;
		dayButton = day;
		weekButton = week;
		monthButton = month;
		
		hourButton.setSize(300, 200);
		hourButton.addActionListener(getActionListener(hourField));
		hourButton.setText("Increment hour");
		hourPanel.setLayout(new GridLayout(1,0));
		hourPanel.add(hourButton);
		hourPanel.add(hourField);
		
		dayButton.setSize(300, 200);
		dayButton.addActionListener(getActionListener(dayField));
		dayButton.setText("Increment day");
		dayPanel.setLayout(new GridLayout(1,0));
		dayPanel.add(dayButton);
		dayPanel.add(dayField);
		
		weekButton.setSize(300, 200);
		weekButton.addActionListener(getActionListener(weekField));
		weekButton.setText("Increment week");
		weekPanel.setLayout(new GridLayout(1,0));
		weekPanel.add(weekButton);
		weekPanel.add(weekField);
		
		monthButton.setSize(300, 200);
		monthButton.addActionListener(getActionListener(monthField));
		monthButton.setText("Increment month");
		monthPanel.setLayout(new GridLayout(1,0));
		monthPanel.add(monthButton);
		monthPanel.add(monthField);
		
		panel.add(hourPanel);
		panel.add(dayPanel);
		panel.add(weekPanel);
		panel.add(monthPanel);
		pane.setLeftComponent(panel);
		
		JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(2,1));
		JPanel bottom = new JPanel();
		JLabel label = new JLabel();
		questionLabel = label;
		questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		questionLabel.setVerticalAlignment(SwingConstants.CENTER);
		questionLabel.setText("How long is the astronaut behind us?");
		JButton button = new JButton();
		answerField = new JTextArea();
		button.addActionListener(getActionListener(answerField, hourField, dayField, weekField, monthField));
		button.setText("Work it out!");
		answerButton = button;
		top.add(questionLabel);
		top.add(answerButton);
		bottom.add(answerField);
		rightPane.setLeftComponent(top);
		rightPane.setRightComponent(bottom);
		pane.setRightComponent(rightPane);	
			
		return pane;
	}

	private ActionListener getActionListener(JTextArea answer, JTextField fieldH,
			JTextField fieldD, JTextField fieldW, JTextField fieldM) {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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

	private ActionListener getActionListener(JTextField field) {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = field.getText();
				int n = Integer.parseInt(text);
				n++;
				text = String.valueOf(n);
				field.setText(text);
				
			}
			
		};
	}

	@Override
	public GUISetting[] getSettings() {
		return null;
	}

	@Override
	public void setColour(Color c) {
		hourField.setForeground(c);
		dayField.setForeground(c);
		weekField.setForeground(c);
		monthField.setForeground(c);
		hourButton.setForeground(c);
		dayButton.setForeground(c);
		weekButton.setForeground(c);
		monthButton.setForeground(c);
		questionLabel.setForeground(c);
		answerButton.setForeground(c);
		answerField.setForeground(c);
	}

	@Override
	public Color getCurrentColour() {
		return hourField.getForeground();
	}

}
