package Math;

public class TrigonometryException extends Exception {

	String message;
	
	public TrigonometryException(String string) {
		message = string;
	}

	private static final long serialVersionUID = 4916560792104825371L;

	public String getMessage(){
		return message;
	}
	
}
