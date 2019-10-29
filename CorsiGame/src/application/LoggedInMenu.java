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

public class LoggedInMenu 
{
	private final String PLAY_BUTTON_LABEL = "Play Game";
	private final String SCOREBOARD_BUTTON_LABEL = "View Scoreboard";
	private final String LOGOUT_BUTTON_LABEL = "Logout & Return to Menu";
	private final String USERNAME_LABEL = "Logged in as:";
	
	private final int COMPONENT_SPACING = 6;

	private ArrayList<PlayerData> players;
	private PlayerData loggedInPlayer;
	
	private Stage stage;
	
	private Button playButton;
	private Button scoreboardButton;
	private Button logoutButton;
	
	private FlowPane mainPane;

	public LoggedInMenu(Stage stage, PlayerData loggedInPlayer, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.players = players;
		this.loggedInPlayer = loggedInPlayer;
		
		playButton = new Button(PLAY_BUTTON_LABEL);
		scoreboardButton = new Button(SCOREBOARD_BUTTON_LABEL);
		logoutButton = new Button(LOGOUT_BUTTON_LABEL);
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(COMPONENT_SPACING);
		
		buttonBox.getChildren().add(playButton);
		buttonBox.getChildren().add(scoreboardButton);
		buttonBox.getChildren().add(logoutButton);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(playButton))
				{
					GameManager gameManager = new GameManager(loggedInPlayer, stage);
				}
				
				if (e.getSource().equals(scoreboardButton))
				{
					ScoreboardMenu menu = new ScoreboardMenu(stage, players, loggedInPlayer);
				}
				
				if (e.getSource().equals(logoutButton))
				{
					MainMenu menu = new MainMenu(stage);
				}
			}
		};
		
		playButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		scoreboardButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		logoutButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setPadding(new Insets(5, 5, 5, 5));
		mainPane.getChildren().add(new GameInformationHeader());
		mainPane.setVgap(COMPONENT_SPACING);
		mainPane.getChildren().add(new Text(USERNAME_LABEL + " " + loggedInPlayer.getUsername()));
		mainPane.getChildren().add(buttonBox);
		
		stage.setScene(new Scene(mainPane, 360, 120));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		stage.show();
	}
}
