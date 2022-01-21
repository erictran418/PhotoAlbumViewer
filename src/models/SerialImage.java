package models;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * This is the Serial Image Class. It is used to control and facilitate the storage of images inside a Photo object
 */

public class SerialImage implements Serializable {
	
	/**
	 * Serial Version ID
	 */
	
	private static final long serialVersionUID = -8496395123727654380L;
	
	/**
	 * The height of the photo
	 */
	
	
	
	private int vertical;
	
	/**
	 * The width of the photo
	 */
	
	private int horizontal;
	
	/**
	 * The pixels of the photo in an integer 2D array
	 */
	
	private int[][] pixelstorage;
	
	
	/**
	 * Constructor of the class; Sets up the initial properties of the Serial Image
	 * @param I: The Actual Image of the Photo
	 */
	
	
	public SerialImage(Image I)
	{
		vertical=(int) (I.getHeight());
	    horizontal=(int) (I.getWidth());
	    pixelstorage= new int[horizontal][vertical];
	    PixelReader p = I.getPixelReader();
	    for(int i=0;i<horizontal;i++)
	    {
	    	for(int j=0;j<vertical;j++)
	    	{
	    		pixelstorage[i][j]=p.getArgb(i, j);
	    	}
	    }
	}
	
	/**
	 * A method to get the Image of the SerialImage class
	 * @return Image of the Photo
	 */
	
	public Image getImage()
	{
		WritableImage retval=new WritableImage(horizontal, vertical);
		PixelWriter w = retval.getPixelWriter();
		for (int i = 0; i < horizontal; i++)
		{
			for (int j = 0; j < vertical; j++)
			{
				w.setArgb(i, j, pixelstorage[i][j]);
			}
		}
		
		
				
		return retval;
	}
	
	
	
	/**
	 * A method to determine if 2 SerialImages are equal
	 * @param image1: the first image to be compared
	 * @param image2: the second image to be compared
	 * @return a boolean that tells us whether 2 serial images are the same
	 */
	
	
	public static boolean isSameImage(SerialImage image1, SerialImage image2)
	{
		boolean retval=true;
		
		if(image1.getHeight()!=image2.getHeight() || image1.getWidth()!=image2.getWidth())
		{
			return false;
		}
		
		int w=image1.getWidth();
		int h=image1.getHeight();
		
		//compare their pixels
		int [][] p1=image1.getStorage();
		int [][] p2=image2.getStorage();
		
		
		for(int i=0;i<w;i++)
		{
			for(int j=0;j<h;j++)
			{
				if(p1[i][j]!=p2[i][j])
				{
					retval=false;
					break;
				}
			}
		}
		
		
		
		return retval;
		
		
		
	}
	
	/**
	 * A method to get the pixels of the Serial Image
	 * @return a 2D array that contains the Pixels of the Serial Image
	 */
	public int[][] getStorage()
	{
		return pixelstorage;
	}
	
	/**
	 * A method to get the width of the Serial Image
	 * @return the width of the Serial Image
	 */
	
	public int getWidth()
	{
		return horizontal;
	}
	
	/**
	 * A method to get the height of the Serial Image
	 * @return the height of the Serial Image
	 */
	
	
	public int getHeight()
	{
		return vertical;
	}
	
	
	
	
	
	
	
	
	
	

}
