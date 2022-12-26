
//Cliff Grandin
//NetID: cjg189
//
//Kartik Sonavane
//NetID: ks1324
//

package gui.view;


import java.util.*;
import java.io.*;
import java.lang.*;
import java.net.URL;
import java.util.Scanner;

//import application.Song;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;





public class SongLibController  {
	
    @FXML
    private ListView<Song> listView;
    
    
    @FXML
    private TextField text2;

    @FXML
    private TextField text3;

    @FXML
    private TextField text4;

    @FXML
    private TextField text1;
    

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
    
    @FXML
    private Button cancelButton;
    
    
    @FXML
    private Button saveButton;
    
    Stage mainStage;
    
    //These four static strings are for the .txt file
    static String name;
	static String artist;
	static String album;
	static String year;
    
	
	static final String textFile = "SongLibSaveFile.txt";
    
   
    private static ObservableList<Song> obsList;
    
    public Song newSong;
    
    public void start(Stage mainStage) throws FileNotFoundException {
    	
 	
   
    }
   

    @FXML
    void addSong(ActionEvent event) throws IOException {
    	boolean ok = listView.getSelectionModel().equals(text1.getText()); 
		int index = listView.getSelectionModel().getSelectedIndex();
		Song item = listView.getSelectionModel().getSelectedItem(); 
    	
    	//disable delete and edit button if list is empty
    	if(obsList.isEmpty()) {
    		System.out.println("List Is Empty");
    		deleteButton.setDisable(true);
    		editButton.setDisable(true);
    	}
    	
    	//if name and artist is blank; warning presented
    	if(text1.getText().trim().isEmpty() || text2.getText().trim().isEmpty()){
    		
    		alertDialogue("Invalid Input", "Warning: Both Song Name/Artist Name Required!");
    		
    		if(item == null) {
    			//setTextFieldsToNull();
    			text1.setText("");
        		text1.setPromptText("");
        		text2.setText("");
        		text2.setPromptText("");
        		text3.setText("");
        		text3.setPromptText("");
        		text4.setText("");
        		text4.setPromptText("");
        		
    		}else {
    			text1.setText("");
        		text1.setPromptText(item.name);
        		text2.setText("");
        		text2.setPromptText(item.artist);
        		text3.setText("");
        		text3.setPromptText(item.album);
        		text4.setText("");
        		text4.setPromptText(item.year);
    		}
    		
    	
    		//Checks if both name in artist are in the same newSong Object
    	}else if(isNameArtistInList(text1.getText().trim(),text2.getText().trim()  )){
    		System.out.println("Duplicate Entry!");
    		
    		alertDialogue("Duplicate Input Error!", "Duplicate: Name & Artist Are Currently In List And Are Not Allowed!");
    		
    		text1.setText("");
    		text1.setPromptText(item.name);
    		text2.setText("");
    		text2.setPromptText(item.artist);
    		text3.setText("");
    		text3.setPromptText(item.album);
    		text4.setText("");
    		text4.setPromptText(item.year);
    		
    		
    	
    		//This checks if the string is a negative and pops a dialogue box when true
    	}else if(!(text4.getText().trim().isEmpty() ) && isNegative(text4.getText().trim())){	
    	
    		alertDialogue("Invalid Input", "Error! Please Enter A Non-Negative Number In Year Column!");
    		
    		text4.setText("");
    		
    		System.out.println("The number is negative");
    	}else {
    		
    		/////////////////////////////////////////////
    		Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Add Song");
        	alert.setHeaderText("Do you want to add song!");
        	
        	//create two buttons with Cancel and OK
        	ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        	ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

        	//inserts those buttons in dialogue
            alert.getButtonTypes().setAll(cancelButton, okButton);
            
            
            //sets data that was pressed into the dialogue box for confirmation
        	alert.setContentText("Song Name:\t" + text1.getText() + "\n" + 
        			"Artist Name:\t" + text2.getText() + "\n" + 
        			"Album Name:\t" + text3.getText() + "\n" + 
        			"Release Year:\t" + text4.getText());
        	
        	
        	
        	Optional<ButtonType> result = alert.showAndWait();
        	
        	if(result.get() == okButton) {
        		newSong = new Song(text1.getText().trim(), text2.getText().trim(),text3.getText().trim(), text4.getText().trim());
            	obsList.add(newSong);
            	//obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER));
            	obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
            			.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
            	
        		listView.setItems(obsList);
        		listView.getSelectionModel().select(newSong);
        		text1.setText("");
        		text1.setPromptText(newSong.name);
        		text2.setText("");
        		text2.setPromptText(newSong.artist);
        		text3.setText("");
        		text3.setPromptText(newSong.album);
        		text4.setText("");
        		text4.setPromptText(newSong.year);
        		
        		deleteButton.setDisable(false);
            	editButton.setDisable(false);
            	
            	saveFile();
        	}
        	


    	}

    }

    @FXML
    void deleteSong(ActionEvent event) throws IOException {
    	
    	
    	if(obsList.isEmpty()) {
    		System.out.println("List Is Empty");
    		deleteButton.setDisable(true);
    		
    	}else{
    		Song item = listView.getSelectionModel().getSelectedItem(); 
    		int index = listView.getSelectionModel().getSelectedIndex();
        	
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Delete Song");
        	alert.setHeaderText("Warning: You are about to Delete!");
        	
        	//create two buttons with Cancel and OK
        	ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        	ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

        	//inserts those buttons in dialogue
            alert.getButtonTypes().setAll(cancelButton, okButton);
            
            
            //sets data that was pressed into the dialogue box for confirmation
        	alert.setContentText("Song Name:\t" + item.name + "\n" + 
        			"Artist Name:\t" + item.artist + "\n" + 
        			"Album Name:\t" + item.album + "\n" + 
        			"Release Year:\t" + item.year);
        	
        	
        	
        	Optional<ButtonType> result = alert.showAndWait();
        	
        	//if ok is pressed, it removes, selects the previous and input
        	//prompt into textfields
            if(result.get() == okButton)
            {
            	obsList.remove(index);
            	System.out.println("Deleting Item: " + item);
            	//obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER));
            	obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
            			.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
            	if(obsList.isEmpty()) {
            		//disables deleteButton when list is empty
            		System.out.println("List Is Empty");
            		deleteButton.setDisable(true);
            		editButton.setDisable(true);
            		text1.setText("");
            		//text1.setPromptText("Enter Song Name");
            		text1.setPromptText("");
            		text2.setText("");
            		text2.setPromptText("");
            		//text2.setPromptText("Enter Artist Name");
            		text3.setText("");
            		text3.setPromptText("");
            		//text3.setPromptText("Enter Album Name");
            		text4.setText("");
            		text4.setPromptText("");
            		//text4.setPromptText("Enter Year");
            		
            	}else {
            	//item = listView.getSelectionModel().getSelectedItem(); 
            		listView.getSelectionModel().select(index);
            		item = listView.getSelectionModel().getSelectedItem(); 
            		 
            	text1.setText("");
        		text1.setPromptText(item.name);
        		text2.setText("");
        		text2.setPromptText(item.artist);
        		text3.setText("");
        		text3.setPromptText(item.album);
        		text4.setText("");
        		text4.setPromptText(item.year);
        		//listView.getSelectionModel().select(item);
        		obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
        				.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
        		//obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER));
            	//obsList.sort(Comparator.comparing(Song::getName));
            	saveFile();
            	
            	}
            	
            }
    	}
    	
    	
    	

    }

    @FXML
    void editSong(ActionEvent event) {
    	//text2.setDisable(false);
    	//editButton.setText("SAVE");
    	editButton.setVisible(false);
    	saveButton.setVisible(true);
    	cancelButton.setVisible(true);
    	
    	
    	Song item = listView.getSelectionModel().getSelectedItem(); 
    	int index = listView.getSelectionModel().getSelectedIndex();
    	text1.setText(item.name);
    	text2.setText(item.artist);
    	text3.setText(item.album);
    	text4.setText(item.year);
    	
    	//Sets the list to unclickable when in update mode
    	listView.setMouseTransparent( true );
    	listView.setFocusTraversable( false );
    	
    	
    	//disable delete button
    	deleteButton.setDisable(true);
    	addButton.setDisable(true);
    	
    	
    	
    	
    	
    	
    }
    
    @FXML
    void cancelEntry(ActionEvent event) {
    	
    	editButton.setVisible(true);
    	saveButton.setVisible(false);
    	cancelButton.setVisible(false);
    	Song item = listView.getSelectionModel().getSelectedItem(); 
    	int index = listView.getSelectionModel().getSelectedIndex();
    	
    	text1.setText("");
		text1.setPromptText(item.name);
		text2.setText("");
		text2.setPromptText(item.artist);
		text3.setText("");
		text3.setPromptText(item.album);
		text4.setText("");
		text4.setPromptText(item.year);
		
		listView.setMouseTransparent( false );
    	listView.setFocusTraversable( true );
    	
    	deleteButton.setDisable(false);
    	addButton.setDisable(false);
		
    }
    
    @FXML
    void mouseClick(MouseEvent event) {
    	
    	if(obsList.isEmpty()) {
    		
    	}else { 
    		Song item = listView.getSelectionModel().getSelectedItem(); 
        	int index = listView.getSelectionModel().getSelectedIndex();
        	
        	text1.setPromptText(item.name);
        	text2.setPromptText(item.artist);
        	text3.setPromptText(item.album);
        	text4.setPromptText(item.year);
        	listView.getSelectionModel().select(index);
    	}
    	
    }
    
    
    @FXML
    void saveEditSong(ActionEvent event) throws IOException {
    	//System.out.println("Saving...");
    	
    	Song item = listView.getSelectionModel().getSelectedItem(); 
    	int index = listView.getSelectionModel().getSelectedIndex();
    	
    	// if name and artist textfield is empty then alert appears
    	if(text1.getText().trim().isEmpty() || text2.getText().trim().isEmpty()){
    		
    		System.out.println("Empty Text!\nUnable to Save!");
    		
    		alertDialogue("Invalid Input", "Warning: Both Song Name/Artist Name Required!");
    		
    		
    		// function if name and artist are in the same newSong object than return true
    	}else if(isNameArtistInList(text1.getText().trim(),text2.getText().trim()  )){
    			
    			//if index selected is being update than it is ok to have a duplicate because
    			//item is going to be replace with same data but updated newSong object 
    		if(index == returnIndex(text1.getText().trim(), text2.getText().trim()) ) {
    		
    			System.out.println("Can be editted");
    			System.out.println("Index: "+ index );
    			System.out.println("Text1: "+returnIndex(text1.getText().trim(), text2.getText().trim()) );

    			obsList.remove(index);
            	newSong = new Song(text1.getText().trim(), text2.getText().trim(),text3.getText().trim(), text4.getText().trim());
            	obsList.add(newSong);
            	//obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER));
            	
            	// sorts by songName first than by artist if the song name is the same
            	obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
            			.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
            	
            	listView.setItems(obsList);
        		listView.getSelectionModel().select(newSong);
        		
        		text1.setText("");
        		text1.setPromptText(newSong.name);
        		text2.setText("");
        		text2.setPromptText(newSong.artist);
        		text3.setText("");
        		text3.setPromptText(newSong.album);
        		text4.setText("");
        		text4.setPromptText(newSong.year);
            	
        		editButton.setVisible(true);
            	saveButton.setVisible(false);
            	cancelButton.setVisible(false);
            	
            	deleteButton.setDisable(false);
            	addButton.setDisable(false);
            	
            	listView.setMouseTransparent( false );
            	listView.setFocusTraversable( true );
            	saveFile();
        		
    		}else {
    			
    			alertDialogue("Duplicate Input Error!", "Duplicate: Name & Artist Are Currently In List And Are Not Allowed!");
        	
    	        	
    	        System.out.println("Duplicate Entry!");
    	    	System.out.println("Unable to Save!");
    	    		
        			
    	    		
    		}
    		
    		
    	}else {
    		
    		//removes previous, adds new object and sorts back to position
        	obsList.remove(index);
        	newSong = new Song(text1.getText().trim(), text2.getText().trim(),text3.getText().trim(), text4.getText().trim());
        	obsList.add(newSong);
        	//obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER));
        	obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
        			.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
        	
        	listView.setItems(obsList);
    		listView.getSelectionModel().select(newSong);
    		
    		text1.setText("");
    		text1.setPromptText(newSong.name);
    		text2.setText("");
    		text2.setPromptText(newSong.artist);
    		text3.setText("");
    		text3.setPromptText(newSong.album);
    		text4.setText("");
    		text4.setPromptText(newSong.year);
        	
    		editButton.setVisible(true);
        	saveButton.setVisible(false);
        	cancelButton.setVisible(false);
        	
        	deleteButton.setDisable(false);
        	addButton.setDisable(false);
        	
        	listView.setMouseTransparent( false );
        	listView.setFocusTraversable( true );
        	saveFile();
    		
    	}
    	
    	
    	
    }
    
    @FXML
    public static void exit() throws IOException {
       
    	
    	saveFile();
    	
    	
       //Made function because code is used on all buttons and exit
     /*  System.out.println("Saving...");
       
       
       File file = new File(textFile);
	      
       file.createNewFile();
       
       FileWriter writer = new FileWriter(file); 
	      
	   //for loop to iterate through the list and prints to text file  
	   for (Song song : obsList) {
		   writer.write(song.fileWriterToString());
	   }
	   
	   writer.flush();
	   writer.close();
	   
	   System.out.println("Saving Completed!");
*/
       
    }
    
    public static void saveFile() throws IOException {
    	System.out.println("Saving...");
        
        
        File file = new File(textFile);
 	      
        file.createNewFile();
        
        FileWriter writer = new FileWriter(file); 
 	     
        
 	   //for loop to iterate through the list and prints to text file  
 	   for (Song song : obsList) {
 		   writer.write(song.fileWriterToString());
 	   }
 	   
 	   writer.flush();
 	   writer.close();
 	   
 	   System.out.println("Saving Completed!");
    }
    
    public static boolean isNegative(String input) {
    	
    	if(input.charAt(0) == ('-')){
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    
    public static boolean isNameArtistInList(String name, String artist) {
    	
    	for (Song song : obsList) {
    		if(song.name.equalsIgnoreCase(name) && song.artist.equalsIgnoreCase(artist) ){
    			return true;
    		}
    		
  	   }
    	return false;
    }
	

    

    public static int returnIndex(String name, String artist) {
    	int index = 0;
    	for (Song song : obsList) {
    		if(song.name.equalsIgnoreCase(name) && song.artist.equalsIgnoreCase(artist) ){
    			
    			return index;
    		}
    		index++;
    		
  	   }
    	return index;
    }
    
    
    @FXML
    public void alertDialogue(String title, String headerText) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(headerText);


    	Optional<ButtonType> result = alert.showAndWait();
    }
    
    @FXML
    public void setTextFieldsToNull() {
    	text1.setText("");
		text1.setPromptText("");
		text2.setText("");
		text2.setPromptText("");
		text3.setText("");
		text3.setPromptText("");
		text4.setText("");
		text4.setPromptText("");
    }
   
   
   @FXML
    public void initialize() throws FileNotFoundException { 	
	 obsList = FXCollections.observableArrayList();
    	File fil = new File(textFile);
    	
    	
    	if(fil.exists()) { 
    		System.out.println("FILE EXISTS!!");
    		System.out.println("Loading Data ...");
    		Scanner scanner = new Scanner(fil);
		      while(scanner.hasNext()) {
		    	  
		    	  String string = scanner.nextLine();
		    	  
		    	  
		    	  if(string.contains("name:")) {
		    		  
		    		  //because "name:" is in .txt file
		    		  // it reads passed that offset of string and 
		    		  //reads the data. Below are the same
		    		  name = string.substring(6);
		    		  //System.out.println(name);
		    		
		    	  }else if(string.contains("artist:") ){
		    		  artist = string.substring(8);
		    		  //System.out.println(artist);
		    		
		    	  }else if(string.contains("album:") ){
		    		  album = string.substring(7);
		    		  //System.out.println(album);
		    		
		    	  }else if(string.contains("year:") ){
		    		  year = string.substring(6);
		    		  
		    		  //System.out.println(year+"\n");
		    		  
		    		  //Once it goes through the first data block up to year
		    		  //it adds it to newSong Object
		    		  //Make sure to check it is trimmed
		    		  newSong = new Song(name.trim(), artist.trim(), album.trim(), year.trim());
		    		  obsList.add(newSong);
		    		  
		    		  
		 		
		    	  }
		    	
		    	
		      }
		      scanner.close();
		      //obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER));
		      obsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
		    		  .thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
		      listView.setItems(obsList); 
		      
		    // at beginning of program select the first item in the list
		  	listView.getSelectionModel().select(0);
		  	

		  	
		  	Song item = listView.getSelectionModel().getSelectedItem(); 
		  	int index = listView.getSelectionModel().getSelectedIndex();
		  	
		  	// set the data in each textfield as prompt
		  	//so user can't add to list
		  	if(item == null) {
		  		text1.setPromptText("");
		  		text2.setPromptText("");
		  		text3.setPromptText("");
		  		text4.setPromptText("");
		  		editButton.setDisable(true);
		  		deleteButton.setDisable(true);
		  	}else {
		  		text1.setPromptText(item.name);
		  		text2.setPromptText(item.artist);
		  		text3.setPromptText(item.album);
		  		text4.setPromptText(item.year);
		  	}
		  
		  
		  	
		  	cancelButton.setVisible(false);
		  	saveButton.setVisible(false);
		  	
		  	System.out.println("Loading Data Completed!");
		      
	 
    	}else {
    		
    		System.out.println("FILE DOESN'T EXISTS!!");
    		obsList = FXCollections.observableArrayList();
    		cancelButton.setVisible(false);
    		saveButton.setVisible(false);
    		deleteButton.setDisable(true);
    		editButton.setDisable(true);
    	}
    }
    

}

