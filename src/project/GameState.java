package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;

//The game state will run the code for the main game.
public class GameState implements IState{
  
	LoadImage imageLoader = LoadImage.get();
	Image baseBackground = null;
    Image statsBackground = null;
    Image livesBackground = null;
	Image healthImg = null;
	Image winMessage = null;
	Image lossMessage = null;
    Tile[][] tiles = new Tile[18][10];
	Tower[][] towers = new Tower[18][10];
	Unit[] wave1 = new Unit[7];
	Tile startTile = null;
	Tile endTile = null;
	Control con =  Control.get();
	int resW =  Control.width;
	int resH = Control.height;
	int mapWidth = 18;
	int mapHeight = 10;
	File f = new File("Maps/map.txt");
	File s = new File("Scoreboard/NameFile.txt");
	FileHandler fHandle = new FileHandler(f);
	FileHandler sHandle = new FileHandler(s);
	int health = 4;
	int gold = 100;
	int kills = 0;
	int score = 0;
	int currentScore = 0;
	int numberT = 0;
	int createTimer = 0;
	int unitCounter = 0;
	int unitCounter2 = 0;
	String Sgold = null;
	boolean tDrawn = false;
	boolean findingComplete = false;
	boolean unitMade = false;
	boolean result = false;
	boolean displayMessage = false;
	boolean ending = false;
	Button b1 = null;
	
	//constructor
	public GameState(){

		//loading all the images that I will be repeatedly drawing to the screen (helps reduce the demand on resources)
		baseBackground = imageLoader.getImage("Images\\BaseBackground.png");
	    statsBackground = imageLoader.getImage("Images\\StatsBackground.png");
	    livesBackground = imageLoader.getImage("Images\\LivesBackground.png");
	    healthImg = imageLoader.getImage("Images\\Health.png");
	    winMessage = imageLoader.getImage("Images\\Victory.png");
		lossMessage = imageLoader.getImage("Images\\loss.png");
		
		for(int row = 0;row<mapHeight;row++){//for the height of the map
            for(int col = 0;col<mapWidth;col++){//for the width of the map
            	
            	//hold the character that was just passed in (is overwritten each loop)
                String tmpS = fHandle.getNextString();
                
                if (tmpS.equals("f")){//if the character is an f
                    tiles[col][row] = new TileFloor(col, row, true, col*70, row*70, 70, 70, imageLoader.getImage("Images\\Grass.png")); //create a floor object and place it in the array
                    System.out.println("New floor");
                }else if (tmpS.equals("w")){//if the character is a w
                    tiles[col][row] = new TileWall(col, row, false, col*70, row*70, 70, 70, imageLoader.getImage("Images\\Wall.png")); //create a wall object ....
                    System.out.println("New wall");
                }else if (tmpS.equals("s")){//if the character is an s
                    tiles[col][row] = new TileStart(col, row, true, col*70, row*70, 70, 70, imageLoader.getImage("Images\\TileStart.png")); //create a start object ....
                    startTile = tiles[col][row];
                    System.out.println("New start");
                }else if (tmpS.equals("e")){//if the character is an e
                    tiles[col][row] = new TileEnd(col, row, true, col*70, row*70, 70, 70, imageLoader.getImage("Images\\End.png")); //create an end .....
                    endTile = tiles[col][row];
                }else{//if its none of these characters
                	System.out.println("Incorrect character at " + col + " " + row);//display an error with the needed indexes
                }
                
                
            }
            
        }
		
		//create a new button (the back button). The last parameter is the code for the lambda function
		b1 = new Button(0, 800, 300, 100, imageLoader.getImage("Images\\Back.png"), 
				() -> {
					Control.get().setState(1);//when the lambda function is called, set the state to the menu state
				}
			);	
		
		
		
	}

	//this is run every frame by the control class
	public boolean update(){
		
		if(gold < 1000 && displayMessage == false){//if the gold is less than the max
			gold++;//add gold
		}
		
		if(health == 0 && !ending){//if you run out of lives
			result = false;
			endGame();//end the game
		}
		
		if(unitMade && unitCounter2 == 0 && !ending){ //if there are no units left
			result = true;
			endGame();//end the game
		}
		
		//Creating Units
		//I use a "timer" here to avoid all units spawning on top of each other
		if(createTimer < 30){//if timer is less than 30
			createTimer++;//increase value of timer
		}
		//if we have made it to 30 and the number of created units is less than the size of the array
		else if (unitCounter < wave1.length){
			//Set the boolean to true
			unitMade = true;
			//create a new unit
			wave1[unitCounter] = new Unit(startTile.inX, startTile.inY, endTile.inX, endTile.inY, imageLoader.getImage("Images\\Unit.jpg"), tiles);
			//increase the unit counter
			unitCounter++;
			//increase the second unit counter
			unitCounter2++;
			//reset the timer
			createTimer = 0;
		}
		
		//Moving Units
		for(int unit = 0;unit<7;unit++){
			
			//if the current unit isn't null, has not reached the end, and still has health
            if(wave1[unit] != null && wave1[unit].checkMove() && wave1[unit].checkHealth()){
            	wave1[unit].move();//move the unit
            }
            
            //if the unit isn't null, has no health left, and has moved at least once
            if(wave1[unit] != null && wave1[unit].checkHealth() == false && wave1[unit].hasMoved){
            	//increase the number of kills
            	kills++;
            	//increase the score
            	score = score + 100;
            	//set this unit to null so it will be ignored in future
            	wave1[unit] = null;
            	//update number of units
            	unitCounter2--;
            }
        }		
		
		
		//Checking tower area (in order to then shoot)
		if(tDrawn){
			for(int col = 0;col<mapWidth;col++){//for the width of the map
                for(int row = 0;row<mapHeight;row++){//for the height of the map
                	
                	//if the tower is not null and the units have all have a path
                    if(towers[col][row] != null && findingComplete){
                    	towers[col][row].checkArea(wave1);//check the area around the tower
                    }
                    
                }
            }   
        	
		}
		
		//update the player score
		currentScore = calcScore();
		
	
		return false;
	}

	//This is run every frame by the control class. Is used to draw the screen
	public boolean render(Graphics g) {
		
		//draw a new background
		g.setColor(Color.pink);
        g.fillRect(0,0, resW, resH);
        
        //draw HUD backgrounds
        g.drawImage(baseBackground, 0, 0, null);
        g.drawImage(statsBackground, 1260, 0, null);
        g.drawImage(livesBackground, 370, 800, null);
      
        //draw HUD items  
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.black);
        g.drawString(String.valueOf(currentScore), 1300, 150);
        g.drawString(String.valueOf(gold), 1300, 300);
        g.drawString(String.valueOf(kills), 1300, 430);
        
        for(int heart = 0;heart<health;heart++){
        	g.drawImage(healthImg, 500 + (heart*40), 833, null);
        }
        
        b1.drawButton(0, 800, g);
          
        
        //Draw map
        for(int col = 0;col<mapWidth;col++){
            for(int row = 0;row<mapHeight;row++){
                tiles[col][row].drawTile(g);
            }
        }   
        
        //Draws towers
        if(tDrawn){
        	for(int col = 0;col<mapWidth;col++){
                for(int row = 0;row<mapHeight;row++){
                    if(towers[col][row] != null){
                    	towers[col][row].drawScale(g, 70, 70);
                    }
                    
                }
            }   
        	
        }
        
        
        if(displayMessage == false){
	        //Draws units
	        for(int unit = 0;unit<7;unit++){//for each unit
	        	
	        	//if the current unit isn't null, has not reached the end target, and still has some health
	            if(wave1[unit] != null && wave1[unit].checkMove() && wave1[unit].checkHealth()){
	            	wave1[unit].draw(g); //draw it to the screen
	            }
	            
	            //if this unit is not null, has reached the end, and has yet to take away health from the player
	            if(wave1[unit] != null && wave1[unit].checkMove() == false && wave1[unit].hasTookHP == false){
	            	health--;//take away 1 life 
	            	wave1[unit].hasTookHP = true;//set the boolean to true
	            	score = score - 50;//remove some score
	            	unitCounter2 --; //decrease the number of active units
	            }
	        }
        }
        
        
        if(displayMessage && result){
        	g.drawImage(winMessage, 530, 250 , null);
        	g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            g.setColor(Color.black);
        	g.drawString(String.valueOf(currentScore), 600, 388);
        }
        else if(displayMessage && result == false){
        	g.drawImage(lossMessage, 530, 250 , null);
        }
        
       
        
		return false;
	}
	
	
	public void endGame(){
		
		ending = true;
		System.out.println("Ending game");
		displayMessage = true;
		//add the player's score to the score board
		//make the "name" the date/time of the score
		sHandle.addName("Player" + System.currentTimeMillis()/3600);
		sHandle.addScore(currentScore);	
		
	}
	
	//calculates the current score
	public int calcScore(){
		int tempKills = 0;

		//this is to prevent multiplication by 0
		if (kills == 0){
			tempKills = 1; 
		}
		else{
			tempKills = kills;
		}
		
		
		int tempScore = (score + gold) * (tempKills * 10); 
		return tempScore;
		
	}
	
	//This is the mouse listener method. Allows us to carry out an action when the mouse is clicked
	public boolean mouse(MouseEvent event) {
		boolean clicked = false;
		int tempCol = 0;
		int tempRow = 0;
		

		//if left click
		if(event.getButton() == MouseEvent.BUTTON1 && displayMessage){
			displayMessage = false;
			Control.get().setState(1);
		}
		else if(event.getButton() == MouseEvent.BUTTON1){
			//check if the button was pressed
			b1.checkClick(event.getX(), event.getY());
			
			
			//check if one of the tiles were clicked. If yes, record the click
			for(int col = 0;col<mapWidth;col++){
	            for(int row = 0;row<mapHeight;row++){
	            	
	            	if(tiles[col][row].checkClick(event.getX(), event.getY())){
	            		clicked = true;
	            		tempCol = col;
	            		tempRow = row;
	            	}	
	            	
	            	
	            }
	        }   
			
			
			//if a tile was clicked
			if(clicked){
        		System.out.println("Tile " + tempCol + " "+  tempRow + " was clicked");	
        		
        		//check if this is a valid place for a tower to be placed
	        	if (numberT < 10 && tiles[tempCol][tempRow].traversable && gold >= 300 && tiles[tempCol][tempRow] != tiles[1][8] && tiles[tempCol][tempRow] != tiles[2][7] && tiles[tempCol][tempRow] != tiles[3][6] && tiles[tempCol][tempRow] != tiles[16][1] && tiles[tempCol][tempRow] != tiles[15][2] && tiles[tempCol][tempRow] != tiles[14][3] && tiles[tempCol][tempRow] != startTile && tiles[tempCol][tempRow] != endTile){      			
	        		
	        		//increase the tower count
	        		numberT++;	
	        		//take the cost of the tower from gold
	        		gold = gold - 300;
	        		//set the tile to non-traversable (for path finding)
	        		tiles[tempCol][tempRow].traversable = false;
	        		//Ensure the game knows we have now drawn at least one tower
	        		tDrawn = true;
	        		//create the tower on the chosen tile
	        		towers[tempCol][tempRow] = new Tower(tempCol*70, tempRow*70, imageLoader.getImage("Images\\Tower.png"));
	        		//draw it to the screen
	        		towers[tempCol][tempRow].draw(con.graphic);
	        		//increase the score by 50
	        		score = score + 50;
	        			
	        		//this loop will update the path of each of the units (ensures they dont walk over towers)
	        		for(int unit = 0;unit<7;unit++){
	        	          if(wave1[unit] != null){
	        	            wave1[unit].updatePath(wave1[unit].x, wave1[unit].y, endTile.inX, endTile.inY, tiles, mapWidth, mapHeight);
	        	            findingComplete = true;
	        	          }
	        		}
	        		
	        		
	        	}	
        	}
			else{
        		System.out.println("No tile clicked");
        	}
			
			
		}
		
		return false;
	}
	
}
