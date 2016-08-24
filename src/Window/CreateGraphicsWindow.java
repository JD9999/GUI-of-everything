package Window;

import Entry.Entry;
import Settings.GUISetting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreateGraphicsWindow implements Window{

	private final String RECTANGLE = "rec";
	private final String CIRCLE = "cir";
	private final String TRIANGLE = "tri";
	private final String WHITESPACE = "whi";
	private final JSplitPane pane = new JSplitPane();
	private static JButton button;
	protected JButton currentButton;
	protected JFrame frame;
	protected JButton changeButton;
	private static List<JButton> buttonList;
	private static JTextField row;
	private static JTextField column;
	private static JButton rectangleShape;
	private static JButton circleShape;
	private static JButton triangleShape;
	private static JButton whitespace;
	private static boolean inLaunch;

	public CreateGraphicsWindow(){
		buttonList = new ArrayList<>();
		for (int i = 0; i < 9; i++) 
			try{
				BufferedImage white = ImageIO.read(new File("white.png"));
				int type = white.getType() == 0 ? 2 : white.getType();
				BufferedImage newWhite = Entry.resizeImage(white, type, 162, 158);
				ImageIcon whiteimage = new ImageIcon(newWhite);
				whiteimage.setDescription(WHITESPACE);
				buttonList.add(new JButton(whiteimage));
			}catch (IOException e){
				ErrorWindow.forException(e);
			}
	}

	public String getName(){
		return "Create Graphics";
	}

	public JInternalFrame getInsideFrame(){
		JInternalFrame frame = new JInternalFrame();
		frame.add(getSplitPane());
		return frame;
	}

	private JSplitPane getSplitPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		try{
			System.out.println("Setting up right component!");
			BufferedImage white = ImageIO.read(new File("white.png"));
			ImageIcon whiteimage = new ImageIcon(white);
			whiteimage.setDescription(WHITESPACE);
			whitespace = new JButton(whiteimage);
			whitespace.addActionListener(getSelectionListener(whitespace));
			BufferedImage rect = ImageIO.read(new File("Rectangle.png"));
			ImageIcon rec = new ImageIcon(getCroppedImage(rect, 0.0D));
			rec.setDescription(RECTANGLE);
			BufferedImage cir = ImageIO.read(new File("Circle.png"));
			ImageIcon circle = new ImageIcon(getCroppedImage(cir, 0.0D));
			circle.setDescription(CIRCLE);
			BufferedImage tri = ImageIO.read(new File("Triangle.png"));
			ImageIcon triangle = new ImageIcon(getCroppedImage(tri, 0.0D));
			triangle.setDescription(TRIANGLE);
			rectangleShape = new JButton(rec);
			rectangleShape.addActionListener(getSelectionListener(rectangleShape));
			circleShape = new JButton(circle);
			circleShape.addActionListener(getSelectionListener(circleShape));
			triangleShape = new JButton(triangle);
			triangleShape.addActionListener(getSelectionListener(triangleShape));
			panel.add(rectangleShape);
			panel.add(circleShape);
			panel.add(triangleShape);
			panel.add(whitespace);
		}catch (IOException e){
			ErrorWindow.forException(e);
		}
		JPanel textPanel = new JPanel();
		row = new JTextField();
		column = new JTextField();
		GridLayout layout = new GridLayout(1, 2);
		textPanel.setLayout(layout);
		textPanel.add(row);
		textPanel.add(column);
		panel.add(textPanel);
		button = new JButton("Add shape");
		button.addActionListener(getAddListener());
		panel.add(button);
		pane.setRightComponent(panel);

		System.out.println("Setting up left component!");
		pane.setDividerLocation(600);
		JPanel overseer = new JPanel();
		overseer.setMinimumSize(new Dimension(600, Entry.INTERNAL_FRAME_HEIGHT));
		JPanel drawingPanel = getDrawingPanel();
		overseer.add(drawingPanel);
		pane.setLeftComponent(overseer);
		pane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, getPropertyChangeListener());
		return pane;
	}

	private PropertyChangeListener getPropertyChangeListener(){
		return new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent event){
				System.out.println("Divider location at: " + pane.getDividerLocation());
				pane.setDividerLocation(600);
			}
		};
	}

	private ActionListener getSelectionListener(final JButton button){
		return new ActionListener(){
			public void actionPerformed(ActionEvent event){
				changeButton = button;
			}
		};
	}

	private JPanel getDrawingPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < buttonList.size(); i++){
			System.out.println("Index: " + i);
			JButton button = buttonList.get(i);
			button.addActionListener(getImgListener(button));
			panel.add(button);
		}
		return panel;
	}

	private ActionListener getImgListener(final JButton button){
		return new ActionListener(){
			public void actionPerformed(ActionEvent event){
				currentButton = button;
				System.out.println("Opening frame!");
				openEditFrame();
			}
		};
	}

	protected void openEditFrame(){
		if (!inLaunch){
			openAndLinkFrame();
		}else System.out.println("Window already in launch!");
	}

	private void openAndLinkFrame(){
		inLaunch = true;
		SwingUtilities.invokeLater(new Runnable(){

			public void run(){
				frame = new JFrame();
				frame.setAlwaysOnTop(true);
				frame.addWindowListener(getWindowListener());
				JPanel shapePanel = new JPanel();
				shapePanel.setLayout(new GridLayout(4, 1));
				JButton recShape = CreateGraphicsWindow.rectangleShape;
				JButton cirShape = CreateGraphicsWindow.circleShape;
				JButton triShape = CreateGraphicsWindow.triangleShape;
				JButton whiteSpace = CreateGraphicsWindow.whitespace;
				for(ActionListener al : recShape.getActionListeners()) recShape.removeActionListener(al);
				for(ActionListener al : cirShape.getActionListeners()) cirShape.removeActionListener(al);
				for(ActionListener al : triShape.getActionListeners()) triShape.removeActionListener(al);
				for(ActionListener al : whiteSpace.getActionListeners()) whiteSpace.removeActionListener(al);
				String xPos = getXPos(currentButton);
				xPos = xPos.substring(0, xPos.indexOf('.'));
				System.out.println(xPos);
				String yPos = getYPos(currentButton);
				yPos = yPos.substring(0, yPos.indexOf('.'));
				System.out.println(yPos);
				CreateGraphicsWindow.row.setText(yPos);
				CreateGraphicsWindow.column.setText(xPos);
				recShape.addActionListener(getExternalSelectionListener(recShape));
				cirShape.addActionListener(getExternalSelectionListener(cirShape));
				triShape.addActionListener(getExternalSelectionListener(triShape));
				whiteSpace.addActionListener(getExternalSelectionListener(whiteSpace));
				shapePanel.add(recShape);
				shapePanel.add(cirShape);
				shapePanel.add(triShape);
				shapePanel.add(whiteSpace);
				JPanel colourRotatePanel = new JPanel();
				JPanel rotatePanel = getRotatePanel();
				colourRotatePanel.add(rotatePanel);
				JPanel simpleColourChanger = getSimpleColourChanger();
				colourRotatePanel.add(simpleColourChanger);
				frame.setLayout(new GridLayout(1, 2));
				frame.add(colourRotatePanel);
				frame.add(shapePanel);
				frame.setSize(600, 600);
				frame.setVisible(true);
			}
		});
	}


	protected WindowListener getWindowListener() {
		return new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				inLaunch = false;
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {	
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			
		};
	}

	protected ActionListener getExternalSelectionListener(final JButton button){
		return new ActionListener(){

			public void actionPerformed(ActionEvent arg0){
				changeButton = button;
				CreateGraphicsWindow.button.doClick();
			}

		};
	}

	protected String getYPos(JButton button){
		Point p = button.getLocation();
		int height = button.getHeight();
		return String.valueOf(p.getY() / (height / 3) / 3.0D);
	}

	protected String getXPos(JButton button){
		Point p = button.getLocation();
		int width = button.getWidth();
		return String.valueOf(p.getX() / (width / 3) / 3.0D);
	}

	protected JPanel getRotatePanel(){
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		label.setText("Rotate:");
		JTextField field = new JTextField();
		field.setText("0");
		field.setEditable(true);
		field.getDocument().addDocumentListener(getDocumentListener(field));
		panel.add(label);
		panel.add(field);
		return panel;
	}

	protected JPanel getSimpleColourChanger(){
		JPanel panel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		JButton redButton = new JButton();
		redButton.addActionListener(getColourListener(Color.RED));
		redButton.setText("Red");
		redButton.setForeground(Color.RED);
		redButton.setBackground(Color.WHITE);
		JButton orangeButton = new JButton();
		orangeButton.addActionListener(getColourListener(Color.ORANGE));
		orangeButton.setText("Orange");
		orangeButton.setForeground(Color.ORANGE);
		orangeButton.setBackground(Color.WHITE);
		JButton pinkButton = new JButton();
		pinkButton.addActionListener(getColourListener(Color.PINK));
		pinkButton.setText("Pink");
		pinkButton.setForeground(Color.PINK);
		pinkButton.setBackground(Color.BLACK);
		JButton yellowButton = new JButton();
		yellowButton.addActionListener(getColourListener(Color.YELLOW));
		yellowButton.setText("Yellow");
		yellowButton.setForeground(Color.YELLOW);
		yellowButton.setBackground(Color.BLACK);
		//Since light green is undefined, we have to define it
		Color lightGreen = new Color(102, 255, 102);
		JButton lightGreenButton = new JButton();
		lightGreenButton.addActionListener(getColourListener(lightGreen));
		lightGreenButton.setText("Light green");
		lightGreenButton.setForeground(lightGreen);
		lightGreenButton.setBackground(Color.WHITE);
		JButton greenButton = new JButton();
		greenButton.addActionListener(getColourListener(Color.GREEN));
		greenButton.setText("Green");
		greenButton.setForeground(Color.GREEN);
		greenButton.setBackground(Color.WHITE);
		JButton lightBlueButton = new JButton();
		lightBlueButton.addActionListener(getColourListener(Color.CYAN));
		lightBlueButton.setText("Light blue");
		lightBlueButton.setForeground(Color.CYAN);
		lightBlueButton.setBackground(Color.BLACK);
		JButton blueButton = new JButton();
		blueButton.addActionListener(getColourListener(Color.BLUE));
		blueButton.setText("Blue");
		blueButton.setForeground(Color.BLUE);
		blueButton.setBackground(Color.WHITE);
		JButton purpleButton = new JButton();
		purpleButton.addActionListener(getColourListener(Color.MAGENTA));
		purpleButton.setText("Purple");
		purpleButton.setForeground(Color.MAGENTA);
		purpleButton.setBackground(Color.WHITE);
		JButton whiteButton = new JButton();
		whiteButton.addActionListener(getColourListener(Color.WHITE));
		whiteButton.setText("White");
		whiteButton.setForeground(Color.WHITE);
		whiteButton.setBackground(Color.BLACK);
		//Since brown is undefined, we must define it
		Color brown = new Color(102, 51, 0);
		JButton brownButton = new JButton();
		brownButton.addActionListener(getColourListener(brown));
		brownButton.setText("Brown");
		brownButton.setForeground(brown);
		brownButton.setBackground(Color.WHITE);
		JButton blackButton = new JButton();
		blackButton.addActionListener(getColourListener(Color.BLACK));
		blackButton.setText("Black");
		blackButton.setForeground(Color.BLACK);
		blackButton.setBackground(Color.WHITE);

		group.add(redButton);
		group.add(orangeButton);
		group.add(yellowButton);
		group.add(pinkButton);
		group.add(lightGreenButton);
		group.add(greenButton);
		group.add(lightBlueButton);
		group.add(blueButton);
		group.add(purpleButton);
		group.add(whiteButton);
		group.add(brownButton);
		group.add(blackButton);

		panel.setLayout(new GridLayout(4, 3));
		panel.add(redButton);
		panel.add(orangeButton);
		panel.add(yellowButton);
		panel.add(pinkButton);
		panel.add(lightGreenButton);
		panel.add(greenButton);
		panel.add(lightBlueButton);
		panel.add(blueButton);
		panel.add(purpleButton);
		panel.add(whiteButton);
		panel.add(brownButton);
		panel.add(blackButton);

		return panel;
	}

	private ActionListener getColourListener(final Color colour){
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				Graphics g = openButton();
				g.setColor(colour);
				button.doClick();
				repaint();
			}
		};
	}

	protected void repaint(){
		if(frame!=null)frame.dispose();
		pane.setLeftComponent(getDrawingPanel());
	}

	private Graphics openButton(){
		Icon icon = currentButton.getIcon();
		if (icon instanceof ImageIcon){
			ImageIcon imageIcon = (ImageIcon)icon;
			return imageIcon.getImage().getGraphics();
		}
		ErrorWindow.forException(new RuntimeException("Button's icon is not an ImageIcon!"));
		return null;
	}

	protected DocumentListener getDocumentListener(final JTextField field){
		return new DocumentListener(){
			
			public void changedUpdate(DocumentEvent arg0){
				rotate(field);
			}

			public void insertUpdate(DocumentEvent arg0){
				rotate(field);
			}

			public void removeUpdate(DocumentEvent arg0){
				rotate(field);
			}
		};
	}

	private void rotate(JTextField field){
		Graphics g = openButton();
		if ((g instanceof Graphics2D)){
			Graphics2D g2d = (Graphics2D)g;
			String text = field.getText();
			int rotation = Integer.parseInt(text);
			g2d.rotate(Math.toRadians(rotation));
			repaint();
		}else{
			ErrorWindow.forException(new RuntimeException("Button's graphics is not an instance of Graphics2D!"));
		}
	}

	private ActionListener getAddListener(){
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				List<JButton> buttons = new ArrayList<>();
				int row = Integer.parseInt(CreateGraphicsWindow.row.getText());
				int column = Integer.parseInt(CreateGraphicsWindow.column.getText());
				int searchIndex = 3 * row + column;
				System.out.println(buttonList.size());
				for(int i = 0; i < buttonList.size(); i++){
					JButton b = changeButton;
					b.addActionListener(getImgListener(b));
					if(i == searchIndex) buttons.add(b);
					else buttons.add(buttonList.get(i));
				}
				buttonList.clear();
				buttonList.addAll(buttons);
				repaint();
			}
		};
	}

	public GUISetting[] getSettings(){
		return new GUISetting[0];
	}

	public void setColour(Color c){
		button.setForeground(c);
	}

	public Color getCurrentColour(){
		return button.getForeground();
	}

	private BufferedImage getCroppedImage(BufferedImage source, double tolerance){
		int baseColor = source.getRGB(0, 0);

		int width = source.getWidth();
		int height = source.getHeight();

		int topY = Integer.MAX_VALUE;int topX = Integer.MAX_VALUE;
		int bottomY = -1;int bottomX = -1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)){
					if (x < topX) {
						topX = x;
					}
					if (y < topY) {
						topY = y;
					}
					if (x > bottomX) {
						bottomX = x;
					}
					if (y > bottomY) {
						bottomY = y;
					}
				}
			}
		}
		BufferedImage destination = new BufferedImage(bottomX - topX + 1, 
				bottomY - topY + 1, 2);

		destination.getGraphics().drawImage(source, 0, 0, 
				destination.getWidth(), destination.getHeight(), 
				topX, topY, bottomX, bottomY, null);

		return destination;
	}

	private boolean colorWithinTolerance(int a, int b, double tolerance){
		int aAlpha = (a & 0xFF000000) >>> 24;
		int aRed = (a & 0xFF0000) >>> 16;
		int aGreen = (a & 0xFF00) >>> 8;
		int aBlue = a & 0xFF;

		int bAlpha = (b & 0xFF000000) >>> 24;
		int bRed = (b & 0xFF0000) >>> 16;
		int bGreen = (b & 0xFF00) >>> 8;
		int bBlue = b & 0xFF;

		double distance = Math.sqrt((aAlpha - bAlpha) * (aAlpha - bAlpha) + 
				(aRed - bRed) * (aRed - bRed) + 
				(aGreen - bGreen) * (aGreen - bGreen) + 
				(aBlue - bBlue) * (aBlue - bBlue));

		double percentAway = distance / 510.0D;

		return percentAway > tolerance;
	}

	public String getDescription(){
		return "This window creates basic graphics using basic shapes. You can rotate, change the colour of the shape, and change the shape entirely.";
	}
}
