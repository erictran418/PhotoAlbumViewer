package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the Tag Class. It is used to control and facilitate the storage of Tags inside the Photo class
 */

public class Tag implements Serializable {
	
	/**
	 * Serial Version ID
	 */
	
	private static final long serialVersionUID = 5475806287657866744L;
	
	/**
	 * Name of the Tag; A String Value
	 */
	
	
	private String tagName;
	
	/**
	 * Value of the Tag; A String Value
	 */
	
	private String value;
	
	/**
	 * A static ArrayList of all possible valid tags
	 */
	
	private static ArrayList<String> validtags = new ArrayList<String>();
	
	/**
	 * Constructor of the class; Sets up the initial properties of the Tag
	 * @param tagName: The name of the Tag
	 * @param value: The value of the Tag
	 */
	
	
	public Tag(String tagName, String value)
	{
		this.tagName=tagName;
		this.value=value;
		
		
		
	}
	

	/**
	 *A method to get the name of the Tag
	 *@return the name of the Tag
	 */
	
	public String getName()
	{
		return tagName;
	}
	
	/**
	 *A method to get the value of the Tag
	 *@return the value of the Tag
	 */
	
	public String getvalue()
	{
		return value;
	}
	
	/**
	 *A method to set the value of the Tag
	 *@param newname: the new name of the Tag
	 */
	
	
	public void setName(String newname)
	{
		this.tagName=newname;
	}
	
	/**
	 *A method to set the value of the Tag
	 *@param nevalue: the new value of the Tag
	 */
	
	public void setValue(String newvalue)
	{
		this.value=newvalue;
	}
	
	/**
	 *A method to add a value to the static list of all possible tags
	 *@param s: the new possible Tag name
	 */
	
	public static void addpossibletag(String s)
	{
		validtags.add(s);
	}
	
	/**
	 *A method to get the static list of all possible tags
	 *@return the ArrayList of all possible tags
	 */
	
	
	public static ArrayList<String> getposstags()
	{
		return validtags;
	}
	
	/**
	 *A method to get the String representation of a tag
	 *@return the String representation of a tag
	 */
	
	
	public String toString()
	{
		return tagName+"="+value;
	}
	
	/**
	 *A method to determine whether this Tag is equivalent to another Tag
	 *@param t2: The Tag we want to compare the current Tag to
	 *@return a boolean that tells us if the current tag is equal to t2
	 */
	
	
	public boolean tagEquals(Tag t2)
	{
		if(this.tagName.equals(t2.getName()) && this.value.equals(t2.getvalue()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	

}
