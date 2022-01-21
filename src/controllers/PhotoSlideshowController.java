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

public class PhotoSlideshowController {
	@FXML ImageView image;
	@FXML TextField CaptionSpot,TagSpot;
	@FXML Button backTo;
	@FXML Button CycleForward;
	@FXML Button CycleBack;
	
	private Album temp;
    private ArrayList<User> userlist;
    private int cycler = 0;
/**
 * Starts the photoslideshow, creates a manual slide show consisting of the currently selected album
 * can cycle forwards and backwards, exits back to login screen
 * @param a            currently focused on album
 * @param userlist     global userlist
 */
	public void start(Album a, ArrayList<User> userlist) {
	this.userlist = userlist;
	this.temp = a;
	image.setImage(a.getPhoto(0).getSerialImage().getImage());
	CaptionSpot.setText(a.getPhoto(0).getCaption());
	TagSpot.setText(a.getPhoto(0).tagsToString());
	}
	/**
	 * cycles photos forward using int max to keep the indexes from going out of bound
	 * iterrates int cycler
	 * sets the imageview to the photo at the index of cycler
	 * sets caption and tag fields to that current photo's value as well
	 */
	@FXML
	public void forward() {
	cycler++;
	
	int max = temp.getPhotos().size();
	if(cycler == max || cycler > max) {
		cycler = 0;
	}
	image.setImage(temp.getPhoto(cycler).getSerialImage().getImage());
	CaptionSpot.setText(temp.getPhoto(cycler).getCaption());
	TagSpot.setText(temp.getPhoto(cycler).tagsToString());
	
	}
	/**
	 * cycles backwards in much the same way as above, only in reverse
	 */
	@FXML void backward() {
		cycler--;
		int max = temp.getPhotos().size();
		if(cycler < 0) {
		cycler = max-1;
		}
		image.setImage(temp.getPhoto(cycler).getSerialImage().getImage());
		CaptionSpot.setText(temp.getPhoto(cycler).getCaption());
		TagSpot.setText(temp.getPhoto(cycler).tagsToString());

	}
	/**
	 * goes back to login screen
	 */
	@FXML
	public void Back(){
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
