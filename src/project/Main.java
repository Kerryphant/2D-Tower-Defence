package project;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

public class Main {

	public static void main (String [] args){

		//need this object in order to speak to the graphics card
		GraphicsDevice vc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
		//Give the window the title "Tower defence", also gives us an object to work with
		JFrame window = new JFrame ("Tower defence");
		
		//Makes sure the program terminates properly
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//No title bar/scroll bars on the window
		window.setUndecorated(true);
		//Sets full screen
		window.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		//Instance of my Control class. Will be used to essentially control what state the game is in, as well as setting up the window
		Control controlObj = Control.get(vc.getDisplayMode().getWidth(), vc.getDisplayMode().getHeight());
		//Adds the control object I just made to the screen
		window.add(controlObj);
		
		
		window.setVisible(true); //Makes the window visible
		
		controlObj.start();

	}

}