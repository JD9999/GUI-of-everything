package Note;

import java.util.ArrayList;
import java.util.List;

public class Note {

	private List<String> lines = new ArrayList<String>();
	private String heading = "";
	
	public Note(){}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public void addLine(String line){
		this.lines.add(line);
	}
	
}
