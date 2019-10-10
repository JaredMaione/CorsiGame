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
	private ArrayList<CorsiBlock> clickBuffer;
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
		
		sequencePlayer = new CorsiSequencePlayer();
		sequenceTimer = new Stopwatch();
		gameTimer = new Stopwatch();
		
		clickBuffer = new ArrayList<CorsiBlock>();
		
		beginGame();
		
	}
	
	public void beginGame()
	{
		startCurrentLevel();
	}
	
	private void startCurrentLevel()
	{
		blocks = CorsiBlockGenerator.generateBlocks(NUM_BLOCKS, (int) gameObjects.getScene().getWidth(), 
				(int) (gameObjects.getScene().getHeight() - SCENE_Y_OFFSET));
		
		EventHandler<MouseEvent> blockHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				if (e.getSource() instanceof CorsiBlock)
				{
					handleBlockClicked((CorsiBlock) e.getSource());
				}
			}
		};
		
		for (CorsiBlock block : blocks)
		{
			block.addEventFilter(MouseEvent.MOUSE_CLICKED, blockHandler);
			gameObjects.getChildren().add(block);
		}
		
		sequencePlayer.playSequence(blocks, currentLevel, 1, 1);
		gameTimer.start();
	}
	
	private void handleBlockClicked(CorsiBlock block)
	{
		if (block.isClickable()) 
		{
			clickBuffer.add(block);
		}
	}
	
	public boolean correctBlockClicked(CorsiBlock block, ArrayList<CorsiBlock> sequence, int level)
	{
		return false;
	}
}
