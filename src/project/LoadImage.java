package project;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/*This class will be used to store loaded images within a hash map. Other classes will be able to call
 * a method which will check if the requested image (pass in path) is already in the hash map. If it is,
 * the loaded image will be returned. If it is not, then the LoadImage class will load the image and add it to the hash
 * map before returning the loaded image to the point of call. 
 */

public class LoadImage {
	
	/*Keys map to sprite. Hash is same as array only use a ".get" method and pass the key to access data.  
	 *Using this means we can easily order the images and index by a unique value.
	 *Array would make it harder as you would need to know the index value where each image is stored when you need to access a loaded image.
	 */
	 private HashMap<String, Image> Images = new HashMap<>();
	 private Image img = null;
	 
	 
	//Only the LoadImage class can create an instance of this class. Doing this as we only ever want one instance.
	private LoadImage(){
		
	}
	
	static LoadImage original = new LoadImage(); //Create an instance
	
	//This method will be used to ensure only one instance of this class is created.
	public static LoadImage get(){
        return original; //Return the instance above
    }
	
	//Returns a loaded image
	public Image getImage(String p){

		//if the requested image is within the hash map
		if(Images.get(p) != null){
			return Images.get(p);//return it
		}
		else{
			
			try{
				Images.put(p, loadImage(p)); //load the requested image and place it in the hashmap
			}
			catch(Exception e) {
				System.out.println("Unable to load image from " +p);
			}
			
			return Images.get(p); //return the newly added image
		}
			
	}
	
	public Image loadImage(String p){
		
		String path = p;
        BufferedImage sourceImage = null;

        
        try{
        	// this reads the image from the folder into a data structure in memory for use by the program.
            sourceImage = ImageIO.read(new File(path));

        }catch(IOException e){
            System.out.println("image unable to load: "+ path);
        }

        //initialise the final data structure that will be used by the program
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        img = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

        // we draw, or copy as it could be seen as, the sourceImage to the final image we will use in the program.
        img.getGraphics().drawImage(sourceImage,0,0,null);
        
        //return the image
        return img;
	}
	 
}
