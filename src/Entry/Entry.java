package Entry;

import java.awt.BorderLayout;
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
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import Settings.SettingsLoader;
import Window.ErrorWindow;
import Window.GUIFrame;
import Window.SmallLinkWindow;
import Window.Window;
import Window.WindowEntry;

public class Entry {

	public static final int INTERNAL_FRAME_WIDTH = 1400;
	public static final int INTERNAL_FRAME_HEIGHT = 925;
	public static final String TAB = "\t";
	public static JFrame frame;
	public static File file;
	public static JInternalFrame currentframe;
	public static GUIFrame currentWindow;
	private static Object lock = new Object();
	public static Map<String, JButton> buttonMap = new LinkedHashMap<String, JButton>();
	
	public static void main(String[] args){
		file = new File("settings.txt");
		frame = new JFrame("GUI of Everything!");
		JMenuBar menu = new JMenuBar();
		JMenuBar bottom = new JMenuBar();
		SmallLinkWindow[] smallwindows = WindowEntry.getSmallWindows();
		bottom.setLayout(new GridLayout(1, smallwindows.length));
		for(SmallLinkWindow window: smallwindows){	
			JButton button = window.getButton();
			String name = window.getName();
			button.setName(name);
			button.addActionListener(getActionListener(window));
			buttonMap.put(name, button);
		}
		for(JButton button : buttonMap.values()){
			bottom.add(button);
		}
		SettingsLoader loader = new SettingsLoader();
		loader.obtainSettings();
		Window[] windows = WindowEntry.getAllWindows();
		for(Window window : windows){
			JButton item = new JButton(window.getName());
			item.addActionListener(getActionListener(window));
			menu.add(item);
		}
		currentframe = windows[0].getInsideFrame();
		menu.add(getRefresh(), BorderLayout.EAST);
		frame.setSize(2000, 1000);
		frame.add(menu, BorderLayout.NORTH);
		frame.add(bottom, BorderLayout.SOUTH);
		frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("Loaded!");
	}

	
	private static JButton getRefresh() {
		try {
			BufferedImage image = ImageIO.read(new File("refresh.png"));
			int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
			image = resizeImage(image, type, 25, 25);
			ImageIcon icon = new ImageIcon(image);
			JButton label = new JButton(icon);
			label.addActionListener(getActionListener());
			return label;
		} catch (IOException e) {
			ErrorWindow.forException(e);
		}
		return null;
	}

    private static ActionListener getActionListener() {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				requestRepaint(currentWindow);
			}
			};
	}
	 
	//Copied from http://www.mkyong.com/java/how-to-resize-an-image-in-java/
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
			
		return resizedImage;
	    }
	
	private static ActionListener getActionListener(GUIFrame window) {
		ActionListener listener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newWindow(window);
			}	
		};
		return listener;
	}

	protected static void newWindow(GUIFrame window) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				JInternalFrame intFrame = window.getInsideFrame();
				synchronized(lock){
					frame.remove(currentframe);
					frame.add(intFrame);
				}
				synchronized(lock){
					currentframe = intFrame;
					currentWindow = window;
					intFrame.setVisible(true);
					frame.repaint();
				}
				System.out.println("Done!");
			}
			
		});
	}


	public static void requestRepaint(GUIFrame window){
		if(window == null) System.out.println("Window is null!");
		else if(currentWindow == window){
			newWindow(window);
		}else System.out.println("Error: Frame not present");
	}
	
	protected static void pushColourChange(Color colour){
		currentWindow.setColour(colour);
	}
	
}
