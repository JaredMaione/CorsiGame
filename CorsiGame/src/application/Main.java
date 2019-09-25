package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stopwatch stopwatch = new Stopwatch();
			CorsiBlock testBlock = new CorsiBlock();
			testBlock.setHeight(100);
			testBlock.setWidth(100);
			testBlock.setX(50);
			testBlock.setY(50);
			root.getChildren().add(testBlock);
			AnimationTimer animTimer = new AnimationTimer()
					{
						@Override
						public void handle(long arg0) 
						{
							testBlock.setLit(!testBlock.isLit());
						}

					};
			animTimer.start();
			testBlock.setFill(Color.RED);
			testBlock.setFill(Color.GREEN);
			testBlock.blink(3);
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
