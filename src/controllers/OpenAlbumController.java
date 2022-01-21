package controllers;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import java.util.Calendar;

import java.util.Date;

/**
 * The Open Album Controller. Helps the User modify the photos in the album
 */
public class OpenAlbumController {
	@FXML Button LogoutButton;
	@FXML Button AddPhotoButton;
	@FXML Button DeletePhotoButton;
	@FXML Button CaptionPhotoButton;
	@FXML Button AddTagButton;
	@FXML Button DeleteTagButton;
	@FXML Button DisplayPhotoButton;
	@FXML Button PhotoSlideshowButton;
	@FXML Button MovePhotoButton;
	@FXML Button RefreshButton;
	@FXML Button ValidTagButton;
	@FXML TextField NewCaption, NewTagName, NewTagValue, DeleteTagName, DeleteTagValue, MoveAlbumName,ValidTagName;
	@FXML ChoiceBox<String> TagNameBox;
	@FXML ChoiceBox<String> TagNameBox2;
	
	private ArrayList<User> userlist;
	private User currentuser;
	private Photo selectedphoto;
	private int selectedindex;
	private Album currentalbum;
	private String displayUser;
	private String displayalbumname;
	Album temp=new Album("newname");
    
	@FXML 
	ListView<Photo> listView = new ListView<Photo>();
    ObservableList<Photo> items = FXCollections.observableArrayList (); 
    
    /**
     * Starts controller. Takes the currently focused upon user and populates 
     * a listview with the images and captions from the currently focused upon album
     * 
     * @param userlist           global list of users
     * @param currentusername     name of user that is currently focused on
     * @param albumname             name of album that is currently focused on
     */
    
	@FXML
	public void start(ArrayList<User> userlist, String currentusername, String albumname)
	{	
		this.displayUser = currentusername;
		this.displayalbumname = albumname;
		
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
	       
	      
	        ArrayList<String> validtags=currentuser.getvalidts();
	        
	        for(int i=0;i<validtags.size();i++)
	        {
	        	String y=validtags.get(i);
	        	TagNameBox.getItems().add(y);
	        	TagNameBox2.getItems().add(y);
	        	
	        }
		
			
	
	}
	
	/**
	 * refreshes the page populating the listview again
	 */
	
	@FXML
	public void Refresh() {
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
	 */
	
	
	public void Logout() {
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
	 * uses a filechooser to select an image file from the computer and adds that photo to the currently selected album of the currently selected user
	 *
	 * @throws IOException
	 */
	
	@FXML
	public void AddPhoto() throws IOException {
		Calendar cal = Calendar.getInstance();
	    

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		
		
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png",
						"*.PNG"),
				new ExtensionFilter("Bitmap Files", "*.bmp", "*.BMP"),
				new ExtensionFilter("GIF Files", "*.gif", "*.GIF"), new ExtensionFilter("JPEG Files", "*.jpg", "*.JPG"),
				new ExtensionFilter("PNG Files", "*.png", "*.PNG"));
		
		
		
		
		
		
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if(selectedFile==null)
		{
			Alert alert= new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Inputted Photo File Cannot be Null/Nonexistent");
			alert.setHeaderText("Inputted Photo File Cannot be Null/Nonexistent");
			String content="Inputted Photo File Cannot be Null/Nonexistent";
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		long lastModified = selectedFile.lastModified();
	    Date d= new Date(lastModified);
	    cal.setTime(d);
	    cal.set(Calendar.MILLISECOND,0);
	    
	    
		
		BufferedImage img = null;

		try {
		    img = ImageIO.read(selectedFile);
		   
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
			
			WritableImage wr = null;
	      if (img != null) {
	          wr = new WritableImage(img.getWidth(), img.getHeight());
	          PixelWriter pw = wr.getPixelWriter();
	          for (int x = 0; x < img.getWidth(); x++) {
	              for (int y = 0; y < img.getHeight(); y++) {
	                  pw.setArgb(x, y, img.getRGB(x, y));
	              }
	          }
	      }
	      
	      SerialImage serialImage1 = new SerialImage(wr);
	      Photo photo1 = new Photo(serialImage1, cal);
	      
	      
	      //duplicate check
	      boolean dup=false;
	      ArrayList<Photo> photoslist=currentalbum.getPhotos();
	      for(int i=0;i<photoslist.size();i++)
		   {
	    	  Photo phcheck=photoslist.get(i);
	    	  
	    	  if(photo1.equals(phcheck))
	    	  {
	    		  dup=true;
	    		  break;
	    	  }
				
		   }
	      
	      if(dup)
	      {
	    	  Alert alert= new Alert(AlertType.INFORMATION);	
			  alert.setTitle("Cannot Insert Duplicate Photo");
			  alert.setHeaderText("Cannot Insert Duplicate Photo");
			  String content="Album Cannot Have 2 Photos That Are Physically the Same, Please Select a Photo Not Already In Album to Add to It";
			  alert.setContentText(content);
			  alert.showAndWait(); 
			  return;
	      }
	      
	      //check if it's anywhere else
	      
	      //change in all other albums too
	      
	     
	     	 ArrayList<Album> albumlist=currentuser.getAlbums();
	     	 //look for photo
	     	 for(int a=0;a<albumlist.size();a++)
	     	 {
	     		 ArrayList<Photo> photolist2=albumlist.get(a).getPhotos();
	     		 for(int b=0;b<photolist2.size();b++)
	     		 {
	     			 //found
	     			 Photo check=photolist2.get(b);
	     			 if(check.equals(photo1))
	     			 {
	     				 String othercap=check.getCaption();
	     				 ArrayList<Tag> othertags=check.getTags();
	     				 photo1.setCaption(othercap);
	     				 photo1.setTags(othertags);
	     				 break;
	     			 }
	     		 }
	     	 }
	     	 
	      
				      
	      
	      
	      Album storage=new Album(currentalbum.getName());
	      storage.setPhotos(photoslist);
	      storage.setEarliest(currentalbum.getEarliest());
	      storage.setLatest(currentalbum.getLatest());
	      
	      
	      currentalbum.addPhoto(photo1);
	      currentuser.changeAlbum(storage, currentalbum);
	      
	      
		    //save new info
			currentuser.saveUser();

	      
	      
	      
	      //change in user list
	      
	      for(int i=0;i<userlist.size();i++)
	      {
	    	  String check=userlist.get(i).getName();
	    	  
	    	  if(check.equals(currentuser.getName()))
	    	  {
	    		  userlist.remove(i);
	    		  userlist.add(currentuser);
	    		  break;
	    	  }
	      }
	      
	      
	      
	      
	      String caption = "";
	      int blankindex = currentalbum.getPhotos().size()-1;
	      currentalbum.getPhoto(blankindex);
	      
	      if(currentalbum.getPhoto(blankindex).getCaption()==null)
	      {
	    	 currentalbum.getPhoto(blankindex).setCaption("");
	      }
	      
	      items.add(photo1);
		
	}
	
	
	
	/**
	 * Deletes the currently selected photo updates the userlists of changes to this user
	 * @throws IOException
	 */
	@FXML
	public void DeletePhoto() throws IOException {
	
		 if(selectedindex>-1 && selectedindex<items.size() && items.size()>0)
		 {
			 //delete from actual info
			 Photo p=items.get(selectedindex);
			 Album storage=new Album(currentalbum.getName());
		     storage.setPhotos(currentalbum.getPhotos());
		     storage.setEarliest(currentalbum.getEarliest());
		     storage.setLatest(currentalbum.getLatest());
			 currentalbum.deletePhoto(p);
			 currentuser.changeAlbum(storage, currentalbum);
			 
			 //save new info
			 currentuser.saveUser();

		      
		      
		      
		      //change in user list
		      
		      for(int i=0;i<userlist.size();i++)
		      {
		    	  String check=userlist.get(i).getName();
		    	  
		    	  if(check.equals(currentuser.getName()))
		    	  {
		    		  userlist.remove(i);
		    		  userlist.add(currentuser);
		    		  break;
		    	  }
		      }
			 
			 
			 
			 
			 //delete it from obs list
		     items.remove(selectedindex);
		 }
		  else
		  {
			  Alert alert= new Alert(AlertType.INFORMATION);
				
			  alert.setTitle("Cannot Delete From Empty Album");
			  alert.setHeaderText("Cannot Delete From Empty Album");
			  String content="Cannot Delete From Empty Album";
			  alert.setContentText(content);
			  alert.showAndWait(); 
			  return;
		  }
		
		
		
		
	}
	
	
	/**
	 * sets a caption to the caption field of the currently selected photo
	 * @throws IOException
	 */
	
	public void AddPhotoCaption() throws IOException {
		
		int index = listView.getSelectionModel().getSelectedIndex();
		
		
		Photo p=items.get(index);
		
		//look for it in the photos list
		
		int realphotosindex=0;
		ArrayList<Photo> photoslist=currentalbum.getPhotos();
		
		
		Album storage=new Album(currentalbum.getName());
	    storage.setPhotos(photoslist);
	    storage.setEarliest(currentalbum.getEarliest());
	    storage.setLatest(currentalbum.getLatest());
		
		currentalbum.deletePhoto(p);
		p.setCaption(NewCaption.getText());
		currentalbum.addPhoto(p);
		
		
		
		currentuser.changeAlbum(storage, currentalbum);
		int oldselect=selectedindex;
		
		items.set(oldselect,p);
		
		
		
		
		//perform save operation here
		
		 //save new info
		 currentuser.saveUser();

	     
	     
	     
	     //change in user list
	     
	     for(int i=0;i<userlist.size();i++)
	     {
	   	  String check=userlist.get(i).getName();
	   	  
	   	  if(check.equals(currentuser.getName()))
	   	  {
	   		  userlist.remove(i);
	   		  userlist.add(currentuser);
	   		  break;
	   	  }
	     }
	     
	     //change in all other albums too
	     
	        User u=currentuser;
	    	 String name=u.getName();
	    	 ArrayList<Album> albumlist=u.getAlbums();
	    	 //look for photo
	    	 for(int a=0;a<albumlist.size();a++)
	    	 {
	    		 ArrayList<Photo> photolist2=albumlist.get(a).getPhotos();
	    		 for(int b=0;b<photolist2.size();b++)
	    		 {
	    			 //found
	    			 Photo check=photolist2.get(b);
	    			 if(check.equals(p))
	    			 {
	    				 u.getAlbums().get(a).getPhotos().get(b).setCaption(NewCaption.getText());
	    				 u.saveUser();
	    			 }
	    		 }
	    	 }
	    	 
	    	 
	    
	     
	     
		 
		}
	
	/**
	 * Adds a valid tag to the list of valid tags of the current photo
	 * @throws IOException
	 */
	

@FXML public void ValidTag() throws IOException {
	
	String tname=ValidTagName.getText();
	
	if(tname==null || tname.isEmpty())
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Must Specify the Tag Name You Want to Add to List of Valid Tag Names");
		alert.setHeaderText("Must Specify the Tag Name You Want to Add to List of Valid Tag Names");
		String content="Must Specify the Tag Name You Want to Add to List of Valid Tag Names";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	ArrayList<String> validtags=currentuser.getvalidts();
	
	boolean found=false;
	
	for(int i=0;i<validtags.size();i++)
	{
		String c=validtags.get(i);
		if(c.equals(tname))
		{
			found=true;
			break;
		}
	}
	
	if(found)
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Tag Name Already Exists in Valid Tags");
		alert.setHeaderText("Tag Name Already Exists in Valid Tags");
		String content="Tag Name Already Exists in Valid Tags";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	currentuser.addtovtags(tname);
	currentuser.saveUser();
	
	//change in user list
    
    for(int i=0;i<userlist.size();i++)
    {
  	  String check=userlist.get(i).getName();
  	  
  	  if(check.equals(currentuser.getName()))
  	  {
  		  userlist.remove(i);
  		  userlist.add(currentuser);
  		  break;
  	  }
    }
	
	
	TagNameBox.getItems().add(tname);
	TagNameBox2.getItems().add(tname);
	
	
	//System.out.println(ValidTagName.getText());
}
@FXML

/**
 * adds the tags from textfields TagNameBox and NewTagValue
 * @throws IOException
 */

public void AddTag() throws IOException {
	
	int index = listView.getSelectionModel().getSelectedIndex();
	//System.out.println(currentalbum.getPhoto(index).getTagList().size());
	
	String newtagname = (String) TagNameBox.getValue();
	String newtagval=NewTagValue.getText();
	
	if(newtagname==null || newtagname.isEmpty() || newtagval==null || newtagval.isEmpty())
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Must Specify the Tag and Value You Want to Add to This Photo");
		alert.setHeaderText("Must Specify the Tag and Value You Want to Add to This Photo");
		String content="Must Specify the Tag and Value You Want to Add to This Photo";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	if(selectedphoto==null)
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Selected Photo Must Not Be Null");
		alert.setHeaderText("Selected Photo Must Not Be Null");
		String content="Selected Photo Must Not Be Null";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	
	boolean found=false;
	
	ArrayList<String> validtags=Tag.getposstags();
	
	for(int i=0;i<validtags.size();i++)
	{
		String c=validtags.get(i);
		if(c.equals(newtagname))
		{
			found=true;
			break;
		}
	}
	
	
	
	if(found==false)
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Not Valid Tag Name");
		alert.setHeaderText("Not Valid Tag Name");
		String content="Not Valid Tag Name";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	
	
	
	
	Tag curr= new Tag(newtagname, newtagval);
	
	boolean f2=false;
	
	
	Photo p=items.get(index);
	ArrayList<Tag> tg=p.getTags();
	
	for(int i=0;i<tg.size();i++)
	{
		Tag check=tg.get(i);
		
		if(check.tagEquals(curr))
		{
			f2=true;
			break;
		}
	}
	
	
	
	if(f2)
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Duplicate Tag");
		alert.setHeaderText("This Photo Already Has That Tag");
		String content="This Photo Already Has That Tag";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	
	
	
	
	
	
	
	
	ArrayList<Photo> photoslist=currentalbum.getPhotos();
	index = listView.getSelectionModel().getSelectedIndex();
	
	
	p=items.get(index);
	
	Album storage=new Album(currentalbum.getName());
    storage.setPhotos(photoslist);
    storage.setEarliest(currentalbum.getEarliest());
    storage.setLatest(currentalbum.getLatest());
	
	currentalbum.deletePhoto(p);
	p.addTag(curr);
	currentalbum.addPhoto(p);
	
	
	
	currentuser.changeAlbum(storage, currentalbum);
	
	
	
	
	
	//perform save operation here
	
	 //save new info
	currentuser.saveUser();

     
     
     
     //change in user list
     
     for(int i=0;i<userlist.size();i++)
     {
   	  String check=userlist.get(i).getName();
   	  
   	  if(check.equals(currentuser.getName()))
   	  {
   		  userlist.remove(i);
   		  userlist.add(currentuser);
   		  break;
   	  }
     }
     
     //change in all other albums too
     
        User u=currentuser;
    	 String name=u.getName();
    	 ArrayList<Album> albumlist=u.getAlbums();
    	 //look for photo
    	 for(int a=0;a<albumlist.size();a++)
    	 {
    		 ArrayList<Photo> photolist2=albumlist.get(a).getPhotos();
    		 for(int b=0;b<photolist2.size();b++)
    		 {
    			 //found
    			 Photo check=photolist2.get(b);
    			 if(check.equals(p))
    			 {
    				 ArrayList<Tag> tgs=u.getAlbums().get(a).getPhotos().get(b).getTagList();
    				 boolean f=false;
    				 for(int i=0;i<tgs.size();i++)
    				 {
    					 Tag ch=tgs.get(i);
    					 
    					 if(ch.equals(curr))
    					 {
    						 f=true;
    						 break;
    					 }
    					 
    				 }
    				 
    				 if(f==false)
    				 {
    					 u.getAlbums().get(a).getPhotos().get(b).addTag(curr);
    				 }
    				 
    				 
    				 
    				 u.saveUser();
    			 }
    		 }
    	 }
    	 
    	 
}

/**
 * Deletes the tag as specified in two textfields tagName and tagValue
 * @throws IOException
 */

@FXML
public void DeleteTag() throws IOException {
	
	int index = listView.getSelectionModel().getSelectedIndex();
	String deltname=(String)TagNameBox2.getValue();
	String deltvalue=DeleteTagValue.getText();
	
	if(deltname==null || deltname.isEmpty() || deltvalue==null || deltvalue.isEmpty())
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Tag Name and Tag Value Need to Be Filled In");
		alert.setHeaderText("Tag Name and Tag Value Need to Be Filled In");
		String content="Tag Name and Tag Value Need to Be Filled In";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	if(selectedphoto==null)
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Selected Photo Must Not Be Null");
		alert.setHeaderText("Selected Photo Must Not Be Null");
		String content="Selected Photo Must Not Be Null";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
    ArrayList<Tag> phs=selectedphoto.getTags();
	
	boolean found=false;
	Tag temp=new Tag(deltname,deltvalue);
	int tIndex=0;
	for(int i=0;i<phs.size();i++)
	{
		Tag t=phs.get(i);
		if(t.tagEquals(temp))
		{
			
			found=true;
			tIndex=i;
			break;
		}
	}
	
	if(found==false)
	{
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Tag Doesnt Exist in This Photo");
		alert.setHeaderText("Tag Doesnt Exist in This Photo");
		String content="Tag Doesnt Exist in This Photo";
		alert.setContentText(content);
		alert.showAndWait(); 
		return;
	}
	
	
	Album storage=new Album(currentalbum.getName());
    storage.setPhotos(currentalbum.getPhotos());
    storage.setEarliest(currentalbum.getEarliest());
    storage.setLatest(currentalbum.getLatest());
	
	
	
    Photo p=items.get(index);
	
	p.getTagList().remove(tIndex);
	
	currentalbum.deletePhoto(p);
	currentalbum.addPhoto(p);
	
	
	
	currentuser.changeAlbum(storage, currentalbum);
	
	
	
	
	
	//perform save operation here
	
	 //save new info
	currentuser.saveUser();

     
     
     
     //change in user list
     
     for(int i=0;i<userlist.size();i++)
     {
   	  String check=userlist.get(i).getName();
   	  
   	  if(check.equals(currentuser.getName()))
   	  {
   		  userlist.remove(i);
   		  userlist.add(currentuser);
   		  break;
   	  }
     }
     
     //change in all other albums too
     Tag tcheck=new Tag(deltname, deltvalue);
        User u=currentuser;
    	 String name=u.getName();
    	 ArrayList<Album> albumlist=u.getAlbums();
    	 //look for photo
    	 for(int a=0;a<albumlist.size();a++)
    	 {
    		 ArrayList<Photo> photolist2=albumlist.get(a).getPhotos();
    		 for(int b=0;b<photolist2.size();b++)
    		 {
    			 //found
    			 Photo check=photolist2.get(b);
    			 if(check.equals(p))
    			 {
    				 ArrayList<Tag> tl=u.getAlbums().get(a).getPhotos().get(b).getTagList();
    				 
    				 for(int i=0;i<tl.size();i++)
    				 {
    					 Tag current=tl.get(i);
    					 if(current.tagEquals(tcheck))
    					 {
    						 u.getAlbums().get(a).getPhotos().get(b).getTagList().remove(i);
    						 break;
    					 }
    				 }
    				 
    				 
    				 u.saveUser();
    			 }
    		 }
    	 }
    	 
	
	
	
	
	
	
	
	
}

/**
 * Returns index of tag in current Tag ArrayList
 * @param name: name of the Tag
 * @param value: value of the Tag
 * @param index: index of selected Photo
 * @return an integer that tells us the index of the specified Tag in the selected photo's Tag List
 */
public int tagIndex(String name, String value, int index) {
	for(int i = 0; i < currentalbum.getPhoto(index).tagSize(); i++) {
		if(currentalbum.getPhoto(index).getTag(i).getName().equalsIgnoreCase(name) && currentalbum.getPhoto(index).getTag(i).getvalue().equalsIgnoreCase(value)) {
			return i;
		}
		
	}
	
	
	return 0;
}

/**
 * displays the currently selected photo
 */

public void DisplayPhoto() {
	try {
	int index = listView.getSelectionModel().getSelectedIndex();
	FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/DisplayPhotoOutside.fxml"));
	Parent root= (Parent) loader.load();
	Stage window=(Stage) DisplayPhotoButton.getScene().getWindow();
	Scene scene = new Scene(root);
	window.setTitle("Display Photo Outside Page");
	window.setScene(scene);
	DisplayPhotoController displayController = loader.getController();

	displayController.start(currentalbum.getPhoto(index), userlist, displayUser, displayalbumname);
	window.show();
	return;
	} catch (Exception exception) {
		exception.printStackTrace();
	}
}

/**
 * launcher for a photoslideshow of all the pictures in the currently focused albums
 */

public void PhotoSlideshow() {
	try {
	FXMLLoader loader= new FXMLLoader(getClass().getResource("/views/PhotoSlideShow.fxml"));
	Parent root= (Parent) loader.load();
	Stage window=(Stage) PhotoSlideshowButton.getScene().getWindow();
	Scene scene = new Scene(root);
	window.setTitle("Photo Slideshow");
	window.setScene(scene);
	PhotoSlideshowController slideshowController = loader.getController();

	slideshowController.start(currentalbum, userlist);
	window.show();
	return;
	} catch (Exception exception) {
		exception.printStackTrace();
	}

}

/**
 * Takes the currently selected photo and attempts to move it to the new album who's name is specified
 * in a text field, if the newalbum does not exist or if theres a duplicate the operation will not be done
 * @throws IOException
 */
@FXML
public void MovePhoto() throws IOException{
	int index = listView.getSelectionModel().getSelectedIndex();
	String target = MoveAlbumName.getText();
	boolean workdone = false;
	for(int i = 0; i<userlist.size();i++) {
		for(int j = 0; j <userlist.get(i).getAlbums().size();j++) {
			if(userlist.get(i).getAlbums().get(j).getName().equals(target)) {
				workdone = true;
				//System.out.println("Target Album.Photos.size:"+userlist.get(i).getAlbums().get(j).getPhotos().size());
				//System.out.println("CurrentAlbum.Photos.size:"+currentalbum.getPhotos().size());
				//check for duplicate
				
				userlist.get(i).getAlbums().get(j).getPhotos().add(currentalbum.getPhoto(index));
				currentalbum.getPhotos().remove(index);
				
				//System.out.println("Target Album.Photos.size:"+userlist.get(i).getAlbums().get(j).getPhotos().size());
				//System.out.println("CurrentAlbum.Photos.size:"+currentalbum.getPhotos().size());

			}
		}
	}
	if(workdone == false) {
		System.out.println("Album Did Not Exist");
	}
	

}

/**
 * Determines if a photo is in an album
 * @param a: An Album
 * @param t: a Photo
 * @return a boolean that tells us if t is in Album a
 */

public boolean isDuplicate(Album a, Photo t) {
	for(int i = 0; i < a.getPhotos().size(); i++) {
		if(a.getPhoto(i).equals(t)) {
			return true;
		}
		
	}
	
	
	return false;
}


}

















