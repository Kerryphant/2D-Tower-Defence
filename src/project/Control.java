 package project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

//The control class will be used to create the window and run the game.
//Another important role is to pass the required graphics object to the state classes
public class Control extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	static BufferStrategy buffer = null;
    Graphics graphic = null;
    MenuState menu = null;
    ScoreboardState scoreboard = null;
    GameState game = null;
    IState state = null;
    static int width = 0;
	static int height = 0;
	static Image img = null;
	Thread thread = null;
    boolean setMenu = false;
    
     private static Control controlObj = null;
	
     //returns an instance of this class(if we don't have the needed w and h values)
     public static Control get() {
		 return get(0,0);
	}
    
     //returns instance of this class
     public static Control get(int w, int h) {
    	 if(controlObj == null)
    	 {
    		 controlObj = new Control(w, h);
    	 }
    	 
    	 return controlObj;
	}
     
    //Constructor
	private Control(int w, int h){
		width = w;
		height = h;
		
        this.setBounds(0,0,width,height);
        this.setBackground(Color.PINK);
        this.setVisible(true);
        
	}
	
	//Starts the thread for the game (we use this for setting frame speed)
	public void start(){
		if (thread==null){
            thread = new Thread(this);
            thread.start();
		}
	}
	
	
	//Runs the game
	public void run(){
		
		MouseHandler handler = new MouseHandler(this); //create mouse handler object
		this.addMouseListener(handler);	//add the mouse handler to this object (the entire screen really)
			
		// These are required so that we have buffers/graphics to draw to
		this.createBufferStrategy(2);
        Control.buffer = this.getBufferStrategy();
        
        //this gets us a fresh graphic to draw to since we have a new buffer
        graphic = buffer.getDrawGraphics();
        
        menu = new MenuState();

        
        
        setState(1); //load main menu state
  

        while(true)
        {
        	//take note of the time this iteration of the loop started
        	long beginTime = System.currentTimeMillis();
        	
        	buffer = this.getBufferStrategy();
        	graphic = buffer.getDrawGraphics();
        	
        	//run the update method for whichever state we are currently in
        	state.update();
        	state.render(graphic);
            
        	if(!Control.buffer.contentsLost()){
        		buffer.show();
            }
            
            // this is for cleaning up our rendering. we do this so that any previous images that were drawn are not reused in case they were moved
            // as will be expected in the game
            if(graphic != null){
                graphic.dispose();
            }
            
          //threads may not be running smoothly as other threads could have a higher priority and therefore certain frames may last longer/shorter than others
          //this is to maintain a constant minimum, don't want things to speed up unexpectedly. Things within game connected to the frame speed, faster frames means things move faster within game
            long timeTaken = System.currentTimeMillis();
            long sleepTime = 50 - (timeTaken - beginTime);
            try{
                thread.sleep(sleepTime);
            }catch(Exception e){
            }

        }

	}
	
	public void setState(int s){
		System.out.println("The value is:" + s);
		
		
		
		if (s == 1){//Set the state to the menu state
			state = menu;
		    setMenu = true;    
		}
		else if (s == 2){//set the state to the scoreboard state
			scoreboard = new ScoreboardState();
			state = scoreboard;
		}
		else if (s == 3){//set the state to the game state
			game = new GameState();
			state = game;
		}
		else if (s == 4){//exit the game (done here as this is the best object to carry out this command)
			 System.exit(0);
		}
		else{
			//This should never be run but maybe add in a method here that will exit the program or something. Makes it robust and all that jazz
			System.out.println("Valid state not given");
		}
	}
	
	
	
	private class MouseHandler implements MouseListener{
		
		Control cont;
		
		public MouseHandler(Control c){
			cont = c;
		}
		
		//Mouse listener methods
		//if the mouse is clicked (pressed down and released quickly) do this:
		public void mouseClicked(MouseEvent event){
			cont.state.mouse(event);			
		}

		
		//following methods had to be present as we implement MouseListener
		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
		
		
		
		
	}	
}
