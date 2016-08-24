package Window;

import Entry.Entry;

import Settings.GUISetting;
import Settings.SettingsLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsWindow implements SmallLinkWindow{
	private Color colour;

	public Color getCurrentColour(){
		return colour;
	}

	public JInternalFrame getInsideFrame(){
		JInternalFrame frame = new JInternalFrame();
		JPanel overseer = new JPanel();
		GridLayout layout = new GridLayout(0, 1);
		overseer.setLayout(layout);
		List<GUISetting> settings = SettingsLoader.getSettings();
		HashSet<String> headings = new HashSet<>();
		HashSet<String> options = new HashSet<>();
		Map<String, String> settingEntry = new HashMap<>();
		HashSet<String> falseSettings = new HashSet<>();
		for (int i = 0; i < settings.size(); i++){
			GUISetting setting = (GUISetting)settings.get(i);
			String heading = setting.getHeading();
			String text = setting.getText();
			headings.add(heading);
			options.add(text);
			settingEntry.put(text, heading);
			if (!setting.getValue()) {
				falseSettings.add(text);
			}
		}
		Iterator<String> iheadings = headings.iterator();
		for (int i = 0; i < headings.size(); i++){
			String heading = (String)iheadings.next();
			JPanel panel = new JPanel();
			JLabel label = new JLabel();
			Font f = new Font("Arial", 1, 14);
			label.setFont(f);
			label.setText(heading);
			overseer.add(label);
			System.out.println("Heading " + heading + " registered! Looking for children.");
			Iterator<String> ioptions = options.iterator();
			for (int e = 0; e < options.size(); e++){
				String text = (String)ioptions.next();
				if (settingEntry.get(text).equals(heading)){
					System.out.println("Setting registered! Name:" + text + " Heading:" + heading);
					JCheckBox box = new JCheckBox();
					if (falseSettings.contains(text)) {
						box.setSelected(false);
					} else {
						box.setSelected(true);
					}
					box.addActionListener(getActionListener(box, text));
					JLabel sublabel = new JLabel();
					sublabel.setText(text);
					Font font = new Font("Arial", 0, 10);
					sublabel.setFont(font);
					panel.add(sublabel);
					panel.add(box);
					overseer.add(panel);
				}
			}
		}
		frame.getContentPane().add(overseer);
		return frame;
	}

	private ActionListener getActionListener(JCheckBox box, final String text){
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				SettingsLoader.reverseSetting(text);
			}
		};
	}

	public JButton getButton(){
		JButton button = null;
		try{
			BufferedImage image = ImageIO.read(new File("cog.png"));
			int type = image.getType() == 0 ? 2 : image.getType();
			image = Entry.resizeImage(image, type, 50, 50);
			button = new JButton(new ImageIcon(image, "Settings"));
		}catch (IOException e){
			ErrorWindow.forException(e);
		}
		return button;
	}

	public void setColour(Color c){
		colour = c;
	}

	public String getName(){
		return "settings";
	}

	public String getDescription(){
		return "Settings. You can click the box to change the retrospective setting.";
	}
}
