package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stopwatch stopwatch = new Stopwatch();
			stopwatch.start();
			stopwatch.stop();
			
			ElapsedTime time = new ElapsedTime(1002);
			System.out.println(time.getMinutes());
			System.out.println(time.getSeconds());
			System.out.println(time.getMS());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
