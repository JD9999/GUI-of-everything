package Entry;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColourFrame implements ChangeListener{

	private JColorChooser chooser = new JColorChooser();
	
	public void run() {
		Runnable r = new Runnable(){
			@Override
			public void run() {
				setup();
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	
	protected void setup() {
		JFrame frame = new JFrame();
		frame.add(chooser);
		chooser.getSelectionModel().addChangeListener(this);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	public void setChooserColour(Color c){
		chooser.setColor(c);
	}
	
	public Color getColour(){
		return chooser.getColor();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		System.out.println("Colour changed!");
		Color c = getColour();
		Entry.pushColourChange(c);
	}
	
}
