package Converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Converts a set to certain objects. Send me suggestions if there's something you can't convert it to
 * @param <T> The type of the list
 */
public class SetConverter<T> {
	
	/**
	 * Add a set to the converter
	 * @param s the set to convert
	 */
	public SetConverter(Set<T> s){
		set = s;
	}
	
	/**
	 * The set stored from the constructor
	 */
	private Set<T> set;
	
	/**
	 * Converts the list to a set.
	 * @return A list representing the set
	 */
	public List<T> toList(){
		Iterator<T> it = set.iterator();
		List<T> list = new ArrayList<T>();
		for(int i = 0; i < set.size(); i++){
			list.add(it.next());
		}
		return list;
	}
	
	/**
	 * Converts the set to a string,, such as for two set entries aa and bb:
	 * 			String s = aa bb
	 */
	public String toString(){
		String s = "";
		for (Iterator<T> it = set.iterator(); it.hasNext();) {
	        T f = it.next();
	        s = s + f.toString();
	    }
		return s;
	}
	
}
