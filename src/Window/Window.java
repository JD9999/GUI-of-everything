package Window;

import java.awt.Color;

import Settings.GUISetting;

public interface Window extends GUIFrame{
	
	public String getName();
	
	public GUISetting[] getSettings();
	
	public void setColour(Color c);
	
}
