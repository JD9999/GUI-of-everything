package Window;

import Converter.ListConverter;
import Entry.Entry;
import Note.Note;
import Settings.GUISetting;
import Settings.SettingsLoader;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
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
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class NoteWindow implements Window{
	public List<JButton> topics = new ArrayList<>();
	public Map<String, Note> notes = new LinkedHashMap<>();
	private final JTextArea area = new JTextArea();
	protected String currentHeading;

	public Color getCurrentColour(){
		return this.area.getForeground();
	}

	public JInternalFrame getInsideFrame(){
		this.topics.clear();
		JInternalFrame intFrame = new JInternalFrame();
		JSplitPane pane = new JSplitPane(0);
		JMenuBar menuBar = new JMenuBar();
		System.out.println("Starting block!");
		try{
			ensureFile();
			List<String> lines = Files.readAllLines(new File("notes.txt").toPath());
			for (String fileLine : lines) {
				System.out.println(fileLine);
			}
			Note note = new Note();
			for (String line : lines) {
				if ((lines.get(0)).equals(line) && (!line.startsWith(Entry.TAB))){
					note.setHeading(line);
				}else if (line.startsWith(Entry.TAB)){
					note.addLine(line);
				}else{
					saveNote(note);
					note = new Note();
					note.setHeading(line);
				}
			}
			saveNote(note);
			for (JButton b : this.topics){
				System.out.println("Topic: " + b.getText());
				menuBar.add(b);
			}
			menuBar.add(getButton(), "East");
			pane.setLeftComponent(menuBar);
			pane.setRightComponent(this.area);
			this.area.setVisible(true);
			menuBar.setVisible(true);
			intFrame.add(pane);
		}catch (IOException e){
			ErrorWindow.forException(e);
		}
		return intFrame;
	}

	private void saveNote(Note note){
		String heading = note.getHeading();
		System.out.println("-------------------------");
		System.out.println("Note heading: " + heading);
		for (String noteLine : note.getLines()) {
			System.out.println("Note line: " + noteLine);
		}
		System.out.println("-------------------------");
		this.notes.put(note.getHeading(), note);
		JButton button = new JButton(heading);
		button.addActionListener(getNoteListener(heading));
		this.topics.add(button);
	}

	private JButton getButton() throws IOException{
		BufferedImage image = ImageIO.read(new File("save-icon.png"));
		int type = image.getType() == 0 ? 2 : image.getType();
		image = Entry.resizeImage(image, type, 25, 25);
		ImageIcon icon = new ImageIcon(image);
		JButton button = new JButton(icon);
		button.addActionListener(getSaveListener());
		return button;
	}

	private ActionListener getSaveListener(){
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				File file = new File("notes.txt");
				File tempFile = new File("notes-last.txt");
				try{
					List<String> lines = Files.readAllLines(file.toPath());
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
					for (String line : lines){
						writer.write(line);
						writer.newLine();
					}
					writer.close();
					Note n = (Note)NoteWindow.this.notes.get(NoteWindow.this.currentHeading);
					String[] array = NoteWindow.this.area.getText().split(System.getProperty("line.separator"));
					List<String> ls = toList(array);
					n.setLines(ls);
					List<String> allNotes = getAllNotesAsList();
					writer = new BufferedWriter(new FileWriter(file));
					for (String line : allNotes){
						writer.write(line);
						writer.newLine();
					}
					writer.close();
				}catch (IOException e){
					e.printStackTrace();
					ErrorWindow.forException(e);
				}
			}

			private List<String> getAllNotesAsList(){
				List<String> ls = new ArrayList<>();
				for(Note n : notes.values()){
					ls.add(n.getHeading());
					ls.addAll(n.getLines());
				}
				return ls;
			}

			private List<String> toList(String[] array){
				List<String> ls = new ArrayList<>();
				for(String s : array) ls.add(s);
				return ls;
			}
		};
	}

	private ActionListener getNoteListener(final String heading){
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				NoteWindow.this.currentHeading = heading;
				Note n = (Note)NoteWindow.this.notes.get(heading);
				ListConverter<String> conv = new ListConverter<>(n.getLines());
				NoteWindow.this.area.setText(conv.toLinedString());
			}
		};
	}

	private void ensureFile() throws IOException{
		File file = new File("notes.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		if (file.length() <= 0L) {
			Files.write(file.toPath(), "Heading".getBytes(), getWriteOptions());
		}
	}

	private OpenOption[] getWriteOptions(){
		return new OpenOption[] { StandardOpenOption.WRITE };
	}

	public String getName(){
		return "Notes";
	}

	public GUISetting[] getSettings(){
		GUISetting textColour = new GUISetting("text-colour", getName());
		return new GUISetting[] { textColour };
	}

	public void setColour(Color c){
		List<GUISetting> settings = SettingsLoader.getSettings();
		for (GUISetting setting : settings) {
			if (setting.getText().equals("text-colour")) {
				if (setting.getValue()) {
					this.area.setForeground(c);
				} else {
					this.area.setBackground(c);
				}
			}
		}
		for(JButton b : topics){
			b.setForeground(c);
		}
	}

	public String getDescription(){
		return "A note window. This is managed by the notes.txt file. To add a note, you will need to put a heading on a new line at the end of a file manually. You can click on each heading and edit the notes. You can then press the save button to save the note.";
	}
}
