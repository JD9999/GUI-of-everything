package Window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

import Entry.Entry;
import Settings.GUISetting;

public class CreateGraphicsWindow implements Window {

	private final String RECTANGLE = "rec";
	private final String CIRCLE = "cir";
	private final String TRIANGLE = "tri";
	private final String WHITESPACE = "whi";
	
	private final JSplitPane pane = new JSplitPane();
	private JButton button;
	protected JButton currentButton;
	private static List<JButton> buttonList;
	private static JTextField row;
	private static JTextField column;
	private static JButton rectangleShape;
	private static JButton circleShape;
	private static JButton triangleShape;
	private static JButton whitespace;
	private static JButton selectionButton;

	public CreateGraphicsWindow(){
		buttonList = new ArrayList<JButton>();
		for(int i = 0; i < 9; i++){
			try {
				BufferedImage white = ImageIO.read(new File("white.png"));
				int type = white.getType() == 0? BufferedImage.TYPE_INT_ARGB : white.getType();
				BufferedImage newWhite = Entry.resizeImage(white, type, 200, 200);
				ImageIcon whiteimage = new ImageIcon(newWhite);
				whiteimage.setDescription(WHITESPACE);
				buttonList.add(new JButton(whiteimage));
			} catch (IOException e) {
				ErrorWindow.forException(e);
			}
		}
	}

	@Override
	public String getName() {
		return "Create Graphics";
	}

	@Override
	public JInternalFrame getInsideFrame() {
		JInternalFrame frame = new JInternalFrame();
		frame.add(getSplitPane());
		return frame;
	}

	private JSplitPane getSplitPane() {	
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		try{
			System.out.println("Setting up right component!");
			BufferedImage white = ImageIO.read(new File("white.png"));
			ImageIcon whiteimage = new ImageIcon(white);
			whiteimage.setDescription(WHITESPACE);
			whitespace = new JButton(whiteimage);
			whitespace.addActionListener(getSelectionListener(whitespace));
			BufferedImage rect = ImageIO.read(new File("Rectangle.png"));
			ImageIcon rec = new ImageIcon(getCroppedImage(rect, 0));
			rec.setDescription(RECTANGLE);
			BufferedImage cir = ImageIO.read(new File("Circle.png"));
			ImageIcon circle = new ImageIcon(getCroppedImage(cir, 0));
			circle.setDescription(CIRCLE);
			BufferedImage tri = ImageIO.read(new File("Triangle.png"));
			ImageIcon triangle = new ImageIcon(getCroppedImage(tri, 0));
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
		}catch(IOException e){
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
		JPanel drawingPanel = getDrawingPanel(whitespace, buttonList);
		overseer.add(drawingPanel);
		pane.setLeftComponent(overseer);
		return pane;
		
	}

	private ActionListener getSelectionListener(JButton button) {
		return new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				selectionButton = button;
			}
			
		};
	}

	private JPanel getDrawingPanel(JButton whiteButton, List<JButton> buttons) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,3));
		for(int i = 0; i < buttons.size(); i++){
			System.out.println("Index: " + i);
			JButton button = buttons.get(i);
			button.addActionListener(getImgListener(button));
			panel.add(button);
		}
		return panel;
	}

	private ActionListener getImgListener(JButton button) {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				currentButton = button;
				openEditFrame();
			}
			
		};
	}

	protected void openEditFrame(){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				JFrame frame = new JFrame();
				JPanel shapePanel = new JPanel();
				shapePanel.setLayout(new GridLayout(4,1));
				JButton recShape = rectangleShape;
				JButton cirShape = circleShape;
				JButton triShape = triangleShape;
				JButton whiteSpace = whitespace;
				for(ActionListener al : recShape.getActionListeners()) recShape.removeActionListener(al);
				for(ActionListener al : cirShape.getActionListeners()) cirShape.removeActionListener(al);
				for(ActionListener al : triShape.getActionListeners()) triShape.removeActionListener(al);
				for(ActionListener al : whiteSpace.getActionListeners()) whiteSpace.removeActionListener(al);
				String xPos = getXPos(currentButton);
				xPos = xPos.substring(0, xPos.indexOf('.'));
				System.out.println(xPos);
				row.setText(xPos);
				String yPos = getYPos(currentButton);
				yPos = yPos.substring(0, yPos.indexOf('.'));
				System.out.println(yPos);
				column.setText(yPos);
				shapePanel.add(recShape);
				shapePanel.add(cirShape);
				shapePanel.add(triShape);
				shapePanel.add(whiteSpace);
				JPanel colourRotatePanel = new JPanel();
				JPanel rotatePanel = getRotatePanel();
				colourRotatePanel.add(rotatePanel);
				JPanel simpleColourChanger = getSimpleColourChanger();
				colourRotatePanel.add(simpleColourChanger);
				frame.setLayout(new GridLayout(1,2));
				frame.add(colourRotatePanel);
				frame.add(shapePanel);
				frame.setSize(600, 600);
				frame.setVisible(true);
			}
			
		});
	}
	
	protected String getYPos(JButton button) {
		Point p = button.getLocation();
		int height = button.getHeight();
		return String.valueOf(p.getY() / (height /3));
	}

	protected String getXPos(JButton button) {
		Point p = button.getLocation();
		int width = button.getWidth();
		return String.valueOf(p.getX() / (width / 3));
	}

	protected JPanel getRotatePanel() {
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

	protected JPanel getSimpleColourChanger() {
		JPanel panel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		JButton redButton = new JButton();
		redButton.addActionListener(getColourListener(Color.RED));
		redButton.setText("Red");
		JButton orangeButton = new JButton();
		orangeButton.addActionListener(getColourListener(Color.ORANGE));
		orangeButton.setText("Orange");
		JButton pinkButton = new JButton();
		pinkButton.addActionListener(getColourListener(Color.PINK));
		pinkButton.setText("Pink");
		JButton yellowButton = new JButton();
		yellowButton.addActionListener(getColourListener(Color.YELLOW));
		yellowButton.setText("Yellow");
		//Light green is not defined, therefore we need to define it.
		Color lightGreen = new Color(102, 255, 102);
		JButton lightGreenButton = new JButton();
		lightGreenButton.addActionListener(getColourListener(lightGreen));
		lightGreenButton.setText("Light green");
		JButton greenButton = new JButton();
		greenButton.addActionListener(getColourListener(Color.GREEN));
		greenButton.setText("Green");
		JButton lightBlueButton = new JButton();
		lightBlueButton.addActionListener(getColourListener(Color.CYAN));
		lightBlueButton.setText("Light blue");
		JButton blueButton = new JButton();
		blueButton.addActionListener(getColourListener(Color.BLUE));
		blueButton.setText("Blue");
		JButton purpleButton = new JButton();
		purpleButton.addActionListener(getColourListener(Color.MAGENTA));
		purpleButton.setText("Purple");
		JButton whiteButton = new JButton();
		whiteButton.addActionListener(getColourListener(Color.WHITE));
		whiteButton.setText("White");
		//Brown is not defined, therefore we need to define it
		Color brown = new Color(102, 51, 0);
		JButton brownButton = new JButton();
		brownButton.addActionListener(getColourListener(brown));
		brownButton.setText("Brown");
		JButton blackButton = new JButton();
		blackButton.addActionListener(getColourListener(Color.BLACK));
		blackButton.setText("Black");
		
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
		
		panel.setLayout(new GridLayout(4,3));
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

	private ActionListener getColourListener(Color colour) {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = openButton();
				g.setColor(colour);
				repaint();
			}
			
		};
	}

	protected void repaint() {
		Entry.requestRepaint(this);
	}

	private Graphics openButton() {
		Icon icon = currentButton.getIcon();
		if(icon instanceof ImageIcon){
			ImageIcon imageIcon = (ImageIcon)icon;
			return imageIcon.getImage().getGraphics();
		}else ErrorWindow.forException(new RuntimeException("Button's icon is not an ImageIcon!"));
		return null;
	}

	protected DocumentListener getDocumentListener(JTextField field) {
		return new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				rotate(field);
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				rotate(field);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				rotate(field);
			}
			
		};
	}

	private void rotate(JTextField field){
		Graphics g = openButton();
		if(g instanceof Graphics2D){
			Graphics2D g2d = (Graphics2D)g;
			String text = field.getText();
			int rotation = Integer.parseInt(text);
			g2d.rotate(Math.toRadians(rotation));
			repaint();
		}else ErrorWindow.forException(new RuntimeException("Button's graphics is not an instance of Graphics2D!"));
	}

	private ActionListener getAddListener() {
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				int row = Integer.parseInt(CreateGraphicsWindow.row.getText());
				int column = Integer.parseInt(CreateGraphicsWindow.column.getText());
				int searchIndex = 3 * row + column;
				buttonList.remove(searchIndex);
				buttonList.add(searchIndex, selectionButton);
				repaint();
			}

		};
	}

	@Override
	public GUISetting[] getSettings() {
		return new GUISetting[]{};
	}

	@Override
	public void setColour(Color c) {
		button.setForeground(c);
	}

	@Override
	public Color getCurrentColour() {
		return button.getForeground();
	}

	
	//Copied on Stack Overflow - http://stackoverflow.com/questions/10678015/how-to-auto-crop-an-image-white-border-in-java
	private BufferedImage getCroppedImage(BufferedImage source, double tolerance) {
		   // Get our top-left pixel color as our "baseline" for cropping
		   int baseColor = source.getRGB(0, 0);

		   int width = source.getWidth();
		   int height = source.getHeight();

		   int topY = Integer.MAX_VALUE, topX = Integer.MAX_VALUE;
		   int bottomY = -1, bottomX = -1;
		   for(int y=0; y<height; y++) {
		      for(int x=0; x<width; x++) {
		         if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)) {
		            if (x < topX) topX = x;
		            if (y < topY) topY = y;
		            if (x > bottomX) bottomX = x;
		            if (y > bottomY) bottomY = y;
		         }
		      }
		   }

		   BufferedImage destination = new BufferedImage( (bottomX-topX+1), 
		                 (bottomY-topY+1), BufferedImage.TYPE_INT_ARGB);

		   destination.getGraphics().drawImage(source, 0, 0, 
		               destination.getWidth(), destination.getHeight(), 
		               topX, topY, bottomX, bottomY, null);

		   return destination;
		}

		private boolean colorWithinTolerance(int a, int b, double tolerance) {
		    int aAlpha  = (int)((a & 0xFF000000) >>> 24);   // Alpha level
		    int aRed    = (int)((a & 0x00FF0000) >>> 16);   // Red level
		    int aGreen  = (int)((a & 0x0000FF00) >>> 8);    // Green level
		    int aBlue   = (int)(a & 0x000000FF);            // Blue level

		    int bAlpha  = (int)((b & 0xFF000000) >>> 24);   // Alpha level
		    int bRed    = (int)((b & 0x00FF0000) >>> 16);   // Red level
		    int bGreen  = (int)((b & 0x0000FF00) >>> 8);    // Green level
		    int bBlue   = (int)(b & 0x000000FF);            // Blue level

		    double distance = Math.sqrt((aAlpha-bAlpha)*(aAlpha-bAlpha) +
		                                (aRed-bRed)*(aRed-bRed) +
		                                (aGreen-bGreen)*(aGreen-bGreen) +
		                                (aBlue-bBlue)*(aBlue-bBlue));

		    // 510.0 is the maximum distance between two colors 
		    // (0,0,0,0 -> 255,255,255,255)
		    double percentAway = distance / 510.0d;     

		    return (percentAway > tolerance);
		}
}
