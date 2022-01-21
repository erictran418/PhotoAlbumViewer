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
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.Calendar;

public class StockPhotosController {
	private ArrayList<User> userlist;
	private User currentuser;
	private Photo selectedphoto;
	private int selectedindex;
	private Album currentalbum;
	Album temp=new Album("newname");
	@FXML Button LogoutButton;
	@FXML 
	ListView<Photo> listView = new ListView<Photo>();
    ObservableList<Photo> items = FXCollections.observableArrayList (); 
/**
 * Starts the stockphotos screen with a default stock user and stock album
 * populates a listview with the images and captions from that stock album
 * @param userlist                 the global userlist
 * @param currentusername          the currentusername which is the default stock in this case
 * @param albumname                the current albumname which is the default stock in this case
 */
	@FXML
	public void start(ArrayList<User> userlist, String currentusername, String albumname)
	{
        this.userlist=userlist;
	    
	    for(int i=0;i<userlist.size();i++)
	    {
	    	String check=userlist.get(i).getName();
	    	
	    	if(check.equals(currentusername))
	    	{
	    		currentuser=userlist.get(i);
	    		break;
	    	}
	    }
		
	    ArrayList<Album> allist= currentuser.getAlbums();
		
	    for(int i=0;i<allist.size();i++)
	    {
	    	String check=allist.get(i).getName();
	    	
	    	if(check.equals(albumname))
	    	{
	    		currentalbum=allist.get(i);
	    		break;
	    	}
	    }
		
	    ArrayList<Photo> l=currentalbum.getPhotos();
		for(int i=0;i<l.size();i++)
		{
			items.add(l.get(i));
		}
		
		
		
		
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
		        

	    	    
	          listView.setItems(items);
	          
	          listView.getSelectionModel().selectedIndexProperty()
	  		.addListener((obs, prevSelec, currSelec) -> {
	  			if ((int)currSelec > -1) {
	  				selectedphoto = items.get((int) currSelec);
	  				selectedindex=(int) currSelec;
	  			}
	  		});
	       
	      
	        
	        
	        
		
			
	
	}
	/**
	 * returns to the login screen
	 * 
	 */
	@FXML
	public void Logout(){
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
