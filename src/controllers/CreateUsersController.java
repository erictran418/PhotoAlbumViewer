package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.User;

/**
 * This is the Create Users Controller. It is the controller for the CreateUser.fxml file
 */

public class CreateUsersController {
	
	@FXML Button BacktoAdminButton;
	@FXML Button CreateUsersButton2;
	@FXML TextField NewUsernameEnter;
	@FXML Button LogoutButton;
	
	/**
	 * A String List View used to show the names of current users in the program
	 */
	
	
	@FXML ListView<String> listView;
	
	
	/**
	 * An ArrayList containing all the current users in the program
	 */
	
	private ArrayList<User> userlist;
	
	/**
	 * The method called when first starting up the Create Users FXML page, populates the class's fields
	 * @param userlist: an ArrayList of Users
	 */
	
	public void start(ArrayList<User> userlist)
	{
		this.userlist=userlist;
		
        ObservableList<String> tempobs= FXCollections.observableArrayList();
		
		for(int i=0;i<userlist.size();i++)
		{
			User u=userlist.get(i);
			tempobs.add(u.getName());
		}
		
		listView.setItems(tempobs);
		
		
		
		
		
	}
	
	/**
	 * Called when the Create Users button in CreateUser.fxml is pressed. Creates a user of the specified username
	 */
	public void ActualCreateUser () throws IOException
	{
		String username=NewUsernameEnter.getText();
		
		//check if valid
		
		if(username==null || username.isEmpty() || username.isBlank())
		{
            Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("New Username Input Error");
			alert.setHeaderText("Need to Enter a Username not Empty or Already Taken");
			String content="Entering a valid new UserName is required to create a New User. You have not entered this, and your login request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		boolean found=false;
		
		for(int i=0;i<userlist.size();i++)
		{
			String check=userlist.get(i).getName();
			
			if(check.equals(username))
			{
				found=true;
				break;
			}
		}
		
		if(found==true)
		{
			 Alert alert= new Alert(AlertType.INFORMATION);
				
			alert.setTitle("New Username Input Error");
			alert.setHeaderText("Need to Enter a Username not Empty or Already Taken");
			String content="Entering a valid new UserName is required to create a New User. You have not entered this, and your login request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			
			NewUsernameEnter.setText("");
			return;
		}
		
		
		
		//create user
		
		User newuser= new User(username);
		
		//save it
		newuser.saveUser();
		
		
		//add to AL
		userlist.add(newuser);
		
		 ObservableList<String> tempobs= FXCollections.observableArrayList();
			
			for(int i=0;i<userlist.size();i++)
			{
				User u=userlist.get(i);
				tempobs.add(u.getName());
			}
			
			listView.setItems(tempobs);
		
		
	}
	
	/**
	 * Called when the Back to Admin button in CreateUser.fxml is pressed. Takes the user back to the AdminStarting.fxml page
	 */
	
	public void backtoAdmin()
	{
		//switch to admin screen
		
		try {
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/AdminStarting.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) BacktoAdminButton.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setScene(scene);
			AdminController adminController = loader.getController();
			adminController.start(userlist);
			window.show();
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	/**
	 * The method called when the Logout Button is pressed. Takes user back to Login Page
	 */
	
	public void logout()
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
