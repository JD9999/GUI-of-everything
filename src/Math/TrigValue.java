package Math;

public class TrigValue {

	private String position;
	private int value = 0;
	
	public String getPosition(){
		return position;
	}
	
	public int getValue(){
		return value;
	}
	
	public TrigValue(String pos) throws TrigonometryException{
		if(pos.toLowerCase().contains("hyp")) position = "hypotenuse";
		else if(pos.toLowerCase().contains("opp")) position = "opposite";
		else if(pos.toLowerCase().contains("ad")) position = "adjacent";
		else throw new TrigonometryException("Unable to interpret position:" + pos);
	}
	
	public TrigValue(String pos, int value) throws TrigonometryException{
		this(pos);
		if(value > 0) this.value = value;
		else throw new TrigonometryException("Side length cannot be a negative number!");
	}
	
	public static String HYPOTENUSE = "hyp";
	public static String OPPOSITE = "opp";
	public static String ADJACENT = "ad";
	
}
