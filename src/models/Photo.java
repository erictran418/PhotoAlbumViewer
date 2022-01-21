package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;

/**
 * This is the Photo Class. It is used to control and facilitate the storage of SerialImages inside it
 */

public class Photo implements Serializable {
	
	/**
	 * Serial Version ID
	 */
	
	private static final long serialVersionUID = -6710677852412514637L;
    
	
	/**
	 * An ArrayList of Tags the Photo has
	 */
	
	
	private ArrayList<Tag> tags;
	
	/**
	 * The Photo's caption
	 */
	
	private String caption;
	
	/**
	 * The Photo's date
	 */
	
	private Calendar date;
	
	/**
	 * The Photo's actual image
	 */
	
	private SerialImage img;
	
	/**
	 * Constructor of the class; Sets up the initial properties of the Photo
	 * @param img: The Actual Image of the Photo
	 * @param lastmod: the Calendar date the photo was last modified
	 */
	
	public Photo(SerialImage img, Calendar lastmod)
	{
		this.img=img;
		tags=new ArrayList<Tag>();
		caption=null;
		this.date=lastmod;
		
		
	}
	
	/**
	 * Method to get photo's caption
	 * @return the photo's caption
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Method to get photo's image
	 * @return the photo's image
	 */

	public SerialImage getSerialImage() {
		return this.img;
	}
	
	/**
	 * Method to add a tag to the photo
	 * @param t: the tag we want to add to the photo
	 */
	
	public void addTag(Tag t)
	{
		this.tags.add(t);
	}
	
	/**
	 * Method to remove a tag of the photo
	 * @param tagname: the tag we want to remove from the photo
	 */
	
	public void removeTag(Tag t)
	{
		this.tags.remove(t);
	}
	public Tag getTag(int i) {
		if(tags.isEmpty()) {
			return null;
		} else {
			
			if(tags.get(i) == null) {
				return null;
			} else {
				return tags.get(i);
			}
		}
		
	}
	/**
	 * Method to return the amount of tags of the photo
	 * @return the size of the tags ArrayList the photo possesses
	 */
	
	public int tagSize() {
		return tags.size();
	}
	
	/**
	 * Method to return the tags of the photo
	 * @return the tags ArrayList the photo possesses
	 */
	
	public  ArrayList<Tag> getTagList() {
		return tags;
	}
	
	/**
	 * Method to modify a tag's value
	 * @param tagname: the name of the existing tag we want to modify
	 * @param oldtagvalue: the value of the existing tag we want to modify
	 * @param newtagvalue: the new value of the tag we want to modify
	 */
	public boolean changeTag(String tagname, String oldtagvalue, String newtagvalue)
	{
		 Tag temp= new Tag(tagname, oldtagvalue);
         for(int i=0;i<tags.size();i++)
         {
        	 Tag tcomp=tags.get(i);
        	 
        	 if(temp.tagEquals(tcomp))
        	 {
        		 tcomp.setValue(newtagvalue);
        		 return true;
        		 
        	 }
         }
         
         return false;
	}
	
	/**
	 * Method to set the photo's caption
	 * @param newcaption: the new caption of the photo 
	 */
	
	public void setCaption(String newcaption)
	{
		caption=newcaption;
	}
	
	/**
	 * Method to get photo's date
	 * @return the photo's date
	 */
	
	public Calendar getDate()
	{
		return date;
	}
	
	/**
	 * Method to get photo's serial image
	 * @return the photo's serial image
	 */
	
	public SerialImage getImage()
	{
		return img;
	}
	
	/**
	 * Method to get photo's tags
	 * @return an ArrayList of the photo's tags
	 */
	
	
	public ArrayList<Tag> getTags()
	{
		return tags;
	}
	
	/**
	 * Method to set photo's tags
	 * @param an ArrayList of the photo's new tags
	 */
	
	public void setTags(ArrayList<Tag> tgs)
	{
		this.tags=tgs;
	}
	
	/**
	 * Method to return a representation of the photo's tags
	 * @param a String representation of the photo's tags
	 */
	
	public String tagsToString(){
		StringBuilder str = new StringBuilder();
		
		for(int i = 0; i < tags.size(); i++) {
			str.append(tags.get(i).getName());
			str.append(":");
			str.append(tags.get(i).getvalue());
			str.append(",  ");
		}
		return str.toString();
	}
	
	
	/**
	 * Method to return if 2 photos are physically the same
	 * @param the photo this one is compared to
	 * @return a boolean that says whether two photos are physically the same
	 */
	
	
	public boolean equals(Photo otherphoto)
	{
		SerialImage si=this.img;
		SerialImage othersi=otherphoto.getImage();
		
		if(SerialImage.isSameImage(si, othersi)==false)
		{
			return false;
		}
		if(this.date.equals(otherphoto.getDate())==false)
		{
			return false;
		}
		
		
		
		return true;
		
	}
	
	
	
	
	

}
