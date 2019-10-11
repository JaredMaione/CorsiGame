package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GameManager 
{
	private Button submitButton;
	private final String SUBMIT_BUTTON_LABEL = "Submit";
	
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
	
	public GameManager(PlayerData playerData, Group objectGroup)
	{
		this.playerData = playerData;
		gameObjects = objectGroup;
		
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
		startCurrentLevel();
	}
	
	private void clearBlocks()
	{
		for (CorsiBlock block : blocks)
		{
			gameObjects.getChildren().remove(block);
		}
		
		blocks.clear();
	}
	
	private void startCurrentLevel()
	{
		clearBlocks();
		
		if (sequenceTimer.isRunning())
		{
			sequenceTimer.stop();
			score.addToAvgSequenceTime(sequenceTimer.getLastElapsedTime());
			sequenceTimer.reset();
			System.out.println(sequenceTimer.getMSFromStart());
		}
		
		++numTries;
		
		blocks = CorsiBlockGenerator.generateBlocks(NUM_BLOCKS, (int) gameObjects.getScene().getWidth(), 
				(int) (gameObjects.getScene().getHeight() - SCENE_Y_OFFSET));
		
		for (CorsiBlock block : blocks)
		{
			block.addEventFilter(MouseEvent.MOUSE_CLICKED, clickHandler);
			gameObjects.getChildren().add(block);
		}
		
		sequencePlayer.playSequence(blocks, currentLevel, 1, 1, true);
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
		if (clickedBlocks.size() == 0 || clickedBlocks.size() != currentLevel)
		{
			if (numTries < 2)
			{
				startCurrentLevel();
			}
		}
		else
		{
			boolean success = true;
			
			for (int i = 0; i < currentLevel; ++i)
			{
				success = success && blocks.get(i).equals(clickedBlocks.get(i));
			}
						
			if (success)
			{
				++currentLevel;
				numTries = 0;
				startCurrentLevel();
			}
			else
			{
				if (numTries < 2)
				{
					startCurrentLevel();
				}
			}
		}
		
		clickedBlocks.clear();
	}
}
