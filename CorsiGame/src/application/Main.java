package application;
	
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			//GameManager manny = new GameManager(new PlayerData(), primaryStage);
			MainMenu menu = new MainMenu(primaryStage);
			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testEventHandler(Object obj)
	{
		System.out.println("foo");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
