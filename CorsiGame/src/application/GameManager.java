package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameManager 
{
	private Button submitButton;
	private final String SUBMIT_BUTTON_LABEL = "Submit";
	private final String START_MESSAGE_TEXT = "START";
	private final String CORRECT_MESSAGE_TEXT = "CORRECT";
	private final String INCORRECT_MESSAGE_TEXT = "INCORRECT";
	private final String GAME_OVER_MESSAGE_TEXT = "GAME\nOVER";
	
	private final String PLAY_AGAIN_TITLE = "Play again?";
	private final String PLAY_AGAIN_MESSAGE = "Would you like to play again?";
	private final String PLAY_AGAIN_YES_LABEL = "Yes, play again!";
	private final String PLAY_AGAIN_NO_LABEL = "No, return to menu";
	
	private final String GAME_OVER_ALERT_TITLE = "Game Over";
	private final String GAME_OVER_ALERT_MESSAGE = "Game over. Click \"OK\" to view your score";
	
	private ArrayList<CorsiBlock> blocks;
	private ArrayList<CorsiBlock> clickedBlocks;
	
	private PlayerData playerData;
	private ArrayList<PlayerData> players;
	
	private Stopwatch sequenceTimer;
	private Stopwatch gameTimer;
	
	private GameData score;
	
	private int currentLevel;
	
	private CorsiSequencePlayer sequencePlayer;
	
	// All blocks will be added to this node
	private Group gameObjects;
	
	private final int NUM_BLOCKS = 9;
	
	private final int STARTING_LEVEL = 2;
	
	// This value will be subtracted from the height of the scene to locate submit button
	private final int SCENE_Y_OFFSET = 50;
	
	private EventHandler<MouseEvent> clickHandler;
	
	private int numTries;
	
	private Stage stage;
	
	private Text startMessage;
	private Text correctMessage;
	private Text incorrectMessage;
	private Text gameOverMessage;
	
	public GameManager(Stage stage, ArrayList<PlayerData> players, PlayerData playerData)
	{
		gameObjects = new Group();
		
		stage.setScene(new Scene(gameObjects, 800, 600));
		stage.setResizable(false);
		stage.show();
		this.stage = stage;
		
		this.playerData = playerData;
		
		this.players = players;
		
		gameObjects.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		startMessage = new Text(START_MESSAGE_TEXT);
		startMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 130);
		startMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		startMessage.setVisible(false);
		startMessage.getStyleClass().add("large_positive_message_text");
		gameObjects.getChildren().add(startMessage);
		
		correctMessage = new Text(CORRECT_MESSAGE_TEXT);
		correctMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 185);
		correctMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		correctMessage.setVisible(false);
		correctMessage.getStyleClass().add("large_positive_message_text");
		gameObjects.getChildren().add(correctMessage);
		
		incorrectMessage = new Text(INCORRECT_MESSAGE_TEXT);
		incorrectMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 205);
		incorrectMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		incorrectMessage.setVisible(false);
		incorrectMessage.getStyleClass().add("large_negative_message_text");
		gameObjects.getChildren().add(incorrectMessage);
		
		gameOverMessage = new Text(GAME_OVER_MESSAGE_TEXT);
		gameOverMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 130);
		gameOverMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		gameOverMessage.setVisible(false);
		gameOverMessage.getStyleClass().add("large_negative_message_text");
		gameObjects.getChildren().add(gameOverMessage);
		
		
		submitButton = new Button(SUBMIT_BUTTON_LABEL);
		submitButton.setLayoutX(0);
		submitButton.setLayoutY(gameObjects.getScene().getHeight() - (SCENE_Y_OFFSET - 15));
		
		gameObjects.getChildren().add(submitButton);
		
		EventHandler<MouseEvent> mouseMoveHandler = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent e)
			{
				score.addTimestampedAction(new MouseAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY())));
			}
		};
		stage.getScene().addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveHandler);
		
		
		EventHandler<MouseEvent> mouseClickHandler = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent e)
			{
	
				score.addTimestampedAction(new MouseClickAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY()), e.getClickCount()));
			}
		};
		stage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClickHandler);
		
		blocks = new ArrayList<CorsiBlock>();
		currentLevel = STARTING_LEVEL;
		
		sequenceTimer = new Stopwatch();
		gameTimer = new Stopwatch();
		
		sequencePlayer = new CorsiSequencePlayer(sequenceTimer);
	
		clickedBlocks = new ArrayList<CorsiBlock>();
		
		score = new GameData();

		clickHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				if (e.getSource() instanceof CorsiBlock)
				{
					handleBlockClicked((CorsiBlock) e.getSource());
					score.addTimestampedAction(new BlockClickedAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY()), e.getClickCount(), (CorsiBlock) e.getSource()));
				}
				else if (e.getSource().equals(submitButton))
				{
					if (clickedBlocks.size() != 0)
					{
						evaluatePerformance();
					}
				}
			}
		};
		
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, clickHandler);
		numTries = 0;
		
		beginGame(1);
		
	}
	
	public void beginGame(int delay)
	{
		gameTimer.start();
		startCurrentLevel(delay);
	}
	
	private void clearBlocks()
	{
		for (CorsiBlock block : blocks)
		{
			gameObjects.getChildren().remove(block);
		}
		
		blocks.clear();
	}
	
	private void startCurrentLevel(double secToDelaySequence)
	{
		clearBlocks();
		
		if (sequenceTimer.isRunning())
		{
			sequenceTimer.stop();
			score.addToAvgSequenceTime(sequenceTimer.getLastElapsedTime());
			sequenceTimer.reset();
		}
		
		++numTries;
		
		blocks = CorsiBlockGenerator.generateBlocks(NUM_BLOCKS, (int) gameObjects.getScene().getWidth(), 
				(int) (gameObjects.getScene().getHeight() - SCENE_Y_OFFSET));
		
		for (CorsiBlock block : blocks)
		{
			block.addEventFilter(MouseEvent.MOUSE_CLICKED, clickHandler);
			gameObjects.getChildren().add(block);
		}
		
		CorsiSequenceData sequenceData = new CorsiSequenceData(blocks, currentLevel, 1, 1, true, secToDelaySequence);
		score.addTimestampedAction(new SequenceInitiationAction(gameTimer.getMSFromStart(), sequenceData));
		
		double seconds = sequencePlayer.playSequence(sequenceData);
		TimedMessageDisplay.displayMessage(startMessage, seconds, 0.2);
	}
	
	private void handleBlockClicked(CorsiBlock block)
	{
		if (block.isClickable()) 
		{
			block.blink(0.1);
			clickedBlocks.add(block);
		}
	}
	
	private void evaluatePerformance()
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
				success = success && blocks.get(i).equals(clickedBlocks.get(i));
			}
			
			if (!success)
			{
				success = true;
				
				// Check sequence in reverse order
				for (int clickedSeqIndex = 0, seqIndex = currentLevel -1; seqIndex >= 0; ++clickedSeqIndex, --seqIndex)
				{
					success = success && blocks.get(seqIndex).equals(clickedBlocks.get(clickedSeqIndex));
				}
			}
		}
					
		if (success)
		{
			++currentLevel;
			numTries = 0;
			TimedMessageDisplay.displayMessage(correctMessage, 0, 2);
			startCurrentLevel(3);
		}
		else
		{
			if (numTries < 2)
			{
				TimedMessageDisplay.displayMessage(incorrectMessage, 0, 2);
				startCurrentLevel(3);
			}
			else
			{
				TimedMessageDisplay.displayMessage(gameOverMessage, 0, 0.5);
				processGameOver();
			}
		}
		
		clickedBlocks.clear();
	}
	
	public void processGameOver()
	{
		if (currentLevel == STARTING_LEVEL)
		{
			score.setCorsiSpan(0);
		}
		else
		{
			score.setCorsiSpan(currentLevel - 1);
		}
		
		score.addTimestampedAction(new GameEndAction(gameTimer.getMSFromStart()));
		gameTimer.stop();
		score.setGameDuration(gameTimer.getLastElapsedTime());
		playerData.addGameData(score);
		reset();
		
		/*Alert playAgainAlert = new Alert(AlertType.CONFIRMATION);
		((Button) playAgainAlert.getDialogPane().lookupButton(ButtonType.OK)).setText(PLAY_AGAIN_YES_LABEL);
		((Button) playAgainAlert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(PLAY_AGAIN_NO_LABEL);
		
		playAgainAlert.setTitle(PLAY_AGAIN_TITLE);
		playAgainAlert.setHeaderText(PLAY_AGAIN_MESSAGE);

		Optional<ButtonType> choice = playAgainAlert.showAndWait();
		
		playerData.saveToFile();
		
		if (choice.isPresent() && choice.get() == ButtonType.OK)
		{
			beginGame(1);
		}
		else
		{
			LoggedInMenu menu = new LoggedInMenu(stage, playerData, players);
		}*/
		
		displayGameOverAlert();
		ScoreboardMenu menu = new ScoreboardMenu(stage, players, playerData);
	}
	
	private void displayGameOverAlert()
	{
		Alert gameOverAlert = new Alert(AlertType.INFORMATION);
		
		gameOverAlert.setTitle(GAME_OVER_ALERT_TITLE);
		gameOverAlert.setHeaderText(GAME_OVER_ALERT_MESSAGE);
		gameOverAlert.showAndWait();
	}
	
	public void reset()
	{
		score = new GameData();
		clickedBlocks.clear();
		gameTimer.reset();
		sequenceTimer.reset();
		clearBlocks();
		currentLevel = STARTING_LEVEL;
	}
}
