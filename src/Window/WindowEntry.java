package Window;

public class WindowEntry {

	public static Window[] getAllWindows(){
		Window astro = new AstronautWindow();
		Window graphic = new CreateGraphicsWindow();
		Window math = new MathWindow();
		Window cal = new CalendarWindow();
		Window note = new NoteWindow();
		return new Window[]{astro, cal, graphic, math, note};
	}
	
	public static SmallLinkWindow[] getSmallWindows(){
		SmallLinkWindow settings = new SettingsWindow();
		TimeWindow time = new TimeWindow();
		ColourFrameShower colour = new ColourFrameShower();
		return new SmallLinkWindow[]{settings, time, colour};
	}
	
}
