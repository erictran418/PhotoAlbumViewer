package controllers;
import models.*;


import controllers.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This is the Admin Controller. It is the controller for the AdminStarting.fxml file
 */


public class AdminController {
	
	
	/**
	 * An ArrayList containing all the current users in the program
	 */
	private ArrayList<User> userlist;
	
	
	@FXML Button ListUsersButton;
	@FXML Button CreateUsersButton;
	@FXML Button DeleteUsersButton;
	@FXML Button LogoutButton;
	@FXML Button TerminateButton;
	
	
	@FXML TextField NewUsernameEnter;
	@FXML Button CreateUsersButton2;
	@FXML Button BacktoAdminButton;
	@FXML Button TerminateButton2;
	
	

	/**
	 * The method called when first starting up the Admin FXML page, populates the class's fields
	 * @param userlist: an ArrayList of Users
	 */
	public void start(ArrayList<User> userlist)
	{
		this.userlist=userlist;
		
		
	}
	
	/**
	 * The method called when the Logout Button is pressed. Takes user back to Login Page
	 */
	
	public void onLogoutAdmin()
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
	 * The method called when the List Users Button is pressed. Takes user to the UserList.fxml page
	 */
	
	public void onListUsersPress()
	{
		//switch to List Users
		
		try {
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/UserList.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) ListUsersButton.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setScene(scene);
			ListUsersController listusersController = loader.getController();
			listusersController.start(userlist);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * The method called when the Create Users Button is pressed. Takes user to the CreateUser.fxml page
	 */
	
	public void onCreateUsersPress()
	{
           try {
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/CreateUser.fxml"));
			Parent root= (Parent) loader.load();
			Stage window=(Stage) ListUsersButton.getScene().getWindow();
			Scene scene = new Scene(root);
			window.setScene(scene);
			CreateUsersController createusersController = loader.getController();
			createusersController.start(userlist);
			window.show();
			
			
			return;
			

			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	/**
	 * The method called when the Delete Users Button is pressed. Takes user to the DeleteUser.fxml page
	 */
	
	public void onDeleteUsersPress()
	{
		 try {
				
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/DeleteUser.fxml"));
				Parent root= (Parent) loader.load();
				Stage window=(Stage) ListUsersButton.getScene().getWindow();
				Scene scene = new Scene(root);
				window.setScene(scene);
				DeleteUsersController deleteusersController = loader.getController();
				deleteusersController.start(userlist);
				window.show();
				
				
				return;
				

				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
