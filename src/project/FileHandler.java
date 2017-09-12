package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

//A class to handle reading and writing to files 
public class FileHandler{
	
    File file = null;
    File sFile = new File("Scoreboard/ScoreFile.txt");
    Scanner scanner = null;
    Scanner scannerSc = null;
    ArrayList<String> nameA = new ArrayList<String>();
    ArrayList<Integer> scoreA = new ArrayList<Integer>();
	String tmpN = null;
	String tmpS = null;	
	int tmpSc = 0;	
	String[] topNames = new String[10];	
	int[] topScores = new int[10];
	boolean moreNames = true;
	boolean moreScores = true;  
	Scanner scannerNewN = null;
	Scanner scannerNewS = null;
	
	//Constructor
    public FileHandler(File f){
    	
        try{
        	file = f;//set "file" to the file that was passed in
            scanner = new Scanner(file);//create a scanner with that file
            scannerSc = new Scanner(sFile);//create a scanner for the score file
        
        }catch(Exception e){}
        
    }
    

    //reads the next string from a file and returns it
    public String getNextString(){
    	String returnString = null;
        
    	if(scanner.hasNext()){//if there is another line in the file
            returnString = scanner.next(); //assign the value to this variable
            return returnString;//return the string
        }
        else{
        	return null;//if there is nothing to return, return null.
        }
        
    }
    
    //does the same as above but for the score scanner instead
    public String getNextScore(){
    	
    	String returnString = null;
    	
            if(scannerSc.hasNext()){
                returnString = scannerSc.next();
                return returnString;
            }
            else{
            	return null;
            }
        }
    
    
    //writes the string that was passed to the file
    public void addName(String in){

        try{
        	//create the buffered writer (lets us write to files, open for appending)
            BufferedWriter nWriter = new BufferedWriter(new FileWriter("Scoreboard/NameFile.txt", true));
            nWriter.write(in);//write the value
            nWriter.newLine();//take a new line
            nWriter.close();//close the file
        }
        catch (Exception e){}
	}
    
    //writes the score to the file as a string
    public void addScore(int in){
    	
    	System.out.println("handler sending " + in);
    	
    	String input = Integer.toString(in);
    	
    	try{
    		//create the buffered writer (open for appending)
			 BufferedWriter sWriter = new BufferedWriter(new FileWriter("Scoreboard/ScoreFile.txt", true));
	            sWriter.write(input);//write the value
	            sWriter.newLine();//take a new line
	            sWriter.close(); //close the file
	            
	            System.out.println("handler finished");
    	}
    	 catch (Exception e){
    		 System.out.println("there was an error lol");
    	 }

	}
    
    //sorts the contents of the files and rewrites them
    public void sortFile(){
    	
		int temp = 0;
		String tempS = null;
		
		while(moreNames && moreScores){
		 
			//get the next lines from the files and assign them to variables
			tmpS =  getNextScore();
			tmpN = getNextString();
			 
			//if the input for names wasn't null
			 if(tmpN != null){
	     		nameA.add(tmpN);//add the input to the names array list
			 }
			 else{
				 moreNames = false; //else, exit the loop
			 }
			 
			 //if the input for scores wasn't null
			 if(tmpS != null){
	     		tmpSc = Integer.parseInt(tmpS); //change from type string to integer
	     		scoreA.add(tmpSc); //add the input to the scores array list
			 }
			 else{
				 moreScores = false; //else, exit the loop
			 }
			 
			 
	     }
		 
		//create an array of type integer which is the size of the array list
		//this is done to allow comparisons for a sort
		 int[] scores = new int[scoreA.size()];
	
		 for(int i = 0; i < scoreA.size(); i++) {
		     if (scoreA.get(i) != null) {
		         scores[i] = scoreA.get(i);
		     }
		 }

		 
		 for(int outer = scores.length - 1; outer >-1 ; outer--){
	     	  for (int inner = 0 ; inner < outer ; inner++){
	     		  if(scores[inner] < scores[inner+1]){ //if the current score is less than the next score
	     			  
	     			  //swap the scores
	     			  temp = scores[inner];
	     			  scores[inner] = scores[inner + 1];
	     			  scores[inner + 1] = temp;
	     			  
	     			  //swap the corresponding names
	     			  tempS = nameA.get(inner);
	    			  nameA.set(inner, nameA.get(inner+1));
	    			  nameA.set(inner + 1, tempS);
	    			  
	     		  }
	     	  }
	       }
		 	
		 
		//the arrays have now been sorted so we can now write to the files
		 
		 try{
			//create the buffered writer (not open for appending, this means the file is wiped empty and we write it from fresh)
	         BufferedWriter sWriter = new BufferedWriter(new FileWriter("Scoreboard/ScoreFile.txt", false));
	         
	        for(int n = 0;n<scores.length;n++){  //for each of the scores 
	            sWriter.write(""+scores[n]);//write the value
	            sWriter.newLine();//take a new line
	        }
	        
	        sWriter.close(); //close the file
	        
	    }catch(Exception e){
	        System.err.println("score sorting writing failed: " + e.getMessage());
	    }
	         
		 
		 
	     try{
	    	//create the buffered writer (not open for appending, this means the file is wiped empty and we write it from fresh)
	         BufferedWriter nWriter = new BufferedWriter(new FileWriter("Scoreboard/NameFile.txt", false));
	         
	         for(int n = 0;n<nameA.size();n++){ //for each of the names
	                    nWriter.write(""+nameA.get(n));//write the value
	                    nWriter.newLine();//take a new line
	 
	        }
	         
	         nWriter.close();//close the file
	         
	     }catch(Exception e){
	         System.err.println("name sorting writing failed: " + e.getMessage());
	     }
	     
		 System.out.println("Sorting finished");

    }

    
    //returns the top 10 scores in the file as an array
	public int[] getTopScores(){
		
		try {
			//create a scanner for the score file
			scannerNewS = new Scanner(sFile);
		} catch (FileNotFoundException e) {}
		
		
		for(int n = 0;n<10;n++){//from 0 to 10
			//if the scanner has another line to input
			if(scannerNewS.hasNext()){
				//store the value in a variable
				String temP  = scannerNewS.next(); 
				//assign the value to the array, changing the type from string to integer
                topScores[n] = Integer.parseInt(temP); 
            }		
		}
		//return the array
		return topScores;
	}
	
	//returns the top 10 names in the file as an array
	public String[] getTopNames(){
		
		String temp = null;
		
		try {
			//create a scanner for the name file
			scannerNewN = new Scanner(file);
		} catch (FileNotFoundException e) {}
		
		
		for(int n = 0;n<10;n++){//from 0 to 10
			//if the scanner has another line to input
			if(scannerNewN.hasNext()){
				//store the value in a variable
				temp = scannerNewN.next();
				//assign the value to the array
				topNames[n] = temp;
            }		
		}
		//return the array
		return topNames;
	}
	
	
}

