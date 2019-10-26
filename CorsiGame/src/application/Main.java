package application;
	
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			
			
			//GameManager manny = new GameManager(new PlayerData(), primaryStage);
			//mainMenu menu = new MainMenu(primaryStage);
			//RegistrationMenu reggie = new RegistrationMenu(primaryStage);
			
			//PlayerData data = new PlayerData("User", "Pass", new Date(1,1,19), "City", "State", "Zip", "Diagnosis");
			//PlayerData data = new PlayerData();
			//FileManager fm = new FileManager();
			//fm.writeEncrypted(data.sendToString(), System.getProperty("user.dir") + "\\kung.foo");
			//data.readFromString(fm.decryptAndRead(System.getProperty("user.dir") + "\\kung.foo"));
			//ReturningPlayerMenu menu = new ReturningPlayerMenu(primaryStage);
			
			
			FileOutputStream os = new FileOutputStream("test.ser");
			ObjectOutputStream objStream = new ObjectOutputStream(os);
			objStream.writeObject(new FeatureTestClass());
			objStream.close();

			ObjectInputStream objInStream = new ObjectInputStream(new FileInputStream("test.ser"));
			
			FeatureTestClass foo = (FeatureTestClass) objInStream.readObject();
			System.out.println(foo.x);
			System.out.println(foo.y);
			objInStream.close();

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
