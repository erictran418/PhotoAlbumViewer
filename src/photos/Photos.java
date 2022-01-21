package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import controllers.*;


/**
 * This is the Photos Class. It is used to control and facilitate the entire program
 */


public class Photos extends Application {
	
	/**
	 * Start method to setup the login screen
	 * @param mainstage: the main Stage of the JavaFX program
	 */

	@Override
	public void start(Stage mainStage) throws Exception {
		
		//create new data directory
		
		File Dir = new File("data");
		if (!Dir.exists()){
		    Dir.mkdirs();
		}
		
		
		
		
		
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/login.fxml"));
		
		BorderPane root = (BorderPane)loader.load();

	    

		LoginController startScreenController = loader.getController();
		startScreenController.start();
		Scene scene = new Scene(root);

		
        
		

		mainStage.setScene(scene);
		mainStage.setTitle("Login Page");
		mainStage.setResizable(false);
		mainStage.show();
	}
	
	/**
	 * Main method; launches the program
	 * @param args: an Array of Strings
	 */


	public static void main(String[] args) {
		
		launch(args);
		
	}

}
