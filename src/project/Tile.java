package project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

//all tiles inherit from this class
public class Tile extends Node{
	
    int x = 0;
    int y = 0;
    int w = 0;
    int h = 0;
    Image i = null;
	Rectangle r = null;
    
    //constructor
    public Tile(int Ix, int Iy, boolean t, int X, int Y, int W, int H, Image I){
    	super(Ix, Iy, t);
    	
    	x = X;
    	y = Y;
    	w = W;
    	h = H;
    	i = I;
    	
    	r = new Rectangle(x, y, w, h);
    }
	
    //This function will check if the coords passed in are within the tile
	public boolean checkClick(int clickX, int clickY){	
		if(this.r.contains(clickX, clickY) == true){
			System.out.println("Tile clicked");
			return true;
		}else{
			return false;
		}
		
	}
    
	//a function to draw the tiles to the screen
	public void drawTile(Graphics g){	
		g.drawImage(i, x, y, 70, 70, null);
	}
	
}
