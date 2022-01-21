package controllers;
import java.io.*;
import java.util.*;

import models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	
   
	@FXML Button terminateButton;
	@FXML Button LoginButton;

	@FXML TextField Username;
	
    ArrayList<User> userlist;
    
    
   //Another test look at me look at the text file
   /**
    * Starts up application and populates the userlist with users stored in the "data" folder
    * @throws IOException
    * @throws ClassNotFoundException
    */
	
	public void start() throws IOException, ClassNotFoundException {
		
		
		userlist=new ArrayList<User>();
		
		
		//get user info
		File[] files = new File("data").listFiles();
		
		if(files!=null)
		{
		for(int i=0;i<files.length;i++)
		{
			//deserialize
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[i]));
			User u = (User)ois.readObject();
			
			userlist.add(u);
			
		}
		
		}
		
		
		
		
	}
	
	
	public void backtoLogin(ArrayList<User> newuserlist)
	{
		this.userlist=newuserlist;
	}
	
	
	
	/**
	 * When the login button is pressed, 3 things can occur
	 * if admin is inputted into the field the screen will jump to the AdminController with all of its features
	 * if Stock is inputted the screen will jump to the screen of stock photos generated from a default stock user with a stock album of 5 photos
	 * Otherwise, the program will match a correct user input to the user list and take them to the user screen with their stored albums if applicable
	 * otherwise the program will send an alert indicating that the username does not exist in the userlist
	 */
	public void onLoginPress()
	{
		String username=Username.getText();
		
		if(username==null || username.isEmpty())
		{
            Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Username Input Error");
			alert.setHeaderText("Need to Enter a Valid Username to Login");
			String content="Entering a valid UserName is required to log in to the Photos application. You have not entered this, and your login request is therefore not allowed and is thus being cancelled";
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		
		if(username.equals("admin"))
		{
			//switch to admin screen
			
			try {
				
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/AdminStarting.fxml"));
				Parent root= (Parent) loader.load();
				Stage window=(Stage) LoginButton.getScene().getWindow();
				Scene scene = new Scene(root);
				window.setScene(scene);
				window.setTitle("Admin User Subsystem");
				AdminController adminController = loader.getController();
				adminController.start(userlist);
				window.show();
				return;
				

				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	
		if(username.equals("stock"))
		{
			//switch to stock screen
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/stockphotos.fxml"));
				Parent root= (Parent) loader.load();
				Stage window=(Stage) LoginButton.getScene().getWindow();
				Scene scene = new Scene(root);
				window.setScene(scene);
				StockPhotosController stockController = loader.getController();
				stockController.start(userlist,username,"stock");
				window.show();
				return;
				

				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	
		
		//check
		boolean found=false;
		User current=null;
		for(int i=0;i<userlist.size();i++)
		{
			String check=userlist.get(i).getName();
			
			if(check.equals(username))
			{
				current=userlist.get(i);
				found=true;
				break;
			}
		}
		
		if(found==true)
		{
			//switch to user screen
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/RegularUserSystem.fxml"));
				Parent root= (Parent) loader.load();
				Stage window=(Stage) LoginButton.getScene().getWindow();
				Scene scene = new Scene(root);
				window.setTitle("Non-Admin User Subsystem");
				window.setScene(scene);
				UserController userController = loader.getController();
				
				
				userController.start(username,userlist);
				window.show();
				return;
				

				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
			
			
			
			
			
		}
		else
		{
			    Alert alert= new Alert(AlertType.INFORMATION);
				
				alert.setTitle("Username Input Error");
				alert.setHeaderText("Need to Enter a Valid Username to Login");
				String content="Entering a valid UserName is required to log in to the Photos application. You have not entered this, and your login request is therefore not allowed and is thus being cancelled";
				alert.setContentText(content);
				alert.showAndWait();
				return;
		}
		
		
	}
	/**
	 * updates userlist with new userlist
	 * @param newUserList
	 */
	public void updateUserList(ArrayList<User> newUserList)
	{
		this.userlist=newUserList;
	}
	
	
	
	
	
	
	
	

}
