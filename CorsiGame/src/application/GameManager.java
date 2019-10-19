package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	private ArrayList<CorsiBlock> blocks;
	private ArrayList<CorsiBlock> clickedBlocks;
	private PlayerData playerData;
	private Stopwatch sequenceTimer;
	private Stopwatch gameTimer;
	private GameScore score;
	private int currentLevel;
	private CorsiSequencePlayer sequencePlayer;
	
	// All blocks will be added to this node
	private Group gameObjects;
	
	private final int NUM_BLOCKS = 9;
	
	// This value will be subtracted from the height of the scene to locate submit button
	private final int SCENE_Y_OFFSET = 50;
	
	private EventHandler<MouseEvent> clickHandler;
	
	private int numTries;
	
	private Stage stage;
	
	private Text startMessage;
	private Text correctMessage;
	private Text incorrectMessage;
	private Text gameOverMessage;
	
	public GameManager(PlayerData playerData, Stage stage)
	{
		gameObjects = new Group();
		
		stage.setScene(new Scene(gameObjects, 800, 600));
		stage.setResizable(false);
		stage.show();
		this.stage = stage;
		
		this.playerData = playerData;
		
		gameObjects.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		startMessage = new Text(START_MESSAGE_TEXT);
		startMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 130);
		startMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		startMessage.setVisible(false);
		startMessage.getStyleClass().add("large_positive_message_text");
		gameObjects.getChildren().add(startMessage);
		
		correctMessage = new Text(CORRECT_MESSAGE_TEXT);
		correctMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 150);
		correctMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		correctMessage.setVisible(false);
		correctMessage.getStyleClass().add("large_positive_message_text");
		gameObjects.getChildren().add(correctMessage);
		
		incorrectMessage = new Text(INCORRECT_MESSAGE_TEXT);
		incorrectMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 160);
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
		
		blocks = new ArrayList<CorsiBlock>();
		currentLevel = 1;
		
		sequenceTimer = new Stopwatch();
		gameTimer = new Stopwatch();
		
		sequencePlayer = new CorsiSequencePlayer(sequenceTimer);
	
		clickedBlocks = new ArrayList<CorsiBlock>();
		
		score = new GameScore();

		clickHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				if (e.getSource() instanceof CorsiBlock)
				{
					handleBlockClicked((CorsiBlock) e.getSource());
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
		
		beginGame();
		
	}
	
	public void beginGame()
	{
		gameTimer.start();
		startCurrentLevel(0);
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
		
		double seconds = sequencePlayer.playSequence(blocks, currentLevel, 1, 1, true, secToDelaySequence);
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
			for (int i = 0; i < currentLevel; ++i)
			{
				success = success && blocks.get(i).equals(clickedBlocks.get(i));
			}
		}
					
		if (success)
		{
			++currentLevel;
			numTries = 0;
			TimedMessageDisplay.displayMessage(correctMessage, 0, 2);
			startCurrentLevel(2);
		}
		else
		{
			if (numTries < 2)
			{
				TimedMessageDisplay.displayMessage(incorrectMessage, 0, 2);
				startCurrentLevel(2);
			}
			else
			{
				score.setCorsiSpan(currentLevel);
				TimedMessageDisplay.displayMessage(gameOverMessage, 0, 2);
			}
		}
		
		clickedBlocks.clear();
	}
}
