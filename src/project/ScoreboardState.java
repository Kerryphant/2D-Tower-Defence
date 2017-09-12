package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;

//The score board state will simply draw the top 10 high scores and the names to the screen
public class ScoreboardState implements IState{
	
	int resW =  Control.width;
	int resH = Control.height;
	LoadImage imageLoader = LoadImage.get();
	Image scoreBackground = null;
	File f = new File("Scoreboard/NameFile.txt");
	FileHandler fHandle = new FileHandler(f);
	String[] names = null;
	int[] scores = null; 
	Button b1 = null;
	
	//constructor
	public ScoreboardState(){
		
		//Get the loaded image for the scoreBackground using the imageLoader class
		scoreBackground = imageLoader.getImage("Images\\ScoreScreen.png");	
		
		//call the sort function from the file handler class
		fHandle.sortFile();
		
		//use the file handler functions to return arrays of the top 10 names and scores
		names = fHandle.getTopNames();
		scores = fHandle.getTopScores();
		
		//create a button, the last parameter is the code for the lambda function
        b1 = new Button(0, 800, 300, 100, imageLoader.getImage("Images\\Back.png"), 
				() -> {
					Control.get().setState(1);//when the lambda function is called, set the state to the menu state
				}
			);	
		
	}
	
	//does nothing as there is nothing to update in the scoreboard state
	//is required as this class implements the IState interface
	public boolean update() {
		
		return false;
	}


	//draws everything to the screen
	public boolean render(Graphics g) {
		//make grey background
		g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0, resW, resH);
        
        //draw the background image
        g.drawImage(scoreBackground, 0, 0, null);
		
        //set the font
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        //set the font colour
		g.setColor(Color.cyan);
        
        //if the first element of the array is not null (lets us know if the array has been populated yet)
        if(names[1] != null){
        	//for each of the high scores
	        for(int n = 0;n<10;n++){
	        	//draw the name to the screen
	            g.drawString(names[n], 550, 130 + (n * 70));
	            //draw the score to the screen
	            g.drawString(String.valueOf(scores[n]), 750, 130  + (n * 70));
	        }
        }
		
        //draw the button to the screen
        b1.drawButton(0, 800, g);
		
		return false;
	}

	//Mouse listener function 
	public boolean mouse(MouseEvent event) {
		
		//if the event was a left mouse click
		if(event.getButton() == MouseEvent.BUTTON1){
			//check if the click was on the button
			//if this returns true, the button will execute it's lambda function
			b1.checkClick(event.getX(), event.getY());
		}
		
		return false;
	}
	
}
