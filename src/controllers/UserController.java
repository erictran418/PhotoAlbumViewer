package controllers;
import java.util.ArrayList;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.User;
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The User Controller. Helps the User view their Albums and perform operations on them
 */

public class UserController {
	
	
	@FXML Button LogoutButton;
	@FXML Button OpenAlbumButton;
	
	@FXML Button AlbumAdd;
	@FXML Button DeleteAlbum;
	@FXML Button CreateAlbum;
	@FXML Button SearchPhoto;
	@FXML Button backtoAdmin;
	@FXML Button onTerminate;
	@FXML Button initialize;
	@FXML ImageView img1;
	@FXML Button DoPhoto;
	@FXML Text startingtext;
	@FXML TextField NewAlbumName;
	
	/**
	 * The List View used to display the album information
	 */
	
	
	@FXML ListView<String> listView;
	
	/**
	 * The list of all users
	 */
	
	private ArrayList<User> userlist;
	
	/**
	 * The observable list connected to the List View
	 */
	private ObservableList<String> obslist;
	
	/**
	 * The Current User
	 */
	
	private User currentuser;
	
	/**
	 * The String representation of the selected album
	 */
	private String selectedalbumstr;
	
	
	/**
	 *the selected index of the List View
	 */
	private int selectedindex;
	
	/**
	 * The method called when first starting up the Regular User System FXML page, populates the class's fields
	 * @param users: an ArrayList of Users
	 * @param username: the current user's name
	 */
	
	public void start(String username, ArrayList<User> users)
	{
        this.userlist=users;
        
        for(int i=0;i<userlist.size();i++)
        {
        	String compare=userlist.get(i).getName();
        	if(username.equals(compare))
        	{
        		currentuser=userlist.get(i);
        		break;
        	}
        }
        
        startingtext.setText("All Album Information for "+ username);
        obslist=FXCollections.observableArrayList();
        
        ArrayList<Album> albumlist=currentuser.getAlbums();
        
        for(int i=0;i<albumlist.size();i++)
        {
        	String add=albumlist.get(i).toString();
        	obslist.add(add);
        }
        
        
        
        
        
        
        listView.setItems(obslist);
        
        listView.getSelectionModel().selectedIndexProperty()
		.addListener((obs, prevSelec, currSelec) -> {
			if ((int)currSelec > -1) {
				selectedalbumstr = obslist.get((int) currSelec);
				selectedindex=(int)currSelec;
			}
		});
        
	}
	
	/**
	 * The method called when the Add Album is pressed. Adds an Album to the Current Users list
	 */
	
	public void AlbumAdd() throws IOException
	{
		String newname=NewAlbumName.getText();
		
		if(newname==null || newname.isEmpty())
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Empty New Name Text Box");
			alert.setHeaderText("New Album Name Text Box Empty");
			String content="You have not specified the new name of the created album. Your create album request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			return;
		}
		
		
		//error
		boolean found=false;
		ArrayList<Album> albumlist=currentuser.getAlbums();
		for(int i=0;i<albumlist.size();i++)
		{
			String checkname=albumlist.get(i).getName();
			if(checkname.equals(newname))
			{
				found=true;
				break;
			}
		}
		
		if(found==true)
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Create Album Input Error");
			alert.setHeaderText("Album Name Must Be Unique");
			String content="Entering a valid new Album Name is required to create a New Album. You have not entered this, and your create album request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			return;
		}
		
		
		
		
		Album albuminsert=new Album(newname);
		currentuser.addAlbum(albuminsert);
		String newresult=albuminsert.toString();
		obslist.add(newresult);
		
		//save new info
		currentuser.saveUser();
		
		//update user list
		String un=currentuser.getName();
		for(int i=0;i<userlist.size();i++)
		{
			String check=userlist.get(i).getName();
			if(un.equals(check))
			{
				userlist.remove(i);
				break;
			}
		}
		
		
		userlist.add(currentuser);
		
		
		
		
	}
	
	/**
	 * The method called when the Delete Album is pressed. Deletes the selected Album and updates the ListView accordingly
	 */
	
	public void DeleteAlbum() throws IOException
	{
		
		//get name of selected album 6 to \n
		
		if(obslist.isEmpty())
		{
			 Alert alert= new Alert(AlertType.INFORMATION);
				
			alert.setTitle("Empty Album List");
			alert.setHeaderText("Current User Has No Albums");
			String content="You have no Albums to delete. Your delete request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		ArrayList<Character> namestorage=new ArrayList<Character>();
		
		int index=6;
		
		while(index<selectedalbumstr.length() && selectedalbumstr.charAt(index)!='\n')
		{
			namestorage.add(selectedalbumstr.charAt(index));
			index++;
		}
		
		String s=new String();
		
		for(int i=0;i<namestorage.size();i++)
		{
			s=s+namestorage.get(i);
		}
		
		//remove from obslist
		
		obslist.remove(selectedindex);
		
		
		
		//remove from actual user albums list
		ArrayList<Album> albumlist=currentuser.getAlbums();
		for(int i=0;i<albumlist.size();i++)
		{
			String check=albumlist.get(i).getName();
			if(s.equals(check))
			{
				currentuser.deleteAlbum(albumlist.get(i));
				break;
			}
		}
		
		
		
		
		//save new info
		currentuser.saveUser();
		
		//update user list
				String un=currentuser.getName();
				for(int i=0;i<userlist.size();i++)
				{
					String check=userlist.get(i).getName();
					if(un.equals(check))
					{
						userlist.remove(i);
						break;
					}
				}
				
				
				userlist.add(currentuser);
		
		
	}
	
	/**
	 * The method called when the Rename Album is pressed. Renames the selected Album and updates the ListView accordingly
	 */
	
	public void RenameAlbum() throws IOException
	{
		String newname=NewAlbumName.getText();
		
		if(newname==null || newname.isEmpty())
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Empty New Name Text Box");
			alert.setHeaderText("New Album Name Text Box Empty");
			String content="You have not specified the new name of the selected Album to rename it to. Your rename request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			return;
		}
		
		if(selectedalbumstr==null)
		{
            Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Selected Album Null");
			alert.setHeaderText("Selected Album Null");
			String content="Selected Album Null";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			return;
		}
		
		
		
		
		
		
		//get name of selected album 6 to \n
		
		if(obslist.isEmpty())
		{
			Alert alert= new Alert(AlertType.INFORMATION);
						
			alert.setTitle("Empty Album List");
			alert.setHeaderText("Current User Has No Albums");
			String content="You have no Albums to rename. Your rename request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			return;
		}
				
				ArrayList<Character> namestorage=new ArrayList<Character>();
				
				int index=6;
				
				while(index<selectedalbumstr.length() && selectedalbumstr.charAt(index)!='\n')
				{
					namestorage.add(selectedalbumstr.charAt(index));
					index++;
				}
				
				String s=new String();
				
				for(int i=0;i<namestorage.size();i++)
				{
					s=s+namestorage.get(i);
				}
		
		
		
				
				
		//check if valid
		boolean found=false;
		ArrayList<Album> allist=currentuser.getAlbums();
		for(int i=0;i<allist.size();i++)
		{
			String check=allist.get(i).getName();
			if(newname.equals(check))
			{
				found=true;
				break;
			}
		}
				
				
		if(found)
		{
			
			Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Invalid Album Name Entered");
			alert.setHeaderText("Current User Already Has Album of That Name");
			String content="You already have an Album with that Name. Your rename request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			
			
			return;
		}
				
				
				
				
				
				
				
				
				
				
				
		//change user's AL value
		String newrep="";
		ArrayList<Album> albumList= currentuser.getAlbums();		
		for(int i=0;i<albumList.size();i++)
		{
			String check=albumList.get(i).getName();
			if(check.equals(s))
			{
				Album old=albumList.get(i);
				Album changed=new Album(newname);
				changed.setEarliest(old.getEarliest());
				changed.setLatest(old.getLatest());
				changed.setPhotos(old.getPhotos());
				
				currentuser.changeAlbum(old, changed);
				newrep=changed.toString();
				break;
			}
		}
		
		
		//save new info
		currentuser.saveUser();
		
		obslist.set(selectedindex,newrep);
		selectedalbumstr=newrep;
		
		//update user list
		String un=currentuser.getName();
		for(int i=0;i<userlist.size();i++)
		{
					String check=userlist.get(i).getName();
					if(un.equals(check))
					{
						userlist.remove(i);
						break;
					}
		}
				
				
		userlist.add(currentuser);
		
	}
	@FXML
	
	/**
	 * The method called when the Search Button is pressed. Takes user back to the Open Album FXML Page
	 */
	
	public void OpenAlbum()
	{
		if(selectedalbumstr==null)
		{
            Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Selected Album Null");
			alert.setHeaderText("Selected Album Null");
			String content="Selected Album Null";
			alert.setContentText(content);
			alert.showAndWait();
			NewAlbumName.setText("");
			return;
		}
		
		
		
		
		
		
       try {
    	   
    	   FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/OpenAlbum.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) OpenAlbumButton.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setTitle("Open Album Page");
			window.setScene(scene);
			OpenAlbumController openalbumController = loader.getController();
			
			
			ArrayList<Character> charlist=new ArrayList<Character>();
			int i=6;
			while(selectedalbumstr.charAt(i)!='\n' && i<selectedalbumstr.length())
			{
				charlist.add(selectedalbumstr.charAt(i));
				i++;
			}
			
			String s=new String();
			
			for(int z=0;z<charlist.size();z++)
			{
				s=s+charlist.get(z);
			}
			
			
			
			
			
			
			
			openalbumController.start(userlist,currentuser.getName(), s);
			window.show();
	
			return;
			

			
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	@FXML
	
	/**
	 * The method called when the Search Button is pressed. Takes user back to the Search Photos FXML Page
	 */
	public void SearchPhoto()
	{
       try {
			
    		FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/SearchPhotos.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) SearchPhoto.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setTitle("Search Photos Page");
			window.setScene(scene);
			SearchPhotosController PhotosController = loader.getController();
			PhotosController.start(userlist, currentuser);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		
	}
	@FXML
	
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
			LoginController loginController = loader.getController();
			loginController.backtoLogin(userlist);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	
	
	

	
	
	
	

}
