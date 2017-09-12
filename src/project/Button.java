package project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

//This is the button class, does as it says on the tin
public class Button {

	///creates the variables we need and sets them to null
	Rectangle r = null;
	Graphics graphic = null;
    Image img = null;
    IButtonClick click = null;
    
    //Constructor
    public Button(int x, int y, int w, int h, Image i, IButtonClick c){
    	r = new Rectangle(x, y, w, h); //creates a rectangle we will use to check clicks on the button
    	img = i; //A pre-loaded image is passed in and used when we draw the button to the screen
    	
    	click = c;//instance of my IButtonClick which will allow me to use lambda/anonymous functions
    }
    
    //This function will check the coords that were passed in to see if they are within the button
	public boolean checkClick(int clickX, int clickY){
		
		if(this.r.contains(clickX, clickY) == true){//if this rectangle contains these coords
			
			System.out.println("Button clicked"); //print to the console
			click.execute();//execute the lambda function
			return true;//return true so we know that the button was clicked
			
		}else{//else (if it wasnt clicked)
			
			System.out.println("Button not clicked");//print to the console
			return false;//return false so we know the button wasnt clicked
		}
		
	}
	 
	//this is the function that draws the button to the screen. Makes use of a graphics object which is passed as a parameter
	public void drawButton(int x, int y, Graphics g){	
		g.drawImage(img, x, y, null);//calls the predefined drawImage function
	}
	
}
