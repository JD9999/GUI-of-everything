package Window;

import java.awt.Color;

import javax.swing.JInternalFrame;

public interface GUIFrame {

	public Color getCurrentColour();
	public JInternalFrame getInsideFrame();
	public void setColour(Color c);
	public String getName();
	public String getDescription();
	
}
