package Entry;

import Settings.SettingsLoader;

import Window.ColourFrameShower;
import Window.ErrorWindow;
import Window.GUIFrame;
import Window.SmallLinkWindow;
import Window.Window;
import Window.WindowEntry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class Entry{
	public static final int INTERNAL_FRAME_WIDTH = 1400;
	public static final int INTERNAL_FRAME_HEIGHT = 925;
	public static final String TAB = "\t";
	public static JFrame frame;
	public static File file;
	public static JInternalFrame currentFrame;
	public static GUIFrame currentWindow;
	public static JSplitPane currentPane;
	private static Object lock = new Object();
	public static Map<String, JButton> buttonMap = new LinkedHashMap<>();
  
	public static void main(String[] args){
		currentPane = new JSplitPane();
		file = new File("settings.txt");
		frame = new JFrame("GUI of Everything!");
		JMenuBar menu = new JMenuBar();
		JMenuBar bottom = new JMenuBar();
		SmallLinkWindow[] smallwindows = WindowEntry.getSmallWindows();
		bottom.setLayout(new GridLayout(1, smallwindows.length));
		for (SmallLinkWindow window : smallwindows){
			JButton button = window.getButton();
			String name = window.getName();
			button.setName(name);
			button.addActionListener(getActionListener(window));
			buttonMap.put(name, button);
		}      
		for (JButton button : buttonMap.values()) {
			bottom.add(button);
		}
		SettingsLoader.obtainSettings();
		Window[] windows = WindowEntry.getAllWindows();
		currentFrame = windows[0].getInsideFrame();
		for(Window window : windows){
			JButton item = new JButton(window.getName());
			item.addActionListener(getActionListener(window));
			menu.add(item);
		}
	    menu.add(getRefresh(), "East");
   	 	frame.setSize(2000, 1000);
   	 	frame.add(menu, "North");
   	 	frame.add(bottom, "South");
   	 	frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
   	 	frame.setVisible(true);
   	 	frame.setDefaultCloseOperation(3);
   	 	System.out.println("Loaded!");
	}
  
	private static JButton getRefresh(){
		try{
			BufferedImage image = ImageIO.read(new File("refresh.png"));
			int type = image.getType() == 0 ? 2 : image.getType();
			image = resizeImage(image, type, 25, 25);
			ImageIcon icon = new ImageIcon(image);
			JButton label = new JButton(icon);
			label.addActionListener(getActionListener());
			return label;
		}catch (IOException e){
			ErrorWindow.forException(e);
		}
		return null;
	}
  
	private static ActionListener getActionListener(){
		return new ActionListener(){
      
		  	public void actionPerformed(ActionEvent arg0){    
		  		Entry.requestRepaint(Entry.currentWindow);
		  	}
	  };
  }
  
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();  
		return resizedImage;
	}
  
	private static ActionListener getActionListener(GUIFrame window){
		return new ActionListener(){
      
			public void actionPerformed(ActionEvent arg0){
				if(window instanceof ColourFrameShower){
					window.getInsideFrame();
					System.out.println("Ignoring ColourFrameShower!");
				}else Entry.newWindow(window);
			}
		  
		};
	}
  
	protected static void newWindow(GUIFrame window){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JSplitPane pane = new JSplitPane(0);
				JInternalFrame intFrame = window.getInsideFrame();
				pane.setRightComponent(intFrame);
				pane.setLeftComponent(new JLabel(window.getDescription()));
				synchronized (Entry.lock){
					Entry.frame.remove(Entry.currentPane);
					Entry.frame.add(pane);
				}
				synchronized (Entry.lock){
					Entry.frame.revalidate();
					pane.setVisible(true);
					intFrame.setVisible(true);
					Entry.frame.repaint();
					if ((window instanceof ColourFrameShower)) {
						return;
					}
					Entry.currentWindow = window;
					Entry.currentPane = pane;
					Entry.currentFrame = intFrame;
				}
			}
		});
	}
  
	public static void requestRepaint(GUIFrame window){
		if (window == null){
			System.out.println("Window is null!");
		}else if (currentWindow == window){
			newWindow(window);
		}else{
			System.out.println("Error: Frame not present");
		}
	}
  
	protected static void pushColourChange(Color colour){
		currentWindow.setColour(colour);
	}
}
