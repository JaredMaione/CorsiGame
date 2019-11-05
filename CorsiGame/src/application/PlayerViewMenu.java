package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class PlayerViewMenu 
{
	private final String VIEW_STATS_BUTTON = "View Selected Game Stats";
	private final String VIEW_REPLAY_BUTTON = "View Game Replay";
	
	private final int COMPONENT_SPACING = 6;
	private final int PADDING_VALUE = 5;
	
	private PlayerData player;
	
	private Button viewGameStatsButton;
	private Button viewReplayButton;
	
	private ArrayList<ItemSelectionPane<GameData>> games;

	private FlowPane mainPane;
	private Stage stage;

	public PlayerViewMenu(Stage stage, PlayerData player)
	{
		this.stage = stage;
		this.player = player;
		
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setHgap(COMPONENT_SPACING);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));
		
	}
}
