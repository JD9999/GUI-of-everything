package Window;

import javax.swing.JButton;

public interface SmallLinkWindow extends GUIFrame{
	
	public default String getButtonText(){
		return "";
	}

	public JButton getButton();
	
}
