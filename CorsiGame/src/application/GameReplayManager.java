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

	private ArrayList<TimestampedAction> threadOneActions;
	private ArrayList<TimestampedAction> threadTwoActions;
	private int threadOneActionIndex;
	private int threadTwoActionIndex;

	private ArrayList<CorsiBlock> rebuiltBlocks;
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
		replayStopwatch = new Stopwatch();
		cursor = new Circle(CURSOR_RADIUS);

		gameObjects.getChildren().add(cursor);

		threadOneActions = new ArrayList<TimestampedAction>();
		threadTwoActions = new ArrayList<TimestampedAction>();
		threadOneActionIndex = 0;
		threadTwoActionIndex = 0;

		for (TimestampedAction action : actionQueue)
		{
			if (threadOneActions.size() <= threadTwoActions.size())
			{
				threadOneActions.add(action);
			}
			else
			{
				threadTwoActions.add(action);
			}
		}

		TimedMessageDisplay.displayMessage(readyMessage, 0, 0.5);
		beginSimulation();
	}

	private void beginSimulation()
	{
		replayStopwatch.start();

		AnimationTimer threadOne = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if (threadOneActionIndex >= threadOneActions.size())
				{
					this.stop();
				}
				else
				{
					if (replayStopwatch.getMSFromStart() >= threadOneActions.get(threadOneActionIndex).getMSFromStart())
					{
						evaluateAction(threadOneActions.get(threadOneActionIndex));
						++threadOneActionIndex;
					}
				}
			}
		};

		AnimationTimer threadTwo = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if (threadTwoActionIndex >= threadTwoActions.size()) 
				{
					this.stop();
				}
				else
				{
					if (replayStopwatch.getMSFromStart() >= threadTwoActions.get(threadTwoActionIndex).getMSFromStart())
					{
						evaluateAction(threadTwoActions.get(threadTwoActionIndex));
						++threadTwoActionIndex;
					}
				}
			}
		};

		threadOne.start();
		threadTwo.start();
	}

	private void evaluateAction(TimestampedAction currentAction)
	{
		if (currentAction instanceof BlockClickedAction)
		{
			CorsiBlock clickedBlock = ((BlockClickedAction) currentAction).getBlock();

			for (CorsiBlock rebuiltBlock : rebuiltBlocks)
			{
				if (clickedBlock.getPosition().equals(rebuiltBlock.getPosition()))
				{
					rebuiltBlock.blink(CLICKED_BLOCK_BLINK_DURATION);
					clickedBlocks.add(rebuiltBlock);
				}
			}
			System.out.println("BlockAction");
		}
		else if (currentAction instanceof SubmitClickedAction)
		{
			handleSubmitClickedAction();
			System.out.println("SubmitAction");
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
			handleSequenceInitiationAction((SequenceInitiationAction) currentAction);
		}
		else if (currentAction instanceof GameEndAction)
		{
			System.out.println("GameEnd");
		}
	}

	private void handleSubmitClickedAction()
	{
		boolean success = true;

		if (clickedBlocks.size() == 0 || clickedBlocks.size() != currentLevel)
		{
			success = false;
		}
		else
		{
			// Check sequence in normal order
			for (int i = 0; i < currentLevel; ++i)
			{
				success = success && rebuiltBlocks.get(i).equals(clickedBlocks.get(i));
			}

			if (!success)
			{
				success = true;

				// Check sequence in reverse order
				for (int clickedSeqIndex = 0, seqIndex = currentLevel -1; seqIndex >= 0; ++clickedSeqIndex, --seqIndex)
				{
					success = success && rebuiltBlocks.get(seqIndex).equals(clickedBlocks.get(clickedSeqIndex));
				}
			}
		}

		if (success)
		{
			++currentLevel;
			numTries = 0;
			TimedMessageDisplay.displayMessage(correctMessage, 0, 2);
		}
		else
		{
			if (numTries < 2)
			{
				TimedMessageDisplay.displayMessage(incorrectMessage, 0, 2);
			}
			else
			{
				TimedMessageDisplay.displayMessage(gameOverMessage, 0, 0.5);
				//processGameOver();
			}
		}

		clickedBlocks.clear();
	}

	private void handleSequenceInitiationAction(SequenceInitiationAction action)
	{
		clearBlocks();

		blocks = action.getSequence().getBlocks();
		currentLevel = action.getSequence().getLevel();
		System.out.println(currentLevel);
		++numTries;

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
		
		// Remove and add cursor to ensure that it draws above blocks
		gameObjects.getChildren().remove(cursor);
		gameObjects.getChildren().add(cursor);

		// Rebuild sequence data object
		CorsiSequenceData sequenceData = new CorsiSequenceData(rebuiltBlocks, 
				action.getSequence().getLevel(), 
				SEC_BETWEEN_BLINKS, 
				SEQUENCE_BLOCK_BLINK_DURATION, 
				false, 
				action.getSequence().getSecToDelay());


		double seconds = sequencePlayer.playSequence(sequenceData);
		TimedMessageDisplay.displayMessage(startMessage, seconds, 0.2);
		System.out.println("InitAction");
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
