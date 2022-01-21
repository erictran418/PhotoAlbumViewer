package controllers;

import java.io.File;
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
 * This is the Delete Users Controller. It is the controller for the DeleteUser.fxml file
 */


public class DeleteUsersController {
	
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
	 * The name of the current user selected in the List View
	 */
	
	private String selectedname;
	
	/**
	 * The Observable List attached to the List View
	 */
	
	ObservableList<String> tempobs;
	
	public void start(ArrayList<User> userlist)
	{
		this.userlist=userlist;
		
		 tempobs= FXCollections.observableArrayList();
			
		 for(int i=0;i<userlist.size();i++)
			{
				User u=userlist.get(i);
				tempobs.add(u.getName());
			}
			
		listView.setItems(tempobs);
		
		listView.getSelectionModel().selectedIndexProperty()
		.addListener((obs, prevSelec, currSelec) -> {
			if ((int)currSelec > -1) {
				selectedname = tempobs.get((int) currSelec);
				
			}
		});
		
		
		
		
	}
	
	/**
	 * Called when the Delete Users button is pressed. Deletes the selected user
	 */
	public void ActualDeleteUser() throws IOException
	{
		

	
		
		//check if valid
		
		if(selectedname==null || selectedname.isEmpty() || tempobs.size()==0)
		{
            Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("New Username Input Error");
			alert.setHeaderText("Need to Select a Username of an Existing User");
			String content="Entering a valid new UserName is required to create a New User. You have not entered this, and your login request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		boolean found=false;
		
		for(int i=0;i<userlist.size();i++)
		{
			String check=userlist.get(i).getName();
			
			if(check.equals(selectedname))
			{
				//remove
				found=true;
				userlist.remove(i);
				tempobs.remove(selectedname);
				break;
			}
		}
		
		
		
		
		
		
		
		
		
		//delete file of user
		
		File toDel=new File("data"+File.separator+selectedname);
		toDel.delete();
		
		
		
		
		
	}
	
	/**
	 * Called when the Back to Admin button in is pressed. Takes the user back to the AdminStarting.fxml page
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
