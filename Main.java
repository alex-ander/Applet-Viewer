package application;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.sun.glass.ui.CommonDialogs.ExtensionFilter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class Main extends Application {
	
	
	@FXML
	
	private Button open;
	private Scene scene;
	private TextField textField;
	private java.util.List<String> list;
	private File selectedDirectory;
	private Button execute;
	public static void main(String[] args) {
		launch(args);
		
		
		
}		


	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Main.fxml"));
			scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("Applet Viewer");
		    textField = (TextField) scene.lookup("#textField");
		    textField.setEditable(false);
			execute = (Button) scene.lookup("#execute");
			execute.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if (selectedDirectory!=null) {
						cmd(selectedDirectory);
					}
					if (!textField.getText().isEmpty()) {
						cmd(new File(textField.getText()));
					}
					else if (textField.getText().isEmpty()) {
						System.err.println("Keine Datei gewählt.");
					}
				}
			});
		    
		    open = (Button) scene.lookup("#open");
			
			open.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					FileChooser fc = new FileChooser();
					
					//fc.setSelectedExtensionFilter(new ExtensionFilter("Applet-Dateien", list));
					//fc.setTitle("Durchsuchen");
					
					
					FileChooser chooser = new FileChooser();
				    chooser.setTitle("Öffnen");
				   
				    ArrayList<String> list2 = new ArrayList<String>();

					list2.add("*.html");
					list2.add("*.jar");
					//fc.setSelectedExtensionFilter(new ExtensionFilter("Applet-Dateien", list));
					
					 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Java Applet-Dateien (*.html)", "*.html");
					 chooser.getExtensionFilters().add(extFilter);
					 selectedDirectory = chooser.showOpenDialog(scene.getWindow());
					 if (selectedDirectory != null) {
					 textField.setText(selectedDirectory.getPath());
					System.out.println(selectedDirectory);
					 }
					 else {
						 System.err.println("Keine Datei ausgewählt.");
					 }
					
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cmd(File url) {
		
		ProcessBuilder builder = new ProcessBuilder( "cmd", "/k", Main.class.getResource("bin\\appletviewer.exe").toString().replace("file:/", "").replace("%5c", "/") + " \"" + url.getPath() + "\"");
		System.out.println(builder.directory());
		
		
				
		try {
	        Process r = builder.start();
	        
	        System.out.println(r);
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
