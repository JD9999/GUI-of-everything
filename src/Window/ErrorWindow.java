package Window;

import Entry.Entry;
import javax.swing.JFrame;
import javax.swing.JTextPane;

public class ErrorWindow
{
  public static void forException(Exception e)
  {
    JFrame frame = new JFrame();
    JTextPane pane = new JTextPane();
    StringBuilder builder = new StringBuilder();
    for(StackTraceElement element : e.getStackTrace()) builder.append(element.toString());
    pane.setText(builder.toString());
    frame.add(pane);
    frame.setSize(100, 50);
    frame.setVisible(true);
    Entry.frame.setVisible(false);
  }
}
