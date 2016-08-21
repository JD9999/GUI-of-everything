package Window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Math.Pythagoras;
import Math.Trig;
import Math.TrigValue;
import Math.TrigonometryException;
import Settings.GUISetting;
import Settings.SettingsLoader;

public class MathWindow implements Window {

	protected static JTextField hypField;
	protected static JTextField adjSmall2Field;
	protected static JTextField oppSmall1Field;
	protected static JTextArea answerField;
	protected static JLabel hypLabel;
	protected static JLabel adjSmall2Label;
	protected static JLabel oppSmall1Label;
	protected static JTextField angField;
	protected static JLabel angLabel;
	private JSplitPane splitPane;
	
	@Override
	public String getName() {
		return "Maths";
	}

	@Override
	public JInternalFrame getInsideFrame() {
		JInternalFrame frame = new JInternalFrame();
		JSplitPane pane = new JSplitPane();
		pane.setDividerLocation(300);	
		JPanel panel = new JPanel();
		panel.setSize(300, 925);
		JButton pyth = new JButton();
		JButton trig = new JButton();
		pyth.setText("Pythagoars theorem");
		trig.setText("Trigonometry");
		pyth.setSize(300, 200);
		trig.setSize(300, 200);
		pyth.addActionListener(getActionListenerForPythagoras());
		trig.addActionListener(getActionListenerForTrignomotry());
		panel.setLayout(new GridLayout(0,1));
		panel.add(pyth);
		panel.add(trig);
		pane.setLeftComponent(panel);
		splitPane = pane;
		frame.getContentPane().add(splitPane);
		return frame;
	}

	private ActionListener getActionListenerForPythagoras() {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel overseePanel = new JPanel();
				JTextField hypField = new JTextField();
				JTextField aField = new JTextField();
				JTextField bField = new JTextField();
				JLabel hypLabel = new JLabel();
				JLabel aLabel = new JLabel();
				JLabel bLabel = new JLabel();
				JButton button = new JButton();
				JTextArea field = new JTextArea();
				hypLabel.setText("Hypotenuse");
				aLabel.setText("Small side 1");
				bLabel.setText("Small side 2");
				hypLabel.setSize(400, hypLabel.getHeight());
				aLabel.setSize(400, aLabel.getHeight());
				bLabel.setSize(400, bLabel.getHeight());
				hypField.setText("0");
				aField.setText("0");
				bField.setText("0");
				hypField.setSize(400, hypLabel.getHeight());
				aField.setSize(400, aLabel.getHeight());
				bField.setSize(400, bLabel.getHeight());
				button.setText("Work it out!");
				
				MathWindow.hypField = hypField;
				MathWindow.oppSmall1Field = aField;
				MathWindow.adjSmall2Field = bField;
				MathWindow.answerField = field;
				MathWindow.hypLabel = hypLabel;
				MathWindow.oppSmall1Label = aLabel;
				MathWindow.adjSmall2Label = bLabel;
				
				button.addActionListener(getActionListenerForPythagorasFinal(MathWindow.hypField, MathWindow.oppSmall1Field, MathWindow.adjSmall2Field, MathWindow.answerField));
				overseePanel.setLayout(new GridLayout(0,1));
				overseePanel.add(MathWindow.hypLabel, BorderLayout.CENTER);
				overseePanel.add(MathWindow.hypField, BorderLayout.CENTER);
				overseePanel.add(MathWindow.oppSmall1Label, BorderLayout.CENTER);
				overseePanel.add(MathWindow.oppSmall1Field, BorderLayout.CENTER);
				overseePanel.add(MathWindow.adjSmall2Label, BorderLayout.CENTER);
				overseePanel.add(MathWindow.adjSmall2Field, BorderLayout.CENTER);
				overseePanel.add(button);
				overseePanel.add(MathWindow.answerField);
				splitPane.setRightComponent(overseePanel);
			}
		};
	}
	
	protected ActionListener getActionListenerForPythagorasFinal(JTextField hypo, JTextField ss1, JTextField ss2, JTextArea answerField2) {
		return new ActionListener(){

			private Pythagoras p = new Pythagoras();
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String hyp = hypo.getText();
				String s1 = ss1.getText();
				String s2 = ss2.getText();
				if(hyp.equals("0")){
					double a = Double.parseDouble(s1);
					double b = Double.parseDouble(s2);
					if(a == 3 && b == 4 || a == 4 && b == 3) System.out.println("The result should be 5!");
					double hypAnswer = p.getHypotenuse(a, b);
					printWorkingIfSettingIsTrue(parseThroughSetting(hypAnswer), p.getWorking());
				}else if(s1.equals("0")){
					double c = Double.parseDouble(hyp);
					double b = Double.parseDouble(s2);
					double a = p.getShorterSide(b, c);
					printWorkingIfSettingIsTrue(parseThroughSetting(a), p.getWorking());
				}else if(s2.equals("0")){
					double c = Double.parseDouble(hyp);
					double a = Double.parseDouble(s1);
					double b = p.getShorterSide(a, c);
					printWorkingIfSettingIsTrue(parseThroughSetting(b), p.getWorking());
				}else throw new IllegalArgumentException("All of the fields have stuff in them!");
				
			}
		};
	}

	protected String parseThroughSetting(double d) {
		List<GUISetting> settings = SettingsLoader.getSettings();
		for(GUISetting setting : settings){
			if(setting.getText().equals("Round")){
				return setting.getValue() ? String.valueOf(d) : String.valueOf(Math.round(d));
			}
		}
		System.out.println("Unable to found Round setting in Maths heading. Does this exist?");
		return String.valueOf(d);
	}
	
	protected boolean printWorkingIfSettingIsTrue(String line, List<String> working){
		List<GUISetting> settings = SettingsLoader.getSettings();
		for(GUISetting setting : settings){
			if(setting.getText().equals("Show working")){
				answerField.setText("");
				if(setting.getValue()){
					for(String workingLine : working){
						answerField.append(workingLine);
						answerField.append("\n");
					}
					return true;
				}else{
					answerField.append(working.get(working.size() - 1));
					return false;
				}
			}
		}
		System.out.println("Unable to found Show working setting in Maths heading. Does this exist?");
		return false;
	}

	private ActionListener getActionListenerForTrignomotry(){
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel overseePanel = new JPanel();		
				JTextField hypField = new JTextField();
				JTextField aField = new JTextField();
				JTextField bField = new JTextField();
				JTextField anField = new JTextField();
				JLabel hypLabel = new JLabel();
				JLabel aLabel = new JLabel();
				JLabel bLabel = new JLabel();
				JLabel anLabel = new JLabel();
				JButton button = new JButton();
				JTextArea field = new JTextArea();
				hypLabel.setText("Hypotenuse");
				aLabel.setText("Opposite");
				bLabel.setText("Adjacent");
				anLabel.setText("Angle size");
				hypLabel.setSize(400, hypLabel.getHeight());
				aLabel.setSize(400, aLabel.getHeight());
				bLabel.setSize(400, bLabel.getHeight());
				anLabel.setSize(400, anLabel.getHeight());
				hypField.setText("0");
				aField.setText("0");
				bField.setText("0");
				anField.setText("0");
				hypField.setSize(400, hypLabel.getHeight());
				aField.setSize(400, aLabel.getHeight());
				bField.setSize(400, bLabel.getHeight());
				anField.setSize(400, anLabel.getHeight());
				button.setText("Work it out!");
				
				MathWindow.hypField = hypField;
				MathWindow.oppSmall1Field = aField;
				MathWindow.adjSmall2Field = bField;
				MathWindow.answerField = field;
				MathWindow.angField = anField;
				MathWindow.hypLabel = hypLabel;
				MathWindow.oppSmall1Label = aLabel;
				MathWindow.adjSmall2Label = bLabel;
				MathWindow.angLabel = anLabel;
				
				button.addActionListener(getActionListenerForTrigonomotryFinal(MathWindow.hypField, MathWindow.oppSmall1Field, MathWindow.adjSmall2Field, MathWindow.angField, MathWindow.answerField));
				overseePanel.setLayout(new GridLayout(0,1));
				overseePanel.add(MathWindow.hypLabel, BorderLayout.CENTER);
				overseePanel.add(MathWindow.hypField, BorderLayout.CENTER);
				overseePanel.add(MathWindow.oppSmall1Label, BorderLayout.CENTER);
				overseePanel.add(MathWindow.oppSmall1Field, BorderLayout.CENTER);
				overseePanel.add(MathWindow.adjSmall2Label, BorderLayout.CENTER);
				overseePanel.add(MathWindow.adjSmall2Field, BorderLayout.CENTER);
				overseePanel.add(MathWindow.angLabel, BorderLayout.CENTER);
				overseePanel.add(MathWindow.angField, BorderLayout.CENTER);
				overseePanel.add(button);
				overseePanel.add(MathWindow.answerField);
				splitPane.setRightComponent(overseePanel);
			}
			
		};
	}
    //a == opposite, b == adjacent
	protected ActionListener getActionListenerForTrigonomotryFinal(JTextField hypo,
			JTextField aa, JTextField bb, JTextField field, JTextArea anField) {
			return new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent event) {
					String hyp = hypo.getText();
					String a = aa.getText();
					String b = bb.getText();
					String an = anField.getText();
					Trig trigonometry = new Trig();
					if(an == "0"){
						
						if(hyp == "0"){
							int shorta = Integer.parseInt(a);
							int shortb = Integer.parseInt(b);
							try {
								TrigValue tA = new TrigValue(TrigValue.OPPOSITE, shorta);
								TrigValue tB = new TrigValue(TrigValue.ADJACENT, shortb);
								field.setText(String.valueOf(trigonometry.getAngleSize(tA, tB)) + " (tangent)");
							} catch (TrigonometryException e) {
								ErrorWindow.forException(e);
							}
						}else if(a == "0"){
							int hypotenuse = Integer.parseInt(hyp);
							int shortb = Integer.parseInt(b);
							try {
								TrigValue tH = new TrigValue(TrigValue.HYPOTENUSE, hypotenuse);
								TrigValue tB = new TrigValue(TrigValue.ADJACENT, shortb);
								field.setText(String.valueOf(trigonometry.getAngleSize(tH, tB)) + " (cosine)");
							} catch (TrigonometryException e) {
								ErrorWindow.forException(e);
							}
						}else if(b == "0"){
							int hypotenuse = Integer.parseInt(hyp);
							int shorta = Integer.parseInt(a);
							try {
								TrigValue tA = new TrigValue(TrigValue.OPPOSITE, shorta);
								TrigValue tH = new TrigValue(TrigValue.HYPOTENUSE, hypotenuse);
								field.setText(String.valueOf(trigonometry.getAngleSize(tA, tH)) + " (sine)");
							} catch (TrigonometryException e) {
								ErrorWindow.forException(e);
							}
						}
						
						
					}else{
						int angle = Integer.parseInt(an);
						if(angle >= 90) throw new IllegalArgumentException("Angle is bigger than 90");
						if(hyp.equals("0")){
							if(a.equals("?")){
								int shortb = Integer.parseInt(b);
								try {
									TrigValue tB = new TrigValue(TrigValue.ADJACENT, shortb);
									TrigValue tA = new TrigValue(TrigValue.OPPOSITE);
									field.setText(String.valueOf(trigonometry.getSideLength(tB, angle, tA)) + " (tangent)");
								} catch (TrigonometryException e) {
									ErrorWindow.forException(e);
								}
							}else if(b.equals("?")){
								int shorta = Integer.parseInt(a);
								try {
									TrigValue tB = new TrigValue(TrigValue.ADJACENT);
									TrigValue tA = new TrigValue(TrigValue.OPPOSITE, shorta);
									field.setText(String.valueOf(trigonometry.getSideLength(tA, angle, tB)) + " (tangent)");
								} catch (TrigonometryException e) {
									ErrorWindow.forException(e);
								}
							}else throw new IllegalArgumentException("We already know what we want to know.");
						}else if(a.equals("0")){
							if(hyp.equals("?")){
								int shortb = Integer.parseInt(b);
								try {
									TrigValue tB = new TrigValue(TrigValue.ADJACENT, shortb);
									TrigValue tH = new TrigValue(TrigValue.HYPOTENUSE);
									field.setText(String.valueOf(trigonometry.getSideLength(tB, angle, tH)) + " (cosine)");
								} catch (TrigonometryException e) {
									ErrorWindow.forException(e);
								}
							}else if(b.equals("?")){
								int h = Integer.parseInt(hyp);
								try {
									TrigValue tB = new TrigValue(TrigValue.ADJACENT);
									TrigValue tH = new TrigValue(TrigValue.HYPOTENUSE, h);
									field.setText(String.valueOf(trigonometry.getSideLength(tH, angle, tB)) + " (cosine)");
								} catch (TrigonometryException e) {
									ErrorWindow.forException(e);
								}
							}else throw new IllegalArgumentException("We already know what we want to know.");
							
						}else if(b.equals("0")){
							if(hyp.equals("?")){
								int shorta = Integer.parseInt(a);
								try {
									TrigValue tA = new TrigValue(TrigValue.OPPOSITE, shorta);
									TrigValue tH = new TrigValue(TrigValue.HYPOTENUSE);
									field.setText(String.valueOf(trigonometry.getSideLength(tA, angle, tH)) + " (sine)");
								} catch (TrigonometryException e) {
									ErrorWindow.forException(e);
								}
							}else if(a.equals("?")){
								int h = Integer.parseInt(hyp);
								try {
									TrigValue tA = new TrigValue(TrigValue.OPPOSITE);
									TrigValue tH = new TrigValue(TrigValue.HYPOTENUSE, h);
									field.setText(String.valueOf(trigonometry.getSideLength(tH, angle, tA)) + " (sine)");
								} catch (TrigonometryException e) {
									ErrorWindow.forException(e);
								}
							}
						}
					}
				}
				
			};
	}

	@Override
	public GUISetting[] getSettings() {
		GUISetting setting = new GUISetting("Show working", "Maths");
		GUISetting setting2 = new GUISetting("Round", "Maths");
		return new GUISetting[]{setting, setting2};
	}

	@Override
	public void setColour(Color c) {
		hypField.setForeground(c);
		adjSmall2Field.setForeground(c);
		oppSmall1Field.setForeground(c);
		answerField.setForeground(c);
		if(angField !=null)angField.setForeground(c);
		hypLabel.setForeground(c);
		adjSmall2Label.setForeground(c);
		oppSmall1Label.setForeground(c);
		if(angLabel !=null)angLabel.setForeground(c);
	}

	@Override
	public Color getCurrentColour() {
		return hypField.getForeground();
	}

}
