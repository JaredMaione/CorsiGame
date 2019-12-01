package application;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// This class is responsible for running the game
// It saves game data to a GameData object for analysis later
public class GameManager 
{
	private Button submitButton;
	private final String SUBMIT_BUTTON_LABEL = "Submit";

	private final String START_MESSAGE_TEXT = "START";
	private final String CORRECT_MESSAGE_TEXT = "CORRECT";
	private final String INCORRECT_MESSAGE_TEXT = "INCORRECT";	
	private final String READY_MESSAGE_TEXT = "READY";
	private final String GAME_OVER_MESSAGE_TEXT = "GAME\nOVER";

	private final String GAME_OVER_ALERT_TITLE = "Game Over";
	private final String GAME_OVER_ALERT_MESSAGE = "Game over. Click \"OK\" to view your score";

	protected final double SEC_BETWEEN_BLINKS = 1;
	protected final double SEQUENCE_BLOCK_BLINK_DURATION = 0.5;
	protected final double CLICKED_BLOCK_BLINK_DURATION = 0.1;
	protected final double GAME_START_DELAY = 2.5;
	protected final int MOUSE_CAPTURE_INTERVAL_MS = 75;

	private ArrayList<CorsiBlock> blocks;
	private ArrayList<CorsiBlock> clickedBlocks;

	private PlayerData playerData;
	private ArrayList<PlayerData> players;

	private Stopwatch sequenceTimer;
	private Stopwatch gameTimer;

	private GameData gameData;

	private int currentLevel;

	private CorsiSequencePlayer sequencePlayer;

	// All blocks will be added to this node
	private Group gameObjects;

	protected final int NUM_BLOCKS = 9;

	private final int STARTING_LEVEL = 2;

	// This value will be subtracted from the height of the scene to locate submit button
	protected final int SCENE_Y_OFFSET = 50;

	private EventHandler<MouseEvent> clickHandler;

	private int numTries;

	private Stage stage;

	private Text startMessage;
	private Text correctMessage;
	private Text incorrectMessage;
	private Text readyMessage;
	private Text gameOverMessage;

	private Stopwatch mouseCaptureStopwatch;

	public GameManager(Stage stage)
	{
		gameObjects = new Group();

		stage.setScene(new Scene(gameObjects, 800, 600));
		stage.setResizable(false);
		stage.show();
		this.stage = stage;

		this.playerData = new PlayerData();

		this.players = new ArrayList<PlayerData>();

		gameObjects.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		startMessage = new Text(START_MESSAGE_TEXT);
		startMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 130);
		startMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		startMessage.setVisible(false);
		startMessage.getStyleClass().add("large_positive_message_text");
		gameObjects.getChildren().add(startMessage);

		readyMessage = new Text(READY_MESSAGE_TEXT);
		readyMessage.setLayoutX((gameObjects.getScene().getWidth() / 2) - 130);
		readyMessage.setLayoutY(gameObjects.getScene().getHeight() / 2);
		readyMessage.setVisible(false);
		readyMessage.getStyleClass().add("large_neutral_message_text");
		gameObjects.getChildren().add(readyMessage);

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

		blocks = new ArrayList<CorsiBlock>();
		currentLevel = STARTING_LEVEL;

		sequenceTimer = new Stopwatch();
		gameTimer = new Stopwatch();

		sequencePlayer = new CorsiSequencePlayer(sequenceTimer);

		clickedBlocks = new ArrayList<CorsiBlock>();

		gameData = new GameData();

		numTries = 0;

		mouseCaptureStopwatch = new Stopwatch();
	}

	public GameManager(Stage stage, ArrayList<PlayerData> players, PlayerData playerData)
	{
		this(stage);
		this.playerData = playerData;

		this.players = players;


		EventHandler<MouseEvent> mouseMoveHandler = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent e)
			{
				if (mouseCaptureStopwatch.getMSFromStart() >= MOUSE_CAPTURE_INTERVAL_MS)
				{
					gameData.addTimestampedAction(new MouseAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY())));
					mouseCaptureStopwatch.reset();
					mouseCaptureStopwatch.start();
				}
			}
		};
		stage.getScene().addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveHandler);


		EventHandler<MouseEvent> mouseClickHandler = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent e)
			{

				gameData.addTimestampedAction(new MouseClickAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY()), e.getClickCount()));
			}
		};
		stage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClickHandler);

		clickHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				if (e.getSource() instanceof CorsiBlock)
				{
					handleBlockClicked((CorsiBlock) e.getSource());
					gameData.addTimestampedAction(new BlockClickedAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY()), e.getClickCount(), (CorsiBlock) e.getSource()));
				}
				else if (e.getSource().equals(submitButton))
				{
					if (clickedBlocks.size() != 0)
					{
						gameData.addTimestampedAction(new SubmitClickedAction(gameTimer.getMSFromStart(), new Position(e.getX(), e.getY()), e.getClickCount()));
						evaluatePerformance();
					}

				}
			}
		};

		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, clickHandler);
		numTries = 0;

		beginGame(GAME_START_DELAY);
	}

	public void beginGame(double delay)
	{
		TimedMessageDisplay.displayMessage(readyMessage, 0, 0.5);
		gameTimer.start();
		mouseCaptureStopwatch.start();
		startCurrentLevel(delay);
	}

	private void clearBlocks()
	{
		for (CorsiBlock block : blocks)
		{
			gameObjects.getChildren().remove(block);
		}

		blocks = new ArrayList<CorsiBlock>();
	}

	private void startCurrentLevel(double secToDelaySequence)
	{
		clearBlocks();

		// Reset sequence timer for the new level
		if (sequenceTimer.isRunning())
		{
			sequenceTimer.stop();
			gameData.addToAvgSequenceTime(sequenceTimer.getLastElapsedTime());
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

		// Save sequence in a CorsiSequenceData object to be added to the action queue in GameData
		CorsiSequenceData sequenceData = new CorsiSequenceData(blocks, currentLevel, SEC_BETWEEN_BLINKS, SEQUENCE_BLOCK_BLINK_DURATION, true, secToDelaySequence);
		
		gameData.addTimestampedAction(new SequenceInitiationAction(gameTimer.getMSFromStart(), sequenceData));

		// Play the sequence and store the estimated time to finish playing
		double secondsToDelayStartMessage = sequencePlayer.playSequence(sequenceData);
		
		// Display the start message after delaying for the specified time
		TimedMessageDisplay.displayMessage(startMessage, secondsToDelayStartMessage, 0.2);
	}

	protected void handleBlockClicked(CorsiBlock block)
	{
		if (block.isClickable()) 
		{
			block.blink(CLICKED_BLOCK_BLINK_DURATION);
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
			// If this is the player's first failure on this sequence, give another chance
			if (numTries < 2)
			{
				TimedMessageDisplay.displayMessage(incorrectMessage, 0, 2);
				startCurrentLevel(3);
			}
			// If this is the player's seconds attempt at this sequence, end the game
			else
			{
				TimedMessageDisplay.displayMessage(gameOverMessage, 0, 0.5);
				processGameOver();
			}
		}

		clickedBlocks.clear();
	}

	// Handle the end of the game and save required information
	private void processGameOver()
	{
		if (currentLevel == STARTING_LEVEL)
		{
			gameData.setCorsiSpan(0);
		}
		else
		{
			gameData.setCorsiSpan(currentLevel - 1);
		}

		gameData.addTimestampedAction(new GameEndAction(gameTimer.getMSFromStart()));
		gameTimer.stop();

		// Subtract off however long the start of the game is delayed for accuracy
		gameTimer.getLastElapsedTime().subtractSeconds(GAME_START_DELAY);
		
		gameData.setGameDuration(gameTimer.getLastElapsedTime());

		playerData.addGameData(gameData);
		playerData.saveToFile();

		displayGameOverAlert();
		
		// Transition to scoreboard
		ScoreboardMenu menu = new ScoreboardMenu(stage, players, playerData);
		menu.displayScore(gameData);
		reset();
	}

	private void displayGameOverAlert()
	{
		Alert gameOverAlert = new Alert(AlertType.INFORMATION);

		gameOverAlert.setTitle(GAME_OVER_ALERT_TITLE);
		gameOverAlert.setHeaderText(GAME_OVER_ALERT_MESSAGE);
		gameOverAlert.showAndWait();
	}

	// Reset all data and progress
	public void reset()
	{
		gameData = new GameData();
		clickedBlocks.clear();
		gameTimer.reset();
		mouseCaptureStopwatch.reset();
		sequenceTimer.reset();
		clearBlocks();
		currentLevel = STARTING_LEVEL;
	}
	
	public ArrayList<PlayerData> getPlayers()
	{
		return players;
	}

	public PlayerData getPlayerData()
	{
		return playerData;
	}

	public Button getSubmitButton()
	{
		return submitButton;
	}

	public ArrayList<CorsiBlock> getBlocks()
	{
		return blocks;
	}

	public ArrayList<CorsiBlock> getClickedBlocks()
	{
		return clickedBlocks;
	}
	
	public void setPlayers(ArrayList<PlayerData> players)
	{
		this.players = players;
	}

	public void setBlocks(ArrayList<CorsiBlock> blocks)
	{
		this.blocks = blocks;
	}

	public void setClickedBlocks(ArrayList<CorsiBlock> clickedBlocks)
	{
		this.clickedBlocks = clickedBlocks;
	}

	public void setPlayerData(PlayerData playerData)
	{
		this.playerData = playerData;
	}

	public Stopwatch getSequenceTimer() 
	{
		return sequenceTimer;
	}

	public void setSequenceTimer(Stopwatch sequenceTimer) 
	{
		this.sequenceTimer = sequenceTimer;
	}

	public GameData getGameData() 
	{
		return gameData;
	}

	public void setGameData(GameData gameData) 
	{
		this.gameData = gameData;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) 
	{
		this.currentLevel = currentLevel;
	}

	public CorsiSequencePlayer getSequencePlayer() 
	{
		return sequencePlayer;
	}

	public void setSequencePlayer(CorsiSequencePlayer sequencePlayer) 
	{
		this.sequencePlayer = sequencePlayer;
	}

	public Group getGameObjects() 
	{
		return gameObjects;
	}

	public void setGameObjects(Group gameObjects) 
	{
		this.gameObjects = gameObjects;
	}

	public int getNumTries() 
	{
		return numTries;
	}

	public void setNumTries(int numTries) 
	{
		this.numTries = numTries;
	}

	public Stage getStage() 
	{
		return stage;
	}

	public void setStage(Stage stage) 
	{
		this.stage = stage;
	}

	public Text getStartMessage() 
	{
		return startMessage;
	}

	public void setStartMessage(Text startMessage) 
	{
		this.startMessage = startMessage;
	}

	public Text getCorrectMessage() 
	{
		return correctMessage;
	}

	public void setCorrectMessage(Text correctMessage) 
	{
		this.correctMessage = correctMessage;
	}

	public Text getIncorrectMessage() 
	{
		return incorrectMessage;
	}

	public void setIncorrectMessage(Text incorrectMessage) 
	{
		this.incorrectMessage = incorrectMessage;
	}

	public Text getReadyMessage() 
	{
		return readyMessage;
	}

	public void setReadyMessage(Text readyMessage) 
	{
		this.readyMessage = readyMessage;
	}

	public Text getGameOverMessage() 
	{
		return gameOverMessage;
	}

	public void setGameOverMessage(Text gameOverMessage) 
	{
		this.gameOverMessage = gameOverMessage;
	}

	public Stopwatch getMouseCaptureStopwatch() 
	{
		return mouseCaptureStopwatch;
	}

	public void setMouseCaptureStopwatch(Stopwatch mouseCaptureStopwatch) 
	{
		this.mouseCaptureStopwatch = mouseCaptureStopwatch;
	}
}