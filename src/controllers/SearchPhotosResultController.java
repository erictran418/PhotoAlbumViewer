package controllers;
import models.*;

import java.io.IOException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Search Photos Result Controller. Helps the User view their search results
 */

public class SearchPhotosResultController {
	
	
	/**
	 * An ArrayList containing the results of the user's search
	 */
	
	private ArrayList<Photo> result;
	
	/**
	 * The current User
	 */
	
	private User currentuser;
	
	/**
	 * An ArrayList containing all the current users in the program
	 */
	
	private ArrayList<User> userlist;
	@FXML Button BacktoSearch;
	@FXML Button LogoutButton;
	@FXML Button MakeAlbumButton;
	@FXML TextField NewAlbumName;
	
	/**
	 * The List View for the search results
	 */
	@FXML 
	ListView<Photo> listView = new ListView<Photo>();
	
	/**
	 * The Observable connected to the List View for the search results
	 */
	
    ObservableList<Photo> items = FXCollections.observableArrayList (); 
	
	
    /**
	 * The method called when first starting up the Search Photos Results FXML page, populates the class's fields
	 * @param userlist: an ArrayList of Users
	 * @param user: the current user
	 * @param result: the results Photos of the user's search
	 */
	
	public void start(ArrayList<User>userlist, User user,ArrayList<Photo>result)
	{
		this.result=result;
		this.currentuser=user;
		this.userlist=userlist;
		
		
		//photo displaying stuff
		
		listView.setCellFactory(listView -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            
            @Override
            public void updateItem(Photo ph, boolean empty) {
                super.updateItem(ph, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	Image image=ph.getImage().getImage();
                    
                    imageView.setImage(image);
                    
                    imageView.setFitWidth(100);
     				imageView.setFitHeight(100);
     				String caption=ph.getCaption();
     				if(caption==null)
     				{
     					caption="";
     				}
                    setText(caption);
                    setGraphic(imageView);
                }
            }
        });
		
		for(int i=0;i<this.result.size();i++)
		{
			Photo p=this.result.get(i);
			items.add(p);
		}
		
		listView.setItems(items);
		
		
		
		
		
	}
	
	
	
	/**
	 * The method called when the Make Album From Search Results Button is pressed. Creates an Album for The User made up of the Search Results Photos
	 */
	
	
	
	
	public void MakeAlbum() throws IOException
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
		albuminsert.setPhotos(result);
		currentuser.addAlbum(albuminsert);
		
		//remove current user
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
		
		
		//save new info
		currentuser.saveUser();
		
		
		
		
		userlist.add(currentuser);
		
		Alert alert= new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Album Creation Successful");
		alert.setHeaderText("Album Creation Successful");
		String content="Your creation of a new album out of the search results is successful";
		alert.setContentText(content);
		alert.showAndWait();
		NewAlbumName.setText("");
		return;
		
		
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
			LoginController loginController = loader.getController();
			loginController.backtoLogin(userlist);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
       
	}
	
	
	/**
	 * The method called when the Back to Search is pressed. Takes user back to Search Photos FXML page
	 */
	
	
	public void backtoSearch()
	{
       try {
			
    		FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/SearchPhotos.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) BacktoSearch.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setScene(scene);
			SearchPhotosController PhotosController = loader.getController();
			PhotosController.start(userlist, currentuser);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
	
	

}
