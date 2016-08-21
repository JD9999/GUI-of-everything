package Math;

import java.util.ArrayList;
import java.util.List;

public class Algebra {
	
	public Algebra(){
	}
	
	public double getValueOfUnknown(double multiplier, double total){
		double value = total / multiplier;
		return value;
	}
	
	public double getValueOfUnknown(double multiplier, double addition, double total){
		double newtotal = total - addition;
		return getValueOfUnknown(multiplier, newtotal);
	}
	
	public List<Integer> getListOfPossibilities(int rangeBottom, int rangeTop, boolean inside){
		List<Integer> intrange = new ArrayList<Integer>();
		for(int i = rangeBottom; i < rangeTop; i++){
			if(rangeBottom == i && inside) continue;
			intrange.add(i);
		}
		if(!inside) intrange.add(rangeTop);
		return intrange;
	}
	
	public double makePositive(double d){
		if(d < 0) return d - 2 * d;
		else return d;
	}
	
}
