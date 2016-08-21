package Settings;

public class GUISetting {

	private String heading;
	private String text;
	private boolean value;
	
	public GUISetting(String name, String sub){
		text = name;
		heading = sub;
		value = true; //The default for all options.
	}
	
	public String getText(){
		return text;
	}
	
	public String getHeading(){
		return heading;
	}

	public void setValue(boolean b) {
		value = b;
	}
	
	public boolean getValue(){
		return value;
	}
	
}
