package project;

//This is the class used within the path finding algorithm. My tile classes inherit from this class
public class Node {
	
	int f_cost = 0;
	int h_cost = 0;
	int g_cost = 0;
	int pathCost = 0;
	int inX = 0;
	int inY = 0;
	boolean traversable = true;
	Node parent = null;
	
	//constructor
	public Node(int Ix, int Iy, boolean t){
		inX =  Ix;
		inY = Iy;
		traversable = t;
	}
	
	//clears the contents of the variables which will be used in another run of the path finding algorithm
	public void clear(){
		f_cost = 0;
		h_cost = 0;
		g_cost = 0;
		pathCost = 0;
		parent = null;
	}

}
