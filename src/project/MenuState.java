package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

public class MenuState implements IState{
	
	Button b1 = null;
	Button b2 = null;
	Button b3 = null;
	Button b4 = null;
	int resW =  Control.width;
	int resH = Control.height;
	Image hScreen = null;
	Image winMessage = null;
	Image lossMessage = null;
	boolean popUp = false;

	
	public MenuState(){
		
		LoadImage imageLoader = LoadImage.get();
		
		hScreen = imageLoader.getImage("Images\\HelpScreen.png");
		
		//create the buttons. The last parameter is the code for the lambda function
		b1 = new Button(570, 250, 300, 100, imageLoader.getImage("Images\\Start.png"), 
				() -> {
					Control.get().setState(3); //when the lambda function for this button is called, set the state to game state
				}
			);
		
		b2 = new Button(570, 370, 300, 100, imageLoader.getImage("Images\\Scoreboard.png"), 
				() -> {
					Control.get().setState(2); //when the lambda function for this button is called, set the state to score board state
				}
			);
		
		b3 = new Button(570, 490, 300, 100, imageLoader.getImage("Images\\Quit.png"), 
				() -> {
					Control.get().setState(4); //when the lambda function for this button is called, exit the game 
				}
			);
		
		b4 = new Button(0, 0, 75, 75, imageLoader.getImage("Images\\Help.png"), 
				() -> {
					popUp = true; //when the lambda function for this button is called,draw the help screen
				}
			);
	}
	
	//there is nothing to update within the menu, but due to the interface I implement it is required to be here
	public boolean update() {
		
		return false;
	}

	
	//draws everything to the screen
	public boolean render(Graphics g) {
		
        // here we are drawing a fresh background. We need to do this so that anything drawn to the screen on previous iterations, is then overwritten
        // we set the colour of the graphic to red and draw a rectangle over the whole screen.
        g.setColor(Color.blue);
        g.fillRect(0,0, resW, resH);
        
        //draw all the buttons to the screen
		b1.drawButton(570, 250, g);
		b2.drawButton(570, 370, g);
		b3.drawButton(570, 490, g);
		b4.drawButton(0, 0, g);
		
		//if the help screen is active, draw it
		if (popUp){
			 g.drawImage(hScreen, 530, 250, null);
		}
		
		

		
		return false;
	}

	
	//This is the mouse listener method. Allows us to carry out an action when the mouse is clicked
	public boolean mouse(MouseEvent event) {
		
		
		if(popUp){
			//when the user clicks, close the pop up help screen
			popUp = false; 
		}
		else{
			//check if any of the buttons were clicked. They will carry out their lambda functions within this method
			if(event.getButton() == MouseEvent.BUTTON1){
				b1.checkClick(event.getX(), event.getY());
				
				b2.checkClick(event.getX(), event.getY());
				
				b3.checkClick(event.getX(), event.getY());
				
				b4.checkClick(event.getX(), event.getY());
			}
			
			
		}
		return false;
	}
	
	
}
