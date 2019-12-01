package application;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// This class is responsible for running game replays
// It inherits much of its functionality from GameManager
public class GameReplayManager extends GameManager
{
	private final String MENU_BUTTON_LABEL = "Return to Menu";
	private final int MENU_BUTTON_OFFSET = 50;
	
	private ArrayList<TimestampedAction> actionQueue;

	// For efficiency, all TimestampedActions from the GameData object
	// are split into two ArrayLists and processed by two separate timers
	private ArrayList<TimestampedAction> timerOneActions;
	private ArrayList<TimestampedAction> timerTwoActions;
	private int timerOneActionIndex;
	private int timerTwoActionIndex;

	// This list stores blocks which have been re-instantiated based off
	// of stored blocks
	
	// This was necessary because CorsiBlocks cannot deserialize everything
	// from their parent Rectangle class
	private ArrayList<CorsiBlock> rebuiltBlocks;
	private Stopwatch replayStopwatch;
	private ReplayCursor cursor;
	
	private Button returnToMenuButton;

	public GameReplayManager(Stage stage, GameData gameData, PlayerData playerData, ArrayList<PlayerData> players)
	{
		super(stage);
		setPlayerData(playerData);
		setPlayers(players);
		setGameData(gameData);

		rebuiltBlocks = new ArrayList<CorsiBlock>();

		actionQueue = gameData.getGameActions();
		replayStopwatch = new Stopwatch();
		
		cursor = new ReplayCursor();
		getGameObjects().getChildren().add(cursor);

		returnToMenuButton = new Button(MENU_BUTTON_LABEL);
		returnToMenuButton.setLayoutX((stage.getScene().getWidth() / 2) - MENU_BUTTON_OFFSET);
		returnToMenuButton.setLayoutY(stage.getScene().getHeight() / 2);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				PlayerViewMenu menu = new PlayerViewMenu(stage, playerData, players);
			}
		};
		
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		timerOneActions = new ArrayList<TimestampedAction>();
		timerTwoActions = new ArrayList<TimestampedAction>();
		timerOneActionIndex = 0;
		timerTwoActionIndex = 0;

		// Split actions into two lists
		for (TimestampedAction action : actionQueue)
		{
			if (timerOneActions.size() <= timerTwoActions.size())
			{
				timerOneActions.add(action);
			}
			else
			{
				timerTwoActions.add(action);
			}
		}

		TimedMessageDisplay.displayMessage(getReadyMessage(), 0, 0.5);
		beginSimulation();
	}

	private void beginSimulation()
	{
		replayStopwatch.start();

		// Start both timers
		
		AnimationTimer timerOne = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if (timerOneActionIndex >= timerOneActions.size())
				{
					this.stop();
				}
				else
				{
					if (replayStopwatch.getMSFromStart() >= timerOneActions.get(timerOneActionIndex).getMSFromStart())
					{
						evaluateAction(timerOneActions.get(timerOneActionIndex));
						++timerOneActionIndex;
					}
				}
			}
		};

		AnimationTimer timerTwo = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if (timerTwoActionIndex >= timerTwoActions.size()) 
				{
					this.stop();
				}
				else
				{
					if (replayStopwatch.getMSFromStart() >= timerTwoActions.get(timerTwoActionIndex).getMSFromStart())
					{
						evaluateAction(timerTwoActions.get(timerTwoActionIndex));
						++timerTwoActionIndex;
					}
				}
			}
		};

		timerOne.start();
		timerTwo.start();
	}

	// Handles an argument action depending on its type
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
					getClickedBlocks().add(rebuiltBlock);
				}
			}
		}
		else if (currentAction instanceof SubmitClickedAction)
		{
			handleSubmitClickedAction();
		}
		else if (currentAction instanceof MouseClickAction)
		{
			cursor.blink();
		}
		else if (currentAction instanceof MouseAction)
		{
			cursor.setCenterX(((MouseAction) currentAction).getMousePosition().getX());
			cursor.setCenterY(((MouseAction) currentAction).getMousePosition().getY());
		}
		else if (currentAction instanceof SequenceInitiationAction)
		{
			handleSequenceInitiationAction((SequenceInitiationAction) currentAction);
		}
		else if (currentAction instanceof GameEndAction)
		{
			handleGameEndAction();
		}
	}
	
	private void handleGameEndAction()
	{
		clearBlocks();
		getGameObjects().getChildren().remove(getSubmitButton());
		getGameObjects().getChildren().remove(cursor);
		getGameObjects().getChildren().add(returnToMenuButton);
	}

	private void handleSubmitClickedAction()
	{
		boolean success = true;

		if (getClickedBlocks().size() == 0 || getClickedBlocks().size() != getCurrentLevel())
		{
			success = false;
		}
		else
		{
			// Check sequence in normal order
			for (int i = 0; i < getCurrentLevel(); ++i)
			{
				success = success && rebuiltBlocks.get(i).equals(getClickedBlocks().get(i));
			}

			if (!success)
			{
				success = true;

				// Check sequence in reverse order
				for (int clickedSeqIndex = 0, seqIndex = getCurrentLevel() -1; seqIndex >= 0; ++clickedSeqIndex, --seqIndex)
				{
					success = success && rebuiltBlocks.get(seqIndex).equals(getClickedBlocks().get(clickedSeqIndex));
				}
			}
		}

		if (success)
		{
			setCurrentLevel(getCurrentLevel() + 1);
			setNumTries(0);
			TimedMessageDisplay.displayMessage(getCorrectMessage(), 0, 2);
		}
		else
		{
			// Display either incorrect message or game over message depending
			// on how many attempts the player made
			if (getNumTries() < 2)
			{
				TimedMessageDisplay.displayMessage(getIncorrectMessage(), 0, 2);
			}
			else
			{
				TimedMessageDisplay.displayMessage(getGameOverMessage(), 0, 0.5);
			}
		}

		getClickedBlocks().clear();
	}

	private void handleSequenceInitiationAction(SequenceInitiationAction action)
	{
		clearBlocks();

		setBlocks(action.getSequence().getBlocks());
		setCurrentLevel(action.getSequence().getLevel());
		setNumTries(getNumTries() + 1);

		// Reconstruct all blocks due to serialization not preserving correct information
		rebuiltBlocks = new ArrayList<CorsiBlock>();
		for (CorsiBlock deserializedBlock : getBlocks())
		{
			deserializedBlock.refreshPosition();
			CorsiBlock rebuiltBlock = new CorsiBlock(deserializedBlock.getX(), deserializedBlock.getY(), CorsiBlockGenerator.BLOCK_SIDE_LENGTH);
			rebuiltBlocks.add(rebuiltBlock);
			getGameObjects().getChildren().add(rebuiltBlock);
		}
		
		// Remove and add cursor to ensure that it draws above blocks
		getGameObjects().getChildren().remove(cursor);
		getGameObjects().getChildren().add(cursor);

		// Rebuild sequence data object
		CorsiSequenceData sequenceData = new CorsiSequenceData(rebuiltBlocks, 
				action.getSequence().getLevel(), 
				SEC_BETWEEN_BLINKS, 
				SEQUENCE_BLOCK_BLINK_DURATION, 
				false, 
				action.getSequence().getSecToDelay());


		double secondsToDelayStartMessage = getSequencePlayer().playSequence(sequenceData);
		TimedMessageDisplay.displayMessage(getStartMessage(), secondsToDelayStartMessage, 0.2);
	}

	private void clearBlocks()
	{
		for (CorsiBlock block : rebuiltBlocks)
		{
			getGameObjects().getChildren().remove(block);
		}

		rebuiltBlocks = new ArrayList<CorsiBlock>();
		setBlocks(new ArrayList<CorsiBlock>());
	}
}