package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class GameManager 
{
	private ArrayList<CorsiBlock> blocks;
	private PlayerData playerData;
	private Stopwatch sequenceTimer;
	private Stopwatch gameTimer;
	private GameScore score;
	private int currentLevel;
	private CorsiSequencePlayer sequencePlayer;
	
	// All blocks will be added to this node
	private Group gameObjects;
	
	private final int NUM_BLOCKS = 9;
	
	public GameManager(PlayerData playerData, Group objectGroup)
	{
		this.playerData = playerData;
		gameObjects = objectGroup;
		blocks = CorsiBlockGenerator.generateBlocks(NUM_BLOCKS, (int) gameObjects.getScene().getWidth(), (int) gameObjects.getScene().getHeight());
		
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
		}
	}
	
	private void handleBlockClicked(CorsiBlock block)
	{
		
	}
	
	public boolean correctBlockClicked(CorsiBlock block, ArrayList<CorsiBlock> sequence, int level)
	{
		return false;
	}
}
