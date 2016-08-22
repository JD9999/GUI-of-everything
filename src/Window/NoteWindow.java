package Window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Converter.ListConverter;
import Entry.Entry;
import Note.Note;
import Settings.GUISetting;
import Settings.SettingsLoader;

public class NoteWindow implements Window {
	
	public List<JButton> topics = new ArrayList<JButton>();
	public Map<String, Note> notes = new LinkedHashMap<String, Note>();
	private final JTextArea area = new JTextArea();

	@Override
	public Color getCurrentColour() {
		return area.getForeground();
	}

	@Override
	public JInternalFrame getInsideFrame() {
		topics.clear();
		JInternalFrame intFrame = new JInternalFrame();
		JPanel frame = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		System.out.println("Starting block!");
		try {
			ensureFile();
			List<String> lines = Files.readAllLines(new File("notes.txt").toPath());
			for(String fileLine : lines){
				System.out.println(fileLine);
			}
			Note note = new Note();
			for(String line : lines){
				if(lines.get(0).equals(line) && !line.startsWith(Entry.TAB)) note.setHeading(line);
				else{
					if(line.startsWith(Entry.TAB)) note.addLine(line);
					else{
						saveNote(note);
						note = new Note();
						note.setHeading(line);
					}
				}
			}
			saveNote(note);
			for(JButton b : topics){
				System.out.println("Topic: " + b.getText());
				menuBar.add(b);
			}
			menuBar.add(getButton(), BorderLayout.EAST);
			frame.add(menuBar, BorderLayout.NORTH);
			frame.add(area, BorderLayout.CENTER);
			area.setVisible(true);
			menuBar.setVisible(true);
			intFrame.add(frame);
		} catch (IOException e) {
			ErrorWindow.forException(e);
		}
		return intFrame;
	}

	private void saveNote(Note note) {
		String heading = note.getHeading();
		System.out.println("-------------------------");
		System.out.println("Note heading: " + heading);
		for(String noteLine : note.getLines()){
			System.out.println("Note line: " + noteLine);
		}
		System.out.println("-------------------------");
		notes.put(note.getHeading(), note);
		JButton button = new JButton(heading);
		button.addActionListener(getNoteListener(heading));
		topics.add(button);
	}

	private JButton getButton() throws IOException {
		BufferedImage image = ImageIO.read(new File("save-icon.png"));
		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		image = Entry.resizeImage(image, type, 25, 25);
		ImageIcon icon = new ImageIcon(image);
		JButton button = new JButton(icon);
		button.addActionListener(getSaveListener());
		return button;
	}

	private ActionListener getSaveListener() {
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				File file = new File("notes.txt");
				File tempFile = new File("notes-last.txt");
				String text = area.getText();
				try {
					Files.copy(file.toPath(), tempFile.toPath(), getCopyOptions());
					Files.write(file.toPath(), text.getBytes(), getOptions());
				} catch (IOException e) {
					e.printStackTrace();
					ErrorWindow.forException(e);
				}
			}
			
		};
	}

	protected CopyOption[] getCopyOptions() {
		return new CopyOption[]{
			StandardCopyOption.REPLACE_EXISTING
		};
	}

	private ActionListener getNoteListener(String heading) {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				Note n = notes.get(heading);
				ListConverter<String> conv = new ListConverter<>(n.getLines());
				area.setText(conv.toLinedString());
			}
			};
	}

	private void ensureFile() throws IOException {
		File file = new File("notes.txt");
		if(!file.exists()) file.createNewFile();
		if(!(file.length() > 0)) Files.write(file.toPath(), "Heading".getBytes(), getOptions());
	}

	private OpenOption[] getOptions() {
		return new OpenOption[]{StandardOpenOption.WRITE};
	}

	@Override
	public String getName() {
		return "Notes";
	}

	@Override
	public GUISetting[] getSettings() {
		GUISetting textColour = new GUISetting("text-colour", getName());
		GUISetting noteSplit = new GUISetting("split-notes-supported", getName());
		GUISetting horizontalLines = new GUISetting("horizontal-lines", getName());
		GUISetting verticalLines = new GUISetting("vertical-lines", getName());
		return new GUISetting[]{textColour, noteSplit, horizontalLines, verticalLines};
	}

	@Override
	public void setColour(Color c) {
		List<GUISetting> settings = SettingsLoader.getSettings();
		System.out.println(settings.size());
		for(GUISetting setting : settings){
			System.out.println("Setting: " + setting);
			if(setting.getText().equals("text-colour")){
				if(setting.getValue()){
					System.out.println("Setting text colour");
					area.setForeground(c);
				}
				else{
					System.out.println("Setting background colour");
					area.setBackground(c);
				}
			}
		}
	}

}
