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
			CorsiBlock testBlock = new CorsiBlock(2, 2, 50);
			for (CorsiBlock block : CorsiBlockGenerator.generateBlocks(4, (int) scene.getWidth(), (int) scene.getHeight()))
			{
				root.getChildren().add(block);
			}
			/*AnimationTimer animTimer = new AnimationTimer()
					{
						@Override
						public void handle(long arg0) 
						{
							testBlock.setLit(!testBlock.isLit());
						}

					};
			animTimer.start();*/

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
