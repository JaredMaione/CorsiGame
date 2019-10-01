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
			Group root = new Group();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stopwatch stopwatch = new Stopwatch();
			CorsiBlock testBlock = new CorsiBlock(2, 2, 50);
			ArrayList<CorsiBlock> blocks = CorsiBlockGenerator.generateBlocks(4, (int) scene.getWidth(), (int) scene.getHeight());
			for (CorsiBlock block : blocks)
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
			CorsiSequencePlayer player = new CorsiSequencePlayer();
			player.playSequence(blocks, 1, 1);
			blocks.get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, e -> testEventHandler(e.getSource()));
			

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
