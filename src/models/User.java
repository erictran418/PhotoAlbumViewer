package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This is the User class. It facilitates the storage of Albums within it
 */

public class User implements Serializable {
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -7597984632340576357L;

	/**
	 * The name of the User
	 */
	
	private String username;
	
	/**
	 * The Albums of the User
	 */
	
	private ArrayList<Album> albums;
	
	/**
	 * The Directory the User is stored in
	 */
	
	private String storedir;
	
	/**
	 * The List of Possible Valid Tags on the User's Photos
	 */
	
	private ArrayList<String> validtags;
	
	
	/**
	 * Constructor of the class; Sets up the initial properties of the User
	 * @param name: the name of the User
	 */
	
	public User(String name)
	{
		this.username=name;
		albums=new ArrayList<Album>();
		storedir=name;
		validtags=new ArrayList<String>();
		
		
	}
	
	/**
	 * Method to Add to The User's Valid Tags list
	 * @param s: the Tag name to Add to the Valid Tags List
	 */
	
	
	public void addtovtags(String s)
	{
		this.validtags.add(s);
	}
	/**
	 * Method to get the User's Valid Tags list
	 * @return the User's Valid Tags List
	 */
	
	
	public ArrayList<String> getvalidts()
	{
		return this.validtags;
	}
	
	/**
	 * Method to Add to The User's Valid Tags list
	 * @param a: the Album to add to the Albums list
	 */
	
	
	public void addAlbum(Album a)
	{
		albums.add(a);
	}
	
	/**
	 * Method to Delete an Album from the User's Albums list
	 * @param a: the Album to delete from the Albums list
	 */
	 
	public void deleteAlbum(Album a)
	{
	    String deletename=a.getName();
	    
	    for(int i=0;i<this.albums.size();i++)
	    {
	    	Album checka=this.albums.get(i);
	    	if(checka.getName().equals(deletename))
	    	{
	    		this.albums.remove(i);
	    		break;
	    	}
	    }
	}
	
	/**
	 * Method to Replace an Album from the User's Albums list
	 * @param old: the Album to be replaced
	 * @param changed: the Album that replaces Album old in the user's Album ArrayList
	 */
	
	public void changeAlbum(Album old, Album changed)
	{
		//look for it and remove the old one
		String searchname=old.getName();
		
		for(int i=0;i<albums.size();i++)
		{
			String check=albums.get(i).getName();
			
			if(searchname.equals(check))
			{
				this.albums.remove(i);
				break;
			}
		}
		
		//add new
		
		albums.add(changed);
		
		
	}
	
	/**
	 * Method to get the User's Name
	 * @return the User's Name
	 */
	
	
	public String getName()
	{
		return username;
	}
	
	/**
	 * Method to get the User's Albums
	 * @return the User's Album ArrayList
	 */
	
	public ArrayList<Album> getAlbums()
	{
		return albums;
	}
	
	/**
	 * Method to save the User's Information to the Disk
	 */
	
	public void saveUser() throws IOException
	{
		File fi=new File("data" + File.separator + this.storedir);
		
		
		
		FileOutputStream f=new FileOutputStream("data" + File.separator + this.storedir);
		ObjectOutputStream oos = new ObjectOutputStream(f);
		
	    oos.writeObject(this);
	    
	    oos.close();
		f.close();
	}
	
	
	
	
	
	
	

}
