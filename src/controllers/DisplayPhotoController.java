package controllers;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

public class DisplayPhotoController {
	@FXML ImageView image;
	@FXML TextField CaptionSpot,TagSpot;
	@FXML Button backTo;
	@FXML
	ListView<String> listView = new ListView<String>();
    ObservableList<Photo> items = FXCollections.observableArrayList (); 
    
    private ArrayList<User> userlist;
	private String currentuser;
	private String currentalbum;
	/**
	 * starts up photo display which displays the image, captions, and tags of a single photo
	 * @param p                The currently selected photo
	 * @param userlist         the global userlist list
	 * @param currentusername  the current username that the photo belongs to
	 * @param albumname        the current album that the photo belongs to
	 */
	public void start (Photo p, ArrayList<User> userlist, String currentusername, String albumname) {
		this.userlist = userlist;
		this.currentuser = currentusername;
		this.currentalbum = albumname;
		
	image.setImage(p.getSerialImage().getImage());
	CaptionSpot.setText(p.getCaption());
	TagSpot.setText(p.tagsToString());
	
	
	
	}
	
	
	/**
	 * Goes back to login screen
	 */
	@FXML
	public void Back() {
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/login.fxml"));
		Parent root= (Parent) loader.load();
		Stage window=(Stage) backTo.getScene().getWindow();
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
