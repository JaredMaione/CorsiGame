package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScoreboardMenu 
{
	private final String GLOBAL_LEADERBOARD_BUTTON_LABEL = "View Global Leaderboard";
	private final String PERSONAL_SCORES_BUTTON_LABEL = "View Personal Scores";
	private final String RETURN_TO_MENU_BUTTON_LABEL = "Return to Menu";
	private final String USERNAME_LABEL = "Username";
	private final String CORSI_SPAN_LABEL = "Highest Corsi Span";
	private final String NUM_GAMES_LABEL = "Number of Games Played";
	private final String NO_SCORES_MSG_TITLE = "No Scores in This Category";
	private final String NO_SCORES_MSG = "There are no scores to be displayed in this category.";
	
	private final int BUTTON_SPACING = 4;
	private final int NUM_PERSONAL_SCORE_COLUMNS = 2;
	private final int NUM_GLOBAL_SCORE_COLUMNS = 3;
			
	private FixedColumnGridPane statDisplayPane;
	private FlowPane mainPane;
	private Button viewPersonalScoresButton;
	private Button viewGlobalLeaderboardButton;
	private Button returnToMenuButton;
	
	private Stage stage;
	
	private ArrayList<PlayerData> players;
	private PlayerData currentPlayer;
	
	public ScoreboardMenu(Stage stage, ArrayList<PlayerData> players, PlayerData currentPlayer)
	{
		this.players = players;
		this.currentPlayer = currentPlayer;
		mainPane = new FlowPane(Orientation.VERTICAL);

		statDisplayPane = new FixedColumnGridPane();
		
		viewPersonalScoresButton = new Button(PERSONAL_SCORES_BUTTON_LABEL);
		viewGlobalLeaderboardButton = new Button(GLOBAL_LEADERBOARD_BUTTON_LABEL);
		returnToMenuButton = new Button(RETURN_TO_MENU_BUTTON_LABEL);
		
		HBox buttonBox = new HBox();
		buttonBox.setPadding(statDisplayPane.getPadding());
		buttonBox.setSpacing(BUTTON_SPACING);
		
		buttonBox.getChildren().add(viewPersonalScoresButton);
		buttonBox.getChildren().add(viewGlobalLeaderboardButton);
		buttonBox.getChildren().add(returnToMenuButton);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(viewPersonalScoresButton))
				{
					if (currentPlayer.getNumberOfGames() > 0)
					{
						displayPersonalScores();
					}
					else
					{
						displayNoScoresMessage();
					}
				}
				
				if (e.getSource().equals(viewGlobalLeaderboardButton))
				{
					for (PlayerData player : players)
					{
						if (player.getNumberOfGames() > 0)
						{
							displayGlobalLeaderboard();
							return;
						}
					}
					
					displayNoScoresMessage();
				}
				
				if (e.getSource().equals(returnToMenuButton))
				{
					LoggedInMenu menu = new LoggedInMenu(stage, currentPlayer, players);
				}
			}
		};
		
		viewPersonalScoresButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		viewGlobalLeaderboardButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		mainPane.getChildren().add(statDisplayPane);
		mainPane.getChildren().add(buttonBox);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 430, 400));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.show();
	}
	
	private void displayPersonalScores()
	{
		statDisplayPane.removeAllNodes();
		statDisplayPane.setColumns(NUM_PERSONAL_SCORE_COLUMNS);
		statDisplayPane.addAll(new Node[] {new Text(USERNAME_LABEL + ":"), new Text(currentPlayer.getUsername()),
								   new Text(CORSI_SPAN_LABEL + ":"), new Text(Integer.toString(currentPlayer.getMaxCorsiSpan())),
							       new Text(NUM_GAMES_LABEL + ":"), new Text(Integer.toString(currentPlayer.getNumberOfGames()))});
	}

	
	private void displayGlobalLeaderboard()
	{
		statDisplayPane.removeAllNodes();
		statDisplayPane.setColumns(NUM_GLOBAL_SCORE_COLUMNS);
		
		// Add column labels
		statDisplayPane.addNode(new Text(USERNAME_LABEL));
		statDisplayPane.addNode(new Text(CORSI_SPAN_LABEL));
		statDisplayPane.addNode(new Text(NUM_GAMES_LABEL));
		
		for (PlayerData player : players)
		{
			Text usernameText = new Text(player.getUsername());
			
			if (player.equals(currentPlayer))
			{
				usernameText.getStyleClass().add("scoreboard_player_username_text");
			}
			
			statDisplayPane.addNode(usernameText);
			statDisplayPane.addNode(new Text(Integer.toString(player.getMaxCorsiSpan())));
			statDisplayPane.addNode(new Text(Integer.toString(player.getNumberOfGames())));
		}
	}
	private void displayNoScoresMessage()
	{
		Alert noScoresAlert = new Alert(AlertType.INFORMATION);
		noScoresAlert.setTitle(NO_SCORES_MSG_TITLE);
		noScoresAlert.setHeaderText(NO_SCORES_MSG);
		noScoresAlert.showAndWait();
	}
}
