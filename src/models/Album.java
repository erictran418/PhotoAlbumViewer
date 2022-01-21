package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This is the Album Class. It is used to control and facilitate the storage of Photos inside it
 */

public class Album implements Serializable {
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 6943570982761652759L;
    
	
	/**
	 * Name of the Album; A String Value
	 */
	
	
	private String name;
	
	/**
	 * An ArrayList of Photos the Album Stores
	 */
	
	
	private ArrayList<Photo> photos;
	
	/**
	 * A Calendar Object telling us the date of the earliest photo in the album
	 */
	
	private Calendar earliest;
	
	/**
	 * A Calendar Object telling us the date of the latest photo in the album
	 */
	
	private Calendar latest;
	
	/**
	 * Constructor of the class; Sets up the initial properties of the Album
	 * @param name: A String name of the created Album
	 */
	
	public Album(String name)
	{
		photos=new ArrayList<Photo>();
		earliest=null;
		latest=null;
		this.name=name;
	}
	
	/**
	 * A method to add a photo to the album's ArrayList of Photos
	 * @param p: A Photo object
	 */
	
	public void addPhoto(Photo p)
	{
		photos.add(p);
		
		if(earliest==null && latest==null)
		{
			this.earliest=p.getDate();
			this.latest=p.getDate();
			return;
		}
		
		
		
		if(p.getDate().before(earliest))
		{
			earliest=p.getDate();
		}
		
		if(p.getDate().after(latest))
		{
			latest=p.getDate();
		}
	}
	
	/**
	 * A method to add a photo to the album's ArrayList of Photos
	 * @param i: an integer representing the desired index in the Album's Photos ArrayList
	 * @return the Photo at index i of the ArrayList
	 */
	
	public Photo getPhoto(int i) {
		if(photos.isEmpty()) {
			return null;
		} else {
			
			if(photos.get(i) == null) {
				return null;
			} else {
				return photos.get(i);
			}
		}
		
		
	}
	
	/**
	 * A method to delete a specified photo from the album's ArrayList of Photos
	 * @param p: the Photo object to be deleted from the Album
	 */
	
	public void deletePhoto(Photo p)
	{
		
		for(int i=0;i<this.photos.size();i++)
		{
			Photo check=this.photos.get(i);
			if(check.equals(p))
			{
				this.photos.remove(i);
				break;
			}
		}
		
		Calendar min=Calendar.getInstance();
		Calendar max=Calendar.getInstance();
		if(this.photos.size()>0)
		{
			min=this.photos.get(0).getDate();
			max=this.photos.get(0).getDate();
			
			for(int i=0;i<this.photos.size();i++)
			{
				Calendar checkdate=this.photos.get(i).getDate();
				if(checkdate.before(min))
				{
					this.earliest=checkdate;
				}
				if(checkdate.after(max))
				{
					this.latest=checkdate;
				}
			}
			
		}
		else
		{
			return;
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	/**
	 * A method to set the name of the Album object
	 * @param newname: a String which is the new name of the Album
	 */
	
	public void setAlbName(String newname)
	{
		this.name=newname;
	}
	
	/**
	 * A method to get the name of the Album object
	 * @return a String which is the name of the Album
	 */
	
	public String getName()
	{
		return name;
	}
	/**
	 * A method to get the date of the earliest photo in the album
	 * @return a Calendar object which is the date of the earliest photo in the album
	 */
	
	public Calendar getEarliest()
	{
		return earliest;
	}
	
	/**
	 * A method to set the date of the earliest photo in the album
	 * @param e: a Calendar object which is the new date of the earliest photo in the album
	 */
	public void setEarliest(Calendar e)
	{
		this.earliest=e;
	}
	
	/**
	 * A method to get the date of the latest photo in the album
	 * @return the date of the latest photo in the album
	 */
	
	
	public Calendar getLatest()
	{
		return latest;
	}
	
	/**
	 * A method to set the date of the latest photo in the album
	 * @param e: a Calendar object which is the new date of the latest photo in the album
	 */
	
	public void setLatest(Calendar l)
	{
		this.latest=l;
	}
	
	/**
	 * A method to get the photos in the album
	 * @return an ArrayList of Photos in the album
	 */
	
	public ArrayList<Photo> getPhotos()
	{
		return photos;
	}
	/**
	 * A method to set the photos in the album
	 * @param newphotos: the new ArrayList of Photos in the album
	 */
	
	public void setPhotos(ArrayList<Photo> newphotos)
	{
		this.photos=newphotos;
	}
	
	
	
	
	/**
	 * A method to get the string representation of an Album object
	 * @return a String representation of an Album object
	 */
	
	
	public String toString()
	{
		
		if(earliest==null && latest==null)
		{
			String retval="Name: "+this.name+"\n"+"Number of Photos in Album: "+this.photos.size()+ "\n" + "Earliest Photo Date in Album: No Photos in Album Yet"+"\n"+"Latest Photo Date in Album:"+"No Photos in Album Yet";
			return retval;
		}
		
		
		
		String retval="Name: "+this.name+"\n"+"Number of Photos in Album: "+this.photos.size()+ "\n" + "Earliest Photo in Album:"+this.earliest.getTime()+"\n"+"Latest Photo in Album:"+this.latest.getTime();
		return retval;
	}
	
	
	
	
	
	
	

}
