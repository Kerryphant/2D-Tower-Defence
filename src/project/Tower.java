package project;

import java.awt.Image;

public class Tower extends Placeable{
	int tempDistance = 0;
	int tempMax = 0;
	int tempIndex = 0;
	int tempX = 0;
	int tempY = 0;
	boolean targetFound = false;
	Unit target = null;
    
	//constructor
    public Tower(int Ix, int Iy, Image i){
    	super(Ix, Iy, i);
    	
    }

	//a method which checks the area around the tower, will call the shoot function on the nearest unit within its area
    public void checkArea(Unit[] u){
    	target = null;
    	tempMax = 0;
    	tempDistance = 0;
    	tempIndex = 0;
    	tempX = 0;
    	tempY = 0;
    	
    	for(int unit = 0;unit<u.length;unit++){//for each unit

    		if(u[unit] != null){//if the unit we are trying to access isn't null
    			
    			//work out the distance to that object (pythagoras)
    			tempX = x - u[unit].x;
    			tempY = y - u[unit].y;
    			tempDistance = (int)Math.round((Math.sqrt(Math.pow(tempX, 2)+Math.pow(tempY, 2))));
    			
    			//if the distance is less than 150
    			if(tempDistance < 150){
    				
    				//set target found to true so the shoot function is called
    				targetFound = true;
    				
    				//if the distance is greater than the max distance
	    			if (tempDistance > tempMax){
	        			tempMax = tempDistance; //set this as the new max
	        			tempIndex = unit; //take note of this unit's index
	        		}
	    			
	    			
    			}
    			
    			
    		}	
    	}
    	
    	//if we have a target
    	if(targetFound){
    		//assign the target to a variable
    		target = u[tempIndex];
    		if(target != null){//if the target isn't null
    			shoot();//use the shoot function
    		}
    	}
    	
    	
    }
    
    //"shoots" the enemy. Removes 1 HP from a target unit
    public void shoot(){
    	
    	if(target != null){
    		target.health--;
    	}
    	
    }
    
    
}
