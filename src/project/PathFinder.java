package project;

import java.util.ArrayList;
import java.util.Collections;

//Finds a valid path for the units to use
public class PathFinder {

    ArrayList<Node> open = new ArrayList<Node>();
    ArrayList<Node> closed = new ArrayList<Node>();
    boolean targetNotReached = true;
    Node[][] map = null; 
    Node current= null;
    Node target = null;
    
    //sx & sy / tx & ty should be the indexes to the tile within the tiles array 
    public PathFinder(Node m[][]){

        map = m;
        
    }
    
    
    
    public Node[] findPath(int sx, int sy, int tx, int ty){  //returns a path as an array
    	target = map[tx][ty];
    	
    	open.add(map[sx][sy]); //add start node to the "open" list
    	
    	while(targetNotReached){
    		 
    		current = open.get(0);// set current node to the lowest fCost in "open" (open is sorted)
    		 open.remove(current); //remove the current node from the "open" list
    		 closed.add(current); //add the current node to the "closed" list
    		 
    		 if (current == target){
    			 System.out.println("Pathfinding complete(1)");
    			 return gatherPath(current); //if the current node is the target node then we can stop searching. 
    		 }

    		 Node neighbours[] = findNeighbours(current);
    		 
    		 
    		 for(int n = 0; n<8; n++){ // for each neighbour
    			 
    			 if (neighbours[n] != null){
    			 
	    			 if (neighbours[n].traversable == false  || closed.contains(neighbours[n])){ //if we cannot "step" onto this neighbour or if it has already been ruled out
	 		 			continue; //move onto the next neighbour
	 		 		}
	    			 
	    			 
	    			 if (open.contains(neighbours[n]) == false){ // if this node isn't in the "open" list
			 				open.add(neighbours[n]); //add it to the list
			 		}
	    			 
	    			 
	    			 if(open.contains(neighbours[n])){
	    				 //calculate new f cost
	    				 int tempF_Cost = current.pathCost + calcGCost(n) + calcHCost(neighbours[n], target);
	    			   				
	    				 if(tempF_Cost < neighbours[n].f_cost || neighbours[n].f_cost == 0){
	    					 
	    					 //update f_cost
	    					 neighbours[n].f_cost = tempF_Cost;
	    					 //update parent
	    					 neighbours[n].parent = current;
	    					 //update pathCost
	    					 neighbours[n].pathCost = current.pathCost + calcGCost(n);
	    				 }
	    				 
	    			 }
    			 }
    		 } 

    		bubbleSort();//sort the open list
    	}
    	
    	
    	System.out.println("Pathfinding complete(2)");
    	//path should be returned within an if block above, and therefore should exit the function before reaching this.
    	 return gatherPath(current);
    }
    
    //bubble sort algorithm
    public void bubbleSort(){
    	  Node nodes[] = new Node[open.size()]; //create an array of the same size as the list
    	  
          for (int i=0; i<open.size();i++){ //add the contents of the list to the array
              nodes[i] = open.get(i);
          }
    	
          for(int outer = open.size() -2 ; outer >-1 ; outer--){
        	  for (int inner = 0 ; inner <= outer ; inner++){
        		  if(nodes[inner].f_cost > nodes[inner + 1].f_cost){//if the current cost is greater than the next
        			  //swap them
        			  Node temp = nodes[inner];
        			  nodes[inner] = nodes[inner+1];
        			  nodes[inner + 1] = temp;
        		  }
        	  }
          }
          
    }

    //finds all the neighbouring nodes for the node that is passed in
    public Node[] findNeighbours(Node currentNode){

    	Node[] neighbours = new Node[8];
    	
    	//straight
    	if(currentNode.inY+1 < 10){
		neighbours[0] = map[currentNode.inX][currentNode.inY+1];}
    	
    	if(currentNode.inX+1 < 18){
		neighbours[1] = map[currentNode.inX+1][currentNode.inY];}
    	
    	if(currentNode.inX-1 > -1){
		neighbours[2] = map[currentNode.inX-1][currentNode.inY];}
    	
    	if(currentNode.inY-1 > -1){
		neighbours[3] = map[currentNode.inX][currentNode.inY-1];}
		
    	
		//diagonal
		if(currentNode.inX+1 < 18 && currentNode.inY+1 < 10){
		neighbours[4] = map[currentNode.inX+1][currentNode.inY+1];}
		
		if(currentNode.inX-1 > -1 && currentNode.inY+1 < 10){
		neighbours[5] = map[currentNode.inX-1][currentNode.inY+1];}
		
		if(currentNode.inX+1 < 18 && currentNode.inY-1 > -1){
		neighbours[6] = map[currentNode.inX+1][currentNode.inY-1];}
		
		if(currentNode.inX-1 > -1 && currentNode.inY-1 > -1){
		neighbours[7] = map[currentNode.inX-1][currentNode.inY-1];}
		
		return neighbours;
		
	}
    
    //calculates the g cost of a node
    public int calcGCost(int n){
    	
    	//using knowledge from the findNeighbours function
    	if (n < 4){
    		return 10;
    	}
    	else if (n < 8){
    		return 14;
    	}
    	else{
    		return 0;//shouldn't ever return 0
    	}
    	
    }
    
    public int calcHCost(Node c, Node t){
    	
    	//pythagoras theorem
    	int h_cost = (int)Math.round(10*(Math.sqrt(Math.pow(t.inX-c.inX, 2)+Math.pow(t.inY-c.inY, 2))));
    	
    	return h_cost;
    }
    
   //gathers the path into an array so it can be returned 
    public Node[] gatherPath(Node endNode){
    	
    	Node path[] = null;
    	
    	ArrayList<Node> list = new ArrayList<Node>();
    	 
    	Node currentNode = endNode;
    	 
    	while(currentNode.parent != null)
    	{
    	list.add(currentNode);
    	currentNode = currentNode.parent;
    	}
    	list.add(currentNode);
    	 
    	Collections.reverse(list);
    	 
    	path = new Node[list.size()];
    	path = list.toArray(path);
    	
        open.clear();
        closed.clear();
        
        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[i].length; ++j){
                map[i][j].clear();
            }
        }
        
        
        return path;
    }
	
        
}
