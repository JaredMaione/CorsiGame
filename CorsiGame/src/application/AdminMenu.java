package application;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminMenu 
{
	private final String VIEW_SCOREBOARD_BUTTON_LABEL = "View Global Scoreboard";
	private final String PLAYER_LOOKUP_BUTTON_LABEL = "Player Lookup";
	private final String MENU_BUTTON_LABEL = "Return to Main Menu";
	
	private final int COMPONENT_SPACING = 6;
	private final int PADDING_VALUE = 5;
	
	private FlowPane mainPane;
	private ArrayList<PlayerData> players;
	private Stage stage;
	
	private Button viewScoreboardButton;
	private Button playerLookupButton;
	private Button mainMenuButton;
	
	private final String ADMIN_LOGGED_IN_MESSAGE = "Administrator Menu";
	
	public AdminMenu(Stage stage, ArrayList<PlayerData> players)
	{
		this.players = players;
		
		viewScoreboardButton = new Button(VIEW_SCOREBOARD_BUTTON_LABEL);
		playerLookupButton = new Button(PLAYER_LOOKUP_BUTTON_LABEL);
		mainMenuButton = new Button(MENU_BUTTON_LABEL);
		
		// Configure box to hold all buttons
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(COMPONENT_SPACING);
		buttonBox.getChildren().add(viewScoreboardButton);
		buttonBox.getChildren().add(playerLookupButton);
		buttonBox.getChildren().add(mainMenuButton);
		
		// Configure event handler for all buttons
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(viewScoreboardButton))
				{
					ScoreboardMenu menu = new ScoreboardMenu(stage, players);
				}
				
				if (e.getSource().equals(playerLookupButton))
				{
					PlayerSearchMenu menu = new PlayerSearchMenu(stage, players);
				}
				
				if (e.getSource().equals(mainMenuButton))
				{
					MainMenu menu = new MainMenu(stage);
				}
			}
		};
		
		viewScoreboardButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		playerLookupButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		mainMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		// Configure main FlowPane and add children
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));
		mainPane.getChildren().add(new GameInformationHeader());
		mainPane.getChildren().add(new Text(ADMIN_LOGGED_IN_MESSAGE));
		mainPane.setVgap(COMPONENT_SPACING);
		mainPane.getChildren().add(buttonBox);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 400, 120));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
	}
}
