
//Cliff Grandin
//NetID: cjg189
//
//Kartik Sonavane
//NetID: ks1324
//

package gui.app;



import java.io.IOException;

import gui.view.SongLibController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SongLib extends Application {

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(); 
		
		
		loader.setLocation(getClass().getResource("/gui/view/SongLib1.fxml")); 

		
		AnchorPane root = (AnchorPane)loader.load();
		
		SongLibController list = loader.getController();
		
		list.start(primaryStage);
		//Scene scene = new Scene(root, 600, 375); 
		Scene scene = new Scene(root);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene); 
		//primaryStage.setOnHidden(e -> SongLibController.exit());
		primaryStage.setOnHidden(e -> {
			try {
				SongLibController.exit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    Platform.exit();
		    System.exit(0);
		});
		primaryStage.show();
		
		
		
	}
	
	public static void main(String[] args) { 
		launch(args);
	}
	

}

