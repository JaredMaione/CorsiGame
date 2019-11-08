package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
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
	private ArrayList<PlayerData> players;
	
	private Button viewGameStatsButton;
	private Button viewReplayButton;
	private Button returnToMenuButton;
	
	private ArrayList<ItemSelectionPane<GameData>> gameSelectionPanes;

	private FlowPane mainPane;
	private FlowPane gameDataListPane;
	private Stage stage;

	public PlayerViewMenu(Stage stage, PlayerData player, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.player = player;
		this.players = players;
		
		gameSelectionPanes = new ArrayList<ItemSelectionPane<GameData>>();
		
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setHgap(COMPONENT_SPACING);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));
		
		viewGameStatsButton = new Button(VIEW_STATS_BUTTON_LABEL);
		viewReplayButton = new Button(VIEW_REPLAY_BUTTON_LABEL);
		returnToMenuButton = new Button(MENU_BUTTON_LABEL);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(viewGameStatsButton))
				{
					GameData game = getSelectedGame();
					
					if (game != null)
					{
						ScoreboardMenu menu = new ScoreboardMenu(stage, player, game);
					}
				}
				
				if (e.getSource().equals(viewReplayButton))
				{
					GameData game = getSelectedGame();
					
					if (game != null)
					{
						GameReplayManager replayManager = new GameReplayManager(stage, game, player, players);
					}

				}
				
				if (e.getSource().equals(returnToMenuButton))
				{
					PlayerSearchMenu menu = new PlayerSearchMenu(stage, players);
				}
			}
		};
		
		viewGameStatsButton = new Button(VIEW_STATS_BUTTON_LABEL);
		viewReplayButton = new Button(VIEW_REPLAY_BUTTON_LABEL);
		returnToMenuButton = new Button(MENU_BUTTON_LABEL);
		
		viewGameStatsButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		viewReplayButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		gameDataListPane = new FlowPane(Orientation.VERTICAL);
		
		ScrollPane pane = new ScrollPane();
		pane.setPrefSize(100, 100);
		pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		EventHandler<MouseEvent> itemClickHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource() instanceof ItemSelectionPane<?>)
				{
					for (ItemSelectionPane<GameData> pane : gameSelectionPanes)
					{
						if (e.getSource().equals(pane))
						{
							pane.setSelected(true);
						}
						else
						{
							pane.setSelected(false);
						}
					}
				}
			}
		};
		
		for (GameData gameData : player.getGameDataList())
		{
			ItemSelectionPane<GameData> gameSelectionPane = new ItemSelectionPane<GameData>(gameData, 
															gameData.getGameDate().toFormattedString() + " " + 
															gameData.getGameStartTime().sendToString());
			gameSelectionPane.addEventFilter(MouseEvent.MOUSE_CLICKED, itemClickHandler);
			gameSelectionPanes.add(gameSelectionPane);
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
	
	public GameData getSelectedGame()
	{
		for (ItemSelectionPane<GameData> pane : gameSelectionPanes)
		{
			if (pane.isSelected())
			{
				return pane.getObj();
			}
		}
		
		return null;
	}
}
