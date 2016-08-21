package Settings;

import Converter.ListConverter;
import Converter.SetConverter;
import Entry.Entry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Window.ErrorWindow;
import Window.Window;
import Window.WindowEntry;

public class SettingsLoader {

	private static List<GUISetting> settings = new ArrayList<GUISetting>();

	public static void obtainSettings(){
		Set<GUISetting> s = new HashSet<GUISetting>();
		Window[] windows = WindowEntry.getAllWindows();
		for(int i = 0; i < windows.length; i++){
			Window w = windows[i];
			GUISetting[] array = w.getSettings();
			if(array!=null)
				for(GUISetting setting : array) s.add(setting);
		}
		for(GUISetting setting: s){
			try {
				List<String> lines = Files.readAllLines(Entry.file.toPath());
				for(String line: lines){
					if(line.startsWith("//")) continue;
					if(line.contains(setting.getText())){
						String value = line.substring(line.indexOf('=') + 1, line.length());
						if(value.equalsIgnoreCase("false")) setting.setValue(false);
						else setting.setValue(true);
					}
				}
			} catch (IOException e) {
				ErrorWindow.forException(e);
			}
			settings = new SetConverter<GUISetting>(s).toList();
		}
	}

	public static List<GUISetting> getSettings() {
		return settings;
	}

	public static void setSettings(List<GUISetting> settings) {
		SettingsLoader.settings = settings;
	}

	public static void reverseSetting(String text) {
		File f = Entry.file;
		List<String> finalLines = new ArrayList<String>();
		try {
			List<String> lineslist = Files.readAllLines(f.toPath());
			Set<String> linesset = new ListConverter<String>(lineslist).toSet();
			List<String> lines = new SetConverter<String>(linesset).toList();
			for(int i = 0; i < lines.size(); i++){
				String line = lines.get(i);
				if(line.contains(text)){
					System.out.println("Line: " + line + " contains: " + text);
					String value = line.substring(line.indexOf('=') + 1, line.length());
					if(value.equalsIgnoreCase("false")){
						System.out.println("Value is currently false. Setting to true");
						finalLines.add(text + "=true");
					}
					else if(value.equalsIgnoreCase("true")){ 
						System.out.println("Value is currently true. Setting to false");
						finalLines.add(text + "=false");
					}
					else throw new IOException("Line is not true or false!");
				}else finalLines.add(line);
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(Entry.file));
			writer.write("");
			writer.close();
			writer = new BufferedWriter(new FileWriter(Entry.file, true));
			for(int i = 0; i < finalLines.size(); i++){
				String line = finalLines.get(i);	
				writer.write(line);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			ErrorWindow.forException(e);
		}
		for(int i = 0; i < settings.size(); i++){
			GUISetting setting = settings.get(i);
			if(setting.getText().equals(text)){
				System.out.println("Found setting. Reversing!");
				if(setting.getValue() == true) setting.setValue(false);
				else setting.setValue(true);
			}
		}
	}

}
