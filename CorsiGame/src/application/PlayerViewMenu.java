package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PlayerViewMenu 
{
	private final String VIEW_STATS_BUTTON_LABEL = "View Selected Game Stats";
	private final String VIEW_REPLAY_BUTTON_LABEL = "View Game Replay";
	private final String MENU_BUTTON_LABEL = "Return to Menu";
	
	private final int COMPONENT_SPACING = 6;
	private final int PADDING_VALUE = 5;
	
	private PlayerData player;
	
	private Button viewGameStatsButton;
	private Button viewReplayButton;
	private Button returnToMenuButton;
	
	private ArrayList<ItemSelectionPane<GameData>> games;

	private FlowPane mainPane;
	private FlowPane gameDataListPane;
	private Stage stage;

	public PlayerViewMenu(Stage stage, PlayerData player)
	{
		this.stage = stage;
		this.player = player;
		
		games = new ArrayList<ItemSelectionPane<GameData>>();
		
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setHgap(COMPONENT_SPACING);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));
		
		viewGameStatsButton = new Button(VIEW_STATS_BUTTON_LABEL);
		viewReplayButton = new Button(VIEW_REPLAY_BUTTON_LABEL);
		returnToMenuButton = new Button(MENU_BUTTON_LABEL);
		
		gameDataListPane = new FlowPane(Orientation.VERTICAL);
		
		ScrollPane pane = new ScrollPane();
		pane.setPrefSize(100, 100);
		pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		for (GameData gameData : player.getGameDataList())
		{
			ItemSelectionPane<GameData> gameSelectionPane = new ItemSelectionPane<GameData>(gameData, gameData.getGameDate().toFormattedString());
			games.add(gameSelectionPane);
			gameDataListPane.getChildren().add(gameSelectionPane);
		}
		
		pane.setContent(gameDataListPane);
		
		mainPane.getChildren().add(pane);
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(COMPONENT_SPACING);
		
		buttonBox.getChildren().add(viewGameStatsButton);
		buttonBox.getChildren().add(viewReplayButton);
		buttonBox.getChildren().add(returnToMenuButton);
		
		mainPane.getChildren().add(buttonBox);
		
		stage.setScene(new Scene(mainPane, 420, 250));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		stage.show();
	} 
}
