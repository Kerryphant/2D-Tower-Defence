package project;

import java.awt.Graphics;
import java.awt.Image;

public class Unit extends Placeable{
    
	boolean justPlaced = false;
	boolean notReached = false;
	boolean diagMove = false;
	Node target = null;
	
	Node[] path = null;
	PathFinder pFinder = null;
	int tX = 0;
	int tY = 0;
	
	boolean notReachX = false;
	boolean notReachY = false;
	boolean notReachedX = true;
	boolean notReachedY = true;
	boolean notReachedT = true;
	LoadImage imageLoader = LoadImage.get();
	
	int pathCount = 0;
	boolean hasTookHP = false;
	
	int health = 70;
	boolean hasMoved = false;
	
	
    public Unit(int sX, int sY, int eX, int eY, Image i, Node m[][]){    
    	//sx and sy will change as every time the path is updated (when a tower is placed), the sx and sy must be of the tile the unit is currently on
    	super(sX*70, sY*70, i);
    	pFinder = new PathFinder(m);
    	

    	tX = eX;
    	tY = eY;
    	
    	updatePath(x, y, tX, tY);
    	target = path[0];
    	
    }
    
    //initial path find
    public void updatePath(int cX, int cY, int tarX, int tarY){
    	//call the path finding algorithm and assign the path to a variable
    	path = pFinder.findPath(cX/70, cY/70, tarX, tarY);
    	
    }
    
    //the function used if the path finding is ever called again
    public void updatePath(int cX, int cY, int tarX, int tarY, Tile[][] m, int w, int h){
    	int newX = 0;
    	int newY = 0;
    	
    	//for each of the tiles on the map
    	for(int col = 0;col<w;col++){
            for(int row = 0;row<h;row++){
            	
            	//check the x and y coords to find out which tile the unit is on
            	//this is done as the path finding algorithm makes use of the indexes, not the raw coords
            	if(m[col][row].checkClick(cX, cY)){
            		newX = m[col][row].inX;
            		newY = m[col][row].inY;
            	}	
            	
            	
            }
        }   
    	
    	//call the path finding algorithm and assign the path to a variable
    	path = pFinder.findPath(newX, newY, tarX, tarY);
		
    	//set the new target to move towards
    	target = path[1];
    	//set pathCount to 1
    	pathCount = 1;
    	
    }
    
	
    //method used to move the unit across the screen
    public void move(){
    	 
    	hasMoved = true;
    	
    	//if we have not reached the current target
    	if(notReachedT){
    		
	    	//if the unit's x coord is equal to the target's x coord
	    	if(target.inX*70 == x){
	    		//set the x booleans to false as we have reached the target x
	    		notReachedX = false;
	    		notReachX = false;
	    	}
	
	    	//if the unit does not have the same x coords as the target's
	    	if(notReachedX){
	    		//make sure the noReachX boolean is set to true
	    		notReachX = true;
	    		//reset dir to 0
	    		int dir = 0;
	    		//calculate the dir
	    		dir = target.inX*70 - x;
	    		
	    		
	    		//if dir is >0 then we need to move in the positive direction of the x axis
	    		if(dir > 0){
	    			x++;
	    		}
	    		else{//if its negative then we need to move in the negative direction of the x axis
	    			x--;
	    		}
	    	}
	    	
	    	
	    	
	    	//if the unit's y coord is equal to the target's y coord
	    	if(target.inY*70 == y){
	    		//set the y booleans to false as we have reached the target y
	    		notReachedY = false;
	    		notReachY = false;
	    	}
	    	
	    	//if the unit does not have the same y coords as the target's
	    	if(notReachedY){
	    		//make sure the noReachY boolean is set to true
	    		notReachY = true;
	    		//reset dir to 0
	    		int dir = 0;
	    		//calculat the dir
	    		dir = target.inY*70 - y;
	    		
	    		//if dir is >0 then we need to move in the positive direction of the y axis
	    		if(dir > 0){
	    			y++;
	    		}
	    		else{//if its negative then we need to move in the negative direction of the y axis
	    			y--;
	    		}
	    	}
	    	
	    	//if BOTH x and y coords of the unit match the target
	    	if(notReachedX == false && notReachedY == false){
	    		//we have reached the target
	    		notReachedT = false;
	    	}
    	
    	
    	}
    	else{//if we have reached the target
    		
    		//if we havent been through all of the targets in the path
    		if(pathCount < path.length){
    			//set the new target
    			target  = path[pathCount++];
    		}
    		
    		//reset all of the booleans
    		notReachedT = true;
    		notReachedX = true;
    		notReachedY = true;
    		
    		
    	}
    	
    	//if we haven't reached the x coord or the y coord
    	if(notReachX && notReachY){
    		diagMove = true;//we are moving diagonally
    	}
    	
    }
    
    //checks if the unit has reached the final target and returns true or false accordingly 
    public boolean checkMove(){
    	if(tX*70 == x && tY*70 == y){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    //checks the health of the unit and returns true if it is not less than 0
    public boolean checkHealth(){
    	if(health <= 0){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
   //a method which draws the unit to the screen
   public void draw(Graphics g){	
    		g.drawImage(i, x, y + 35, null);
	}
    
}