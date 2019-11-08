package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class GameReplayManager extends GameManager
{
	private ArrayList<TimestampedAction> actionQueue;
	private int actionIndex;
	private Stopwatch replayStopwatch;
	
	public GameReplayManager(Stage stage, GameData gameData, PlayerData playerData, ArrayList<PlayerData> players)
	{
		super(stage);
		this.playerData = playerData;
		this.players = players;
		this.gameData = gameData;
		actionQueue = gameData.getGameActions();
		actionIndex = 0;
		replayStopwatch = new Stopwatch();
		beginSimulation();
	}
	
	private void beginSimulation()
	{
		replayStopwatch.start();
		
		AnimationTimer gameTimer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if (actionQueue.get(actionIndex).getMSFromStart() >= replayStopwatch.getMSFromStart())
				{
					TimestampedAction currentAction = actionQueue.get(actionIndex);
					
					if (currentAction instanceof BlockClickedAction)
					{
						
					}
					else if (currentAction instanceof MouseClickAction)
					{
						
					}
					else if (currentAction instanceof MouseAction)
					{
						
					}
					else if (currentAction instanceof SequenceInitiationAction)
					{
					
					}
					else if (currentAction instanceof SubmitClickedAction)
					{
						
					}
					else if (currentAction instanceof GameEndAction)
					{
						
					}
				}
			}

		};
		
		gameTimer.start();
	}
}
