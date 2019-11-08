package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameReplayManager extends GameManager
{
	private final int CURSOR_RADIUS = 4;
	
	private ArrayList<TimestampedAction> actionQueue;
	private int actionIndex;
	private Stopwatch replayStopwatch;
	private Circle cursor;
	
	public GameReplayManager(Stage stage, GameData gameData, PlayerData playerData, ArrayList<PlayerData> players)
	{
		super(stage);
		this.playerData = playerData;
		this.players = players;
		this.gameData = gameData;
		
		actionQueue = gameData.getGameActions();
		actionIndex = 0;
		replayStopwatch = new Stopwatch();
		cursor = new Circle(CURSOR_RADIUS);
		
		gameObjects.getChildren().add(cursor);
		
		beginSimulation();
	}
	
	private void beginSimulation()
	{
		replayStopwatch.start();
		System.out.println(actionQueue.size());
		
		AnimationTimer gameTimer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				System.out.println(actionQueue.get(actionIndex).getMSFromStart());
				if (actionQueue.get(actionIndex).getMSFromStart() >= replayStopwatch.getMSFromStart())
				{
					TimestampedAction currentAction = actionQueue.get(actionIndex);
					
					if (currentAction instanceof BlockClickedAction)
					{
						System.out.println("BlockAction");
					}
					else if (currentAction instanceof MouseClickAction)
					{
						cursor.setCenterX(((MouseClickAction) currentAction).getMousePosition().getX());
						cursor.setCenterY(((MouseClickAction) currentAction).getMousePosition().getY());
						System.out.println(((MouseClickAction) currentAction).getMousePosition().getX());
					}
					else if (currentAction instanceof MouseAction)
					{
						System.out.println("MouseAction");
					}
					else if (currentAction instanceof SequenceInitiationAction)
					{
						System.out.println("InitAction");
					}
					else if (currentAction instanceof SubmitClickedAction)
					{
						System.out.println("SubmitAction");
					}
					else if (currentAction instanceof GameEndAction)
					{
						System.out.println("GameEnd");
					}
					
					++actionIndex;
				}
			}

		};
		
		gameTimer.start();
	}
}
