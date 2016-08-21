package Math;

import java.util.ArrayList;
import java.util.List;

public class Trig {

	private List<String> working;
	
	public Trig(){
		working = new ArrayList<String>();
	}
	
	public double getSideLength(TrigValue known, int degrees, TrigValue unknown) throws TrigonometryException{
		//Create and set our variables
		boolean sine = true;
		boolean cosine = true;
		boolean tangent = true;
		int knownLength = known.getValue();
		int unknownLength = unknown.getValue();
		String knownPos = known.getPosition();
		String unknownPos = unknown.getPosition();
		
		//We need to run some tests to make sure that the values and positions are valid.
		if(knownLength == 0) throw new TrigonometryException("Known side must have a value!");
		if(unknownLength != 0) throw new TrigonometryException("Unknown side must not have a value!");
		//Just setting a few values here, but it's in this section because of the if -else if- else throw statement.
		if(knownPos.equals("hypotenuse") || unknownPos.equals("hypotenuse")){
			tangent = false;
		}
		else if(knownPos.equals("opposite") || unknownPos.equals("opposite")) cosine = false;
		else if(knownPos.equals("adjacent") || unknownPos.equals("adjacent")) sine = false;
		else throw new TrigonometryException("Either one or both of these values are invalid!: " + knownPos + " or " + unknownPos);
		if(!sine && !cosine && !tangent) throw new TrigonometryException("How can two values have three positions? Please report!");
		if(knownPos.equals(unknownPos)) throw new TrigonometryException("Both values cannot be on the same side!");
		
		//There's one more exception thrown here, but for the same reason as before.
		//Calculate the side's length.
		double rads = Math.PI /180;
		double ans;
		if(sine){
			working.add("we are working with sine");
			working.add("side length = degrees * sin(Pi)");// We cannot type pi
			ans =  degrees * Math.sin(rads);
			working.add("side length = " + degrees + " * " + ans / degrees);
			working.add("answer = " + ans);
		}
		else if(cosine){
			working.add("we are working with cosine");
			working.add("side length = degrees * cos(Pi)");// We cannot type pi
			ans =  degrees * Math.cos(rads);
			working.add("side length = " + degrees + " * " + ans / degrees);
			working.add("answer = " + ans);
		}
		else if(tangent){
			working.add("we are working with tangent");
			working.add("side length = degrees * tan(Pi)");// We cannot type pi
			ans =  degrees * Math.tan(rads);
			working.add("side length = " + degrees + " * " + ans / degrees);
			working.add("answer = " + ans);
		}
		else throw new TrigonometryException("How can two values have three positions? Please report!");
		return ans;
	} 
	
	public double getAngleSize(TrigValue a, TrigValue b) throws TrigonometryException{
		//Create and set our variables
		boolean sine = true;
		boolean cosine = true;
		boolean tangent = true;
		int aLength = a.getValue();
		int bLength = b.getValue();
		String aPos = a.getPosition();
		String bPos = b.getPosition();
		double ans = 0;
		
		//We need to run some tests to make sure that the values and positions are valid.
		if(aLength == 0 || bLength == 0) throw new TrigonometryException("Both sides must have a value!");
		//Just setting a few values here, but it's in this section because of the if -else if- else throw statement.
		if(aPos.equals("hypotenuse") || bPos.equals("hypotenuse")) tangent = false;
		else if(aPos.equals("opposite") || bPos.equals("opposite")) cosine = false;
		else if(aPos.equals("adjacent") || bPos.equals("adjacent")) sine = false;
		else throw new TrigonometryException("Either one or both of these values are invalid!: " + aPos + " or " + bPos);
		if(!sine && !cosine && !tangent) throw new TrigonometryException("How can two values have three positions? Please report!");
		if(aPos.equals(bPos)) throw new TrigonometryException("Both values cannot be on the same side!");
				
		//Work out the angle
		if(sine){
			working.add("We are working with sine");
			int sin;
			if(aPos.equalsIgnoreCase("hypotenuse")){
				sin = bLength / aLength;
				working.add("the angle = sin-1(smaller side length / hypotenuse length)");
				working.add("the angle = sin-1(" + bLength + " / " + aLength + ")");
				working.add("the angle = sin-1(" + sin + ")");
				ans = Math.asin(sin);
				working.add("the angle = " + ans);
			}
			else if(bPos.equalsIgnoreCase("hypotenuse")){
				sin = aLength / bLength;
				working.add("the angle = sin-1(smaller side length / hypotenuse length)");
				working.add("the angle = sin-1(" + aLength + " / " + bLength + ")");
				working.add("the angle = sin-1(" + sin + ")");
				ans = Math.asin(sin);
				working.add("the angle = " + ans);
			}
			else throw new TrigonometryException("Sine requires one of the two variables to be a hypotenuse!");
		}else if(cosine){
			working.add("we are working with cosine");
			int cos;
			if(aPos.equalsIgnoreCase("hypotenuse")){
				cos = bLength / aLength;
				working.add("the angle = cos-1(smaller side length / hypotenuse length)");
				working.add("the angle = cos-1(" + bLength + " / " + aLength + ")");
				working.add("the angle = cos-1(" + cos + ")");
				ans = Math.acos(cos);
				working.add("the angle = " + ans);
			}
			else if(bPos.equalsIgnoreCase("hypotenuse")){
				cos = aLength / bLength;
				working.add("the angle = cos-1(smaller side length / hypotenuse length)");
				working.add("the angle = cos-1(" + aLength + " / " + bLength + ")");
				working.add("the angle = cos-1(" + cos + ")");
				ans = Math.acos(cos);
				working.add("the angle = " + ans);
			}
			else throw new TrigonometryException("Cosine requires one of the two variables to be a hypotenuse!");
		}else if(tangent){
			int tan;
			if(aPos.equalsIgnoreCase("adjacent")){
				tan = bLength / aLength;
				working.add("the angle = tan-1(opposite length / adjacent length)");
				working.add("the angle = tan-1(" + bLength + " / " + aLength + ")");
				working.add("the angle = tan-1(" + tan + ")");
				ans = Math.atan(tan);
				working.add("the angle = " + ans);
			}
			else if(bPos.equalsIgnoreCase("adjacent")){
				tan = aLength / bLength;
				working.add("the angle = tan-1(opposite length / adjacent length)");
				working.add("the angle = tan-1(" + aLength + " / " + bLength + ")");
				working.add("the angle = tan-1(" + tan + ")");
				ans = Math.atan(tan);
				working.add("the angle = " + ans);
			}
			else throw new TrigonometryException("Tangent requires one of the two variables to be the adjacent side!");
		}else throw new TrigonometryException("How can two values have three positions? Please report!");
		return ans;
	}
	
}
