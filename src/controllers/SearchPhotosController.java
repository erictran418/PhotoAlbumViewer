package controllers;


import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;


/**
 * The Search Photos Controller. Helps the User enter the criteria they want to search for photos on
 */



public class SearchPhotosController {
	
	/**
	 * An ArrayList containing all the current users in the program
	 */
	
	
	private ArrayList<User> userlist;
	
	/**
	 * The current User
	 */
	
	
	private User currentuser;
	
	@FXML TextField TagType1;
	@FXML TextField TagValue1;
	@FXML TextField TagType2;
	@FXML TextField TagValue2;
	@FXML TextField conjordis;
	@FXML Button SearchByTags;
	@FXML Button SearchByDate;
	@FXML Button LogoutButton;
	@FXML Button BacktoUsersButton;
	@FXML
	DatePicker fromDate, toDate;
	@FXML TextField startingtime;
	@FXML TextField endingtime;
	
	
	/**
	 * The method called when first starting up the Search Photos FXML page, populates the class's fields
	 * @param userlist: an ArrayList of Users
	 * @param currentuser: the current user
	 */
	
	public void start(ArrayList<User> userlist, User currentuser)
	{
		this.userlist=userlist;
		this.currentuser=currentuser;
	}
	
	/**
	 * The method called when the Search Photos by Tag button is pressed. Takes the user to the Search Results Page with their search results by tag
	 */
	
	public void SearchbyTag()
	{
		//how many are filled
		int filled=0;
		String tag1type=TagType1.getText();
		String tag2type=TagType2.getText();
		String tag1value=TagValue1.getText();
		String tag2value=TagValue2.getText();
		
		if(tag1type!=null && tag1type.isEmpty()==false)
		{
			filled++;
		}
		if(tag2type!=null && tag2type.isEmpty()==false)
		{
			filled++;
		}
		
		//errors in type and value, if both are null
		
		if((tag1type==null || tag1type.isEmpty())   && (tag2type==null || tag2type.isEmpty()) )
		{
            Alert alert= new Alert(AlertType.INFORMATION);
			alert.setTitle("Empty Tag Type Text Boxes");
			alert.setHeaderText("Tag Type Text Box Empty");
			String content="You have not specified any tag type to search for photos on. Your request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			conjordis.setText("");
			return;
		}
		
		
		
		
		
		
		
		
		
		//check for error
		String type=conjordis.getText();
		String realtype=type.toLowerCase();
		
		if(realtype==null || realtype.isEmpty() && filled==2)
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			alert.setTitle("Empty Search Type Text Box");
			alert.setHeaderText("Search Type Text Box Empty");
			String content="If you want to search photos belonging to 2 tag-value pairs, you must specify whether you want a conjunctive or disjunctive combination on the tag-value pairs.  You have not specified this search type to search for photos on. Your request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			conjordis.setText("");
			return;
		}
		if(realtype.equals("conjunction")==false && realtype.equals("disjunction")==false && filled==2)
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			alert.setTitle("Search Type Text Box Must be Filled with Appropriate Values-Conjunction or Disjunction");
			alert.setHeaderText("Search Type Text Box Has Wrong Value");
			String content="If you want to search photos belonging to 2 tag-value pairs, you must specify whether you want a conjunctive or disjunctive combination on the tag-value pairs.  You have not specified this search type to search for photos on. Your request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			conjordis.setText("");
			return;
		}
		if((realtype.equals("conjunction") || realtype.equals("disjunction"))&& filled!=2)
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			alert.setTitle("Search Type Text Box Filled For One Tag-Value Pair");
			alert.setHeaderText("Search Type Text Box Empty");
			String content="If you want to search photos belonging to 2 tag-value pairs, you must specify whether you want a conjunctive or disjunctive combination on the tag-value pairs.  You have not specified this search type to search for photos on. Your request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			conjordis.setText("");
			return;
		}
		
		//look for appropriate photos
		ArrayList<Photo> result=new ArrayList<Photo>();		
		
		ArrayList<Album> albumlist=currentuser.getAlbums();
		
		for(int albumindex=0;albumindex<albumlist.size();albumindex++)
		{
			Album curralb=albumlist.get(albumindex);
			ArrayList<Photo> currentphotos=curralb.getPhotos();
			for(int photoindex=0;photoindex<currentphotos.size();photoindex++)
			{
				if(realtype.equals("conjunction"))
				{
					ArrayList<Tag> taglist=currentphotos.get(photoindex).getTags();
					boolean t1good=false;
					boolean t2good=false;
					Tag t1test=new Tag(tag1type, tag1value);
					Tag t2test=new Tag(tag2type, tag2value);
					for(int i=0;i<taglist.size();i++)
					{
						Tag current=taglist.get(i);
						
						if(current.tagEquals(t1test))
						{
							t1good=true;
						}
						if(current.tagEquals(t2test))
						{
							t2good=true;
						}
						
						
						
					}
					
					//good
					if(t1good && t2good)
					{
                        boolean found=false;
						
						for(int k=0;k<result.size();k++)
						{
							Photo alreadyin=result.get(k);
							Photo current=currentphotos.get(photoindex);
							
							if(current.equals(alreadyin))
							{
								found=true;
								break;
							}
						}
						
						
						
						if(found==false)
						{
						
						  result.add(currentphotos.get(photoindex));
						
						}
						
					}
					
					
					
					
					
					
					
					
				}
				//disjunc
				else if(realtype.equals("disjunction"))
				{
					ArrayList<Tag> taglist=currentphotos.get(photoindex).getTags();
					boolean t1good=false;
					boolean t2good=false;
					Tag t1test=new Tag(tag1type, tag1value);
					Tag t2test=new Tag(tag2type, tag2value);
					for(int i=0;i<taglist.size();i++)
					{
						Tag current=taglist.get(i);
						
						if(current.tagEquals(t1test))
						{
							t1good=true;
						}
						if(current.tagEquals(t2test))
						{
							t2good=true;
						}
						
						
						
					}
					
					//good
					if(t1good || t2good)
					{
                        boolean found=false;
						
						for(int k=0;k<result.size();k++)
						{
							Photo alreadyin=result.get(k);
							Photo current=currentphotos.get(photoindex);
							
							if(current.equals(alreadyin))
							{
								found=true;
								break;
							}
						}
						
						
						
						if(found==false)
						{
						
						  result.add(currentphotos.get(photoindex));
						
						}
						
					}
				}
				else
				{
					Tag test=null;
					if(tag1type!=null && tag1type.isEmpty()==false)
					{
			            test=new Tag(tag1type, tag1value);			
 
					}
					else if(tag2type!=null && tag2type.isEmpty()==false)
					{
						test=new Tag(tag2type, tag2value);	
					}
					
					
					ArrayList<Tag> taglist=currentphotos.get(photoindex).getTags();
					boolean good=false;
					
				
					for(int i=0;i<taglist.size();i++)
					{
						Tag current=taglist.get(i);
						
						if(current.tagEquals(test))
						{
							good=true;
						}
						
						
						
						
					}
					
					//good
					if(good)
					{
						boolean found=false;
						
						for(int k=0;k<result.size();k++)
						{
							Photo alreadyin=result.get(k);
							Photo current=currentphotos.get(photoindex);
							
							if(current.equals(alreadyin))
							{
								found=true;
								break;
							}
						}
						
						
						
						if(found==false)
						{
						
						  result.add(currentphotos.get(photoindex));
						
						}
						
					}
					
					
					
					
					
				}
			}
		}
		
		
		//switch to search results page
		
        try {
			
    		FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/SearchPhotosResult.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) SearchByTags.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setScene(scene);
			window.setTitle("Search Photo Results Page");
			SearchPhotosResultController searchresultsController = loader.getController();
			searchresultsController.start(userlist, currentuser,result);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		
		
		
		
        /**
    	 * The method called when the Search Photos by Date button is pressed. Takes the user to the Search Results Page with their search results by date
    	 */	
		
		
	}
	public void SearchbyDate()
	{
		
		 String begtime=startingtime.getText();
		 String endtime=endingtime.getText();
		 
		 if(begtime==null || begtime.isEmpty())
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Empty Starting Time Text Box");
			 alert.setHeaderText("Starting Time Text Box Empty");
			 String content="If you want to search photos by date, you must specify the starting and ending times for your search range.  You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 if(begtime==null || begtime.isEmpty())
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Empty Ending Time Text Box");
			 alert.setHeaderText("Ending Time Text Box Empty");
			 String content="If you want to search photos by date, you must specify the starting and ending times for your search range.  You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 //format
		 
		 if(begtime.length()!=8)
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Starting Time Text Box");
			 alert.setHeaderText("Starting Time Text Box Input Not Correct Format");
			 String content="The specified starting time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 if(begtime.charAt(2)!=':' || begtime.charAt(5)!=':')
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Starting Time Text Box");
			 alert.setHeaderText("Starting Time Text Box Input Not Correct Format");
			 String content="The specified starting time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 if( ((Character.isDigit(begtime.charAt(0)))==false) || ((Character.isDigit(begtime.charAt(1)))==false) || ((Character.isDigit(begtime.charAt(3)))==false) || ((Character.isDigit(begtime.charAt(4)))==false) || ((Character.isDigit(begtime.charAt(6)))==false || ((Character.isDigit(begtime.charAt(7)))==false))                             )
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Starting Time Text Box");
			 alert.setHeaderText("Starting Time Text Box Input Not Correct Format");
			 String content="The specified starting time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 int first2=Integer.parseInt(begtime.substring(0, 2));
		 int second2=Integer.parseInt(begtime.substring(3, 5));
		 int third2=Integer.parseInt(begtime.substring(6, 8));
		 
		 
		 
		 
		 if(first2<0 || first2>24 || second2<0 || second2>60 || third2<0 || third2>60)
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Starting Time Text Box");
			 alert.setHeaderText("Starting Time Text Box Input Not Correct Format");
			 String content="The specified starting time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 
		 
		 
		 if(endtime.length()!=8)
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Ending Time Text Box");
			 alert.setHeaderText("Ending Time Text Box Input Not Correct Format");
			 String content="The specified ending time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 if(endtime.charAt(2)!=':' || endtime.charAt(5)!=':')
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Ending Time Text Box");
			 alert.setHeaderText("Ending Time Text Box Input Not Correct Format");
			 String content="The specified starting time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 if( ((Character.isDigit(endtime.charAt(0)))==false) || ((Character.isDigit(endtime.charAt(1)))==false) || ((Character.isDigit(endtime.charAt(3)))==false) || ((Character.isDigit(endtime.charAt(4)))==false) || ((Character.isDigit(endtime.charAt(6)))==false || ((Character.isDigit(endtime.charAt(7)))==false))                             )
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Ending Time Text Box");
			 alert.setHeaderText("Ending Time Text Box Input Not Correct Format");
			 String content="The specified ending time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 int first22=Integer.parseInt(endtime.substring(0, 2));
		 int second22=Integer.parseInt(endtime.substring(3, 5));
		 int third22=Integer.parseInt(endtime.substring(6, 8));
		 
		 
		 
		 
		 if(first22<0 || first22>24 || second22<0 || second22>60 || third22<0 || third22>60)
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Invalid Ending Time Text Box");
			 alert.setHeaderText("Ending Time Text Box Input Not Correct Format");
			 String content="The specified ending time must be in the Hours.Minutes.Seconds Format, with all numerical values being between 1 and 24. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 
		
		
		
		
		
		
		 LocalDate beginningrangel=fromDate.getValue();
		 
		 if(beginningrangel==null)
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Must Pick a Starting and Ending Date to Search By Date");
			 alert.setHeaderText("Must Pick a Starting and Ending Date to Search By Date");
			 String content="You must pick a starting and ending date to be able to search by date. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 
		 
		 int year1=beginningrangel.getYear();
		 int month1=beginningrangel.getMonth().getValue();
		 int day1=beginningrangel.getDayOfMonth();
		 
		 
			
		 LocalDate endingrangel=toDate.getValue();
		 
		 if(endingrangel==null)
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Must Pick a Starting and Ending Date to Search By Date");
			 alert.setHeaderText("Must Pick a Starting and Ending Date to Search By Date");
			 String content="You must pick a starting and ending date to be able to search by date. You have not specified this. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		 
		 
		 
		 
		 
		 
		 
		 int year2=endingrangel.getYear();
		 int month2=endingrangel.getMonth().getValue();
		 int day2=endingrangel.getDayOfMonth();
		 
		 
		 
		
		
		
		Calendar beginningrange=Calendar.getInstance();
		beginningrange.set(year1, month1, day1, first2, second2, third2);
		beginningrange.set(Calendar.MILLISECOND,0);
		
		Calendar endingrange=Calendar.getInstance();
		endingrange.set(year2, month2, day2, first22, second22, third22);
		endingrange.set(Calendar.MILLISECOND,0);
		
		if(endingrange.before(beginningrange))
		 {
			 Alert alert= new Alert(AlertType.INFORMATION);
			 alert.setTitle("Ending Date and Time cannot be Before Starting Date");
			 alert.setHeaderText("Ending Date and Time cannot be Before Starting Date");
			 String content="You must pick a starting and ending date and time to be able to search by date. The ending date and time cannot be before the starting date and time, which you have entered. Your request is therefore not allowed and is thus being cancelled";
			 alert.setContentText(content);
			 alert.showAndWait();
			 startingtime.setText("");
			 endingtime.setText("");
			 return;
		 }
		
		
        ArrayList<Photo> result=new ArrayList<Photo>();		
		
		ArrayList<Album> albumlist=currentuser.getAlbums();
		
		for(int i=0;i<albumlist.size();i++)
		{
			Album curralb=albumlist.get(i);
			ArrayList<Photo> currentphotos=curralb.getPhotos();
			
			for(int j=0;j<currentphotos.size();j++)
			{
				Calendar datecheck=currentphotos.get(j).getDate();
				
				if((datecheck.equals(beginningrange) || datecheck.after(beginningrange) ) &&  (datecheck.equals(endingrange) || datecheck.before(endingrange) ) )
				{
					
					boolean found=false;
					
					for(int k=0;k<result.size();k++)
					{
						Photo alreadyin=result.get(k);
						Photo current=currentphotos.get(j);
						
						if(current.equals(alreadyin))
						{
							found=true;
							break;
						}
					}
					
					
					
					if(found==false)
					{
					
					result.add(currentphotos.get(j));
					
					}
				}
				
				
				
			}
			
		}
		
		//take to results page
		
		 try {
				
	    		FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/SearchPhotosResult.fxml"));
				Parent root= (Parent) loader.load();
				Stage window=(Stage) SearchByDate.getScene().getWindow();
				Scene scene = new Scene(root);
				window.setScene(scene);
				window.setTitle("Search Photo Results Page");
				SearchPhotosResultController searchresultsController = loader.getController();
				searchresultsController.start(userlist, currentuser,result);
				window.show();
				
				
				return;
				

				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		
		
		
		
		
		
	}
	
	
	
	
	/**
	 * The method called when the Logout Button is pressed. Takes user back to Login Page
	 */
	
	
	
	public void Logout()
	{
		 try {
				
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/login.fxml"));
				Parent root= (Parent) loader.load();
				Stage window=(Stage) LogoutButton.getScene().getWindow();
				Scene scene = new Scene(root);
				window.setScene(scene);
				window.setTitle("Login Page");
				LoginController loginController = loader.getController();
				loginController.backtoLogin(userlist);
				window.show();
				
				
				return;
				

				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
	}
	
	/**
	 * The method called when the Back to User is pressed. Takes user back to User FXML page
	 */
	
	public void backtoUserPage()
	{
		try {
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/RegularUserSystem.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) BacktoUsersButton.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setScene(scene);
			window.setTitle("Non-Admin User Subsystem");
			UserController userController = loader.getController();
			userController.start(currentuser.getName(),userlist);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
    
	

}
