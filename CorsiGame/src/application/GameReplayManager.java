package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameReplayManager extends GameManager
{
	private final int CURSOR_RADIUS = 4;

	private ArrayList<TimestampedAction> actionQueue;
	private ArrayList<CorsiBlock> rebuiltBlocks;
	private int actionIndex;
	private Stopwatch replayStopwatch;
	private Circle cursor;

	public GameReplayManager(Stage stage, GameData gameData, PlayerData playerData, ArrayList<PlayerData> players)
	{
		super(stage);
		this.playerData = playerData;
		this.players = players;
		this.gameData = gameData;
		
		rebuiltBlocks = new ArrayList<CorsiBlock>();

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

		AnimationTimer gameTimer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{

				if (replayStopwatch.getMSFromStart() >= actionQueue.get(actionIndex).getMSFromStart())
				{

					TimestampedAction currentAction = actionQueue.get(actionIndex);

					if (currentAction instanceof BlockClickedAction)
					{
						System.out.println("BlockAction");
					}
					else if (currentAction instanceof MouseClickAction)
					{
						System.out.println("ClickAction");
					}
					else if (currentAction instanceof MouseAction)
					{
						cursor.setCenterX(((MouseAction) currentAction).getMousePosition().getX());
						cursor.setCenterY(((MouseAction) currentAction).getMousePosition().getY());
						System.out.println("Mouse moved to " + ((MouseAction) currentAction).getMousePosition().getX() + " " + ((MouseAction) currentAction).getMousePosition().getY());
					}
					else if (currentAction instanceof SequenceInitiationAction)
					{
						clearBlocks();
						
						++numTries;
						
						blocks = ((SequenceInitiationAction) currentAction).getSequence().getBlocks();
						
						// Reconstruct all blocks due to serialization not preserving correct information
						rebuiltBlocks = new ArrayList<CorsiBlock>();
						for (CorsiBlock deserializedBlock : blocks)
						{
							deserializedBlock.refreshPosition();
							CorsiBlock rebuiltBlock = new CorsiBlock(deserializedBlock.getX(), deserializedBlock.getY(), CorsiBlockGenerator.BLOCK_SIDE_LENGTH);
							rebuiltBlocks.add(rebuiltBlock);
							gameObjects.getChildren().add(rebuiltBlock);
							System.out.println("Adding a block at " + deserializedBlock.getX() + "," + deserializedBlock.getY());							
						}
						
						// Rebuild sequence data object
						CorsiSequenceData sequenceData = new CorsiSequenceData(rebuiltBlocks, 
								((SequenceInitiationAction) currentAction).getSequence().getLevel(), 
								SEC_BETWEEN_BLINKS, 
								BLINK_DURATION, 
								false, 
								((SequenceInitiationAction) currentAction).getSequence().getSecToDelay());
						
						
						double seconds = sequencePlayer.playSequence(sequenceData);
						TimedMessageDisplay.displayMessage(startMessage, seconds, 0.2);
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
	
	private void clearBlocks()
	{
		for (CorsiBlock block : rebuiltBlocks)
		{
			gameObjects.getChildren().remove(block);
		}
		
		rebuiltBlocks = new ArrayList<CorsiBlock>();
		blocks = new ArrayList<CorsiBlock>();
	}
}
