package Math;

import java.util.ArrayList;
import java.util.List;

public class Pythagoras {
	
	private List<String> working;
	
	public Pythagoras() {
		working = new ArrayList<String>();
	}
	
	public boolean isTriad(double a, double b, double c){
		double as = a * a;
		working.add("a squared = " + as);
		double bs = b * b;
		working.add("b squared = " + bs);
		double cs = c * c;
		working.add("c squared = " + cs);
		if(as+bs == cs) working.add(cs + "is equal to: " + as + " and " + bs);
		else working.add(cs + "is not equal to: " + as + " and " + bs);
		return as + bs == cs;
	}
	
	public double getHypotenuse(double a, double b){
		double as = a * a;
		working.add("a squared = " + as);
		double bs = b * b;
		working.add("b squared = " + bs);
		double cs = as + bs;
		working.add("hypotenuse squared = " + as + " plus " + bs);
		double sqrt = Math.sqrt(cs);
		working.add("hypotenuse = " + sqrt);
		return sqrt;
	}
	
	public double getShorterSide(double a, double hyp){
		if(a > hyp) throw new IllegalArgumentException("The hypotenuse cannot be smaller than a shorter side!");
		double cs = hyp * hyp;
		working.add("hypotenuse squared = " + hyp);
		double as = a * a;
		working.add("shorter side squared = " + as);
		double bs = cs - as;
		working.add("other shorter side squared = " + cs + " minus " + as);
		double sqrt = Math.sqrt(bs);
		working.add("Shorter side = " + sqrt);
		return sqrt;
	}
	
	//These two methods are not being used by the GUI
	
	public double getSideLengthOfSquare(double hyp){
		double cs = hyp * hyp;
		double sqrt = Math.sqrt(cs);
		return sqrt /2;
	}
	
	public double getHypotenuseOfSquare(double side){
		double ss = side * side;
		double hyp = 2 * ss;
		return hyp;
	}

	public List<String> getWorking() {
		return working;
	}
	
}
