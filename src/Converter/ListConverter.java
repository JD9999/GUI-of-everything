package Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Converts a list to certain objects. Send me suggestions if there's something you can't convert it to
 * @param <T> The type of the list
 */
public class ListConverter<T> {

	/**
	 * Add a list to the converter
	 * @param list the list to convert
	 */
	public ListConverter(List<T> list){
		ls = list;
	}
	
	/**
	 * The list stored from the constructor
	 */
	private List<T> ls = new ArrayList<>();
	
	/**
	 * Converts the list to a string,, such as for two list entries aa and bb:
	 * 			String s = aa bb
	 */
	public String toString(){
		String s = "";
		for(int i = 0; i < ls.size(); i++){
			String ob = ls.get(i).toString();
			s = s + " " + ob;
		}
		return s;
	}
	
	/**
	 * Converts the list to a set.
	 * Uses the TreeSet class
	 * WARNING: In sets duplicate elements are removed.
	 * @return a Set representing the list.
	 */
	public Set<T> toSet(){
		Set<T> s = new TreeSet<T>();
		for(int i = 0; i < ls.size(); i++) s.add(ls.get(i));
		return s;
	}

	/**
	 * Converts the list to a string,, such as for two list entries aa and bb:
	 * 			String s = aa
	 * 					   bb
	 */
	public String toLinedString() {
		String s = "";
		for(int i = 0; i < ls.size(); i++){
			String ob = ls.get(i).toString();
			s = s + "\n" + ob;
		}
		return s;
	}
	
}
