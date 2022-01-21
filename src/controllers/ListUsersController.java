package controllers;



import java.util.ArrayList;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.User;

/**
 * This is the List Users fxml page Controller. 
 */


public class ListUsersController {
	
	
	/**
	 * A String List View used to show the names of current users in the program
	 */
	@FXML ListView<String> listView;
	
	
	
	@FXML Button BacktoAdminButton;
	@FXML Button terminateButton;
	@FXML Button LogoutButton;
	
	/**
	 * An ArrayList containing all the current users in the program
	 */
	
	private ArrayList<User> userlist;
	
	/**
	 * The method called when first starting up the List Users FXML page, populates the class's fields
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
	 * Called when the Back to Admin button in is pressed. Takes the user back to the AdminStarting.fxml page
	 */
	
	public void backtoAdmin()
	{
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
