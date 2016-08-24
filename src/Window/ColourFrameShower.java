package Window;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

import Entry.ColourFrame;
import Entry.Entry;

public class ColourFrameShower implements SmallLinkWindow {

	private final ColourFrame frame = new ColourFrame();
	private final JButton button = new JButton(getIcon());
	
	private Icon getIcon() {
		try {
			BufferedImage image = ImageIO.read(new File("ColourIcon.png"));
			int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
			image = Entry.resizeImage(image, type, 25, 25);
			ImageIcon icon = new ImageIcon(image);
			return icon;
		} catch (IOException e) {
			ErrorWindow.forException(e);
		}
		return null;
	}

	@Override
	public Color getCurrentColour() {
		return frame.getColour();
	}

	@Override
	public JInternalFrame getInsideFrame() {
		frame.run();
		return Entry.currentFrame;
	}

	@Override
	public void setColour(Color c) {
		button.setForeground(c);
	}

	@Override
	public String getName() {
		return "colour chooser";
	}

	@Override
	public JButton getButton() {
		return button;
	}

	public String getDescription() {
   		return Entry.currentWindow.getDescription();
 	}

}
