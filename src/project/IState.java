package project;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

//This is the interface that my state classes implement
public interface IState {
	
	/*These are methods that will be required in each of the state classes. They are required as the control class will call them.
	*By using an interface instead of a class, the classes that implement this will be required to have these methods within them, 
	*preventing runtime errors
	*/
	public boolean update();
	public boolean render(Graphics g);
	public boolean mouse(MouseEvent event);
	
	
}