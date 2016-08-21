package Window;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
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

import Entry.Entry;
import Settings.GUISetting;
import Settings.SettingsLoader;

public class SettingsWindow implements SmallLinkWindow{

	private Color colour;
	
	@Override
	public Color getCurrentColour() {
		return colour;
	}

	@Override
	public JInternalFrame getInsideFrame() {
		JInternalFrame frame = new JInternalFrame();
		JPanel overseer = new JPanel();
		GridLayout layout = new GridLayout(0,1);
		overseer.setLayout(layout);
		List<GUISetting> settings = SettingsLoader.getSettings();
		HashSet<String> headings = new HashSet<String>();
		HashSet<String> options = new HashSet<String>();
		Map<String, String> settingEntry = new HashMap<String, String>();
		HashSet<String> falseSettings = new HashSet<String>();
		
		for(int i = 0; i < settings.size(); i++){
			GUISetting setting = settings.get(i);
			String heading = setting.getHeading();
			String text = setting.getText();
			headings.add(heading);
			options.add(text);
			settingEntry.put(text, heading);
			if(!setting.getValue()) falseSettings.add(text);
		}
		Iterator<String> iheadings = headings.iterator();
		for(int i = 0; i < headings.size(); i++){
			String heading = iheadings.next();
			JPanel panel = new JPanel();
			JLabel label = new JLabel();
			Font f = new Font("Arial", Font.BOLD, 14);
			label.setFont(f);
			label.setText(heading);
			overseer.add(label);
			System.out.println("Heading " + heading + " registered! Looking for children.");
			Iterator<String> ioptions = options.iterator();
			for(int e = 0; e < options.size(); e++){
				String text = ioptions.next();
				if(settingEntry.get(text).equals(heading)){
					System.out.println("Setting registered! Name:" + text + " Heading:" + heading);
					JCheckBox box = new JCheckBox();
					if(falseSettings.contains(text)) box.setSelected(false);
					else box.setSelected(true);
					box.addActionListener(getActionListener(box, text));
					JLabel sublabel = new JLabel();
					sublabel.setText(text);
					Font font = new Font("Arial", Font.PLAIN, 10);
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

	private ActionListener getActionListener(JCheckBox box, String text) {
		return new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SettingsLoader.reverseSetting(text);
			}
			
		};
	}

	@Override
	public JButton getButton() {
		JButton button = null;
		try {
			BufferedImage image = ImageIO.read(new File("cog.png"));
			int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
			image = Entry.resizeImage(image, type, 50, 50);
			button = new JButton(new ImageIcon(image, "Settings"));
		} catch (IOException e) {
			ErrorWindow.forException(e);
		}
		return button;
	}

	@Override
	public void setColour(Color c) {
		colour = c;
	}

	@Override
	public String getName() {
		return "settings";
	}

}
