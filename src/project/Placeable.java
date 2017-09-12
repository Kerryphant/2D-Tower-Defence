package project;

import java.awt.Graphics;
import java.awt.Image;

//this class is should be used to "place" units and towers onto the map
public class Placeable {

	int x = 0;
	int y = 0;
	Image i = null;
	
	//Constructor
	public Placeable(int Ix, int Iy, Image img){	
		x = Ix;
		y = Iy;
		i = img;	
	}
	
	//draw to a scale
	public void drawScale(Graphics g, int scale1, int scale2){	
		g.drawImage(i, x, y, scale1, scale2, null);
	}
	
	//draw at the size of the image assigned to the object
	public void draw(Graphics g){	
		g.drawImage(i, x, y, null);
	}
	
}