package application;

import java.util.ArrayList;
import java.util.Collections;
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

// This class manages the scoreboard. It can be configured
// to display personal score data, or allow the user to compare their
// score with other players
public class ScoreboardMenu 
{
	private final String GLOBAL_LEADERBOARD_BUTTON_LABEL = "View Global Leaderboard";
	private final String PERSONAL_SCORES_BUTTON_LABEL = "View Personal Scores";
	private final String RETURN_TO_MENU_BUTTON_LABEL = "Return to Menu";
	private final String USERNAME_LABEL = "Username";
	private final String MAX_CORSI_SPAN_LABEL = "Highest Corsi Span";
	private final String NUM_GAMES_LABEL = "Number of Games Played";
	private final String PAGE_LEFT_LABEL = "< Page Left <";
	private final String PAGE_RIGHT_LABEL = "> Page Right >";
	
	private final String CORSI_SPAN_LABEL = "Corsi Span";
	private final String GAME_DURATION_LABEL = "Game Duration (m:s:ms)";
	private final String AVG_SEQ_TIME_LABEL = "Average Time to Complete a Sequence (m:s:ms)";

	private final String NO_SCORES_MSG_TITLE = "No Scores in This Category";
	private final String NO_SCORES_MSG = "There are no scores to be displayed in this category.";
	
	private final int BUTTON_SPACING = 4;
	
	private final int NUM_PERSONAL_SCORE_COLUMNS = 2;
	private final int NUM_GLOBAL_SCORE_COLUMNS = 3;
	private final int NUM_SINGLE_SCORE_COLUMNS = 2;
	
	private final int SCORES_PER_PAGE = 8;
			
	private FixedColumnGridPane statDisplayPane;
	private FlowPane mainPane;
	private Button viewPersonalScoresButton;
	private Button viewGlobalLeaderboardButton;
	private Button returnToMenuButton;
	
	private Button pageLeftButton;
	private Button pageRightButton;
	
	private Stage stage;
	
	private ArrayList<PlayerData> players;
	
	private ArrayList<PlayerData> sortedPlayers;
	private int scoresListTargetIndex;
	
	private PlayerData currentPlayer;
	
	// Constructor to be used in PlayerViewMenu (for a single game played by a single player)
	public ScoreboardMenu(Stage stage, PlayerData player, GameData game)
	{
		this.players = new ArrayList<PlayerData>();
		this.currentPlayer = player;

		sortedPlayers = new ArrayList<PlayerData>(players);
		scoresListTargetIndex = 0;
		
		mainPane = new FlowPane(Orientation.VERTICAL);

		statDisplayPane = new FixedColumnGridPane();
		
		pageLeftButton = new Button(PAGE_LEFT_LABEL);
		pageRightButton = new Button(PAGE_RIGHT_LABEL);
		
		returnToMenuButton = new Button(RETURN_TO_MENU_BUTTON_LABEL);
				
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{				
				if (e.getSource().equals(returnToMenuButton))
				{
					PlayerViewMenu menu = new PlayerViewMenu(stage, currentPlayer, players);
				}
			}
		};
		
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		mainPane.getChildren().add(statDisplayPane);
		mainPane.getChildren().add(returnToMenuButton);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 430, 200));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.show();
		
		displayScore(game);
	}
	
	// Constructor to be used for global AdminMenu scoreboard
	// This hides personal score information button (no player is logged in)
	public ScoreboardMenu(Stage stage, ArrayList<PlayerData> players)
	{
		this.players = players;
		
		sortedPlayers = new ArrayList<PlayerData>(players);
		Collections.sort(sortedPlayers, Collections.reverseOrder());
		scoresListTargetIndex = 0;
		
		mainPane = new FlowPane(Orientation.VERTICAL);

		statDisplayPane = new FixedColumnGridPane();
		
		pageLeftButton = new Button(PAGE_LEFT_LABEL);
		pageRightButton = new Button(PAGE_RIGHT_LABEL);
		
		HBox pageButtonBox = new HBox();
		pageButtonBox.setPadding(statDisplayPane.getPadding());
		pageButtonBox.setSpacing(BUTTON_SPACING);
		pageButtonBox.getChildren().add(pageLeftButton);
		pageButtonBox.getChildren().add(pageRightButton);
		
		pageLeftButton.setDisable(true);
		pageRightButton.setDisable(true);
		
		returnToMenuButton = new Button(RETURN_TO_MENU_BUTTON_LABEL);
				
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{				
				if (e.getSource().equals(returnToMenuButton))
				{
					AdminMenu menu = new AdminMenu(stage, players);
				}
				
				if (e.getSource().equals(pageLeftButton))
				{
					scoresListTargetIndex -= SCORES_PER_PAGE / 2;
					
					if (scoresListTargetIndex >= 0)
					{
						displayGlobalLeaderboard(scoresListTargetIndex);
					}
					else
					{
						scoresListTargetIndex = 0;
					}
				}
				
				if (e.getSource().equals(pageRightButton)) 
				{
					scoresListTargetIndex += SCORES_PER_PAGE / 2;
					
					if (scoresListTargetIndex < sortedPlayers.size())
					{
						displayGlobalLeaderboard(scoresListTargetIndex);
					}
					else
					{
						scoresListTargetIndex = sortedPlayers.size() - 1;
					}
				}
			}
		};
		
		pageLeftButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		pageRightButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		mainPane.getChildren().add(statDisplayPane);
		mainPane.getChildren().add(pageButtonBox);
		mainPane.getChildren().add(returnToMenuButton);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 430, 375));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.show();
		
		displayGlobalLeaderboard(0);
	}
	
	// Constructor to be used when player is logged in
	// This will allow the player to view the global scoreboard or their own score information
	public ScoreboardMenu(Stage stage, ArrayList<PlayerData> players, PlayerData currentPlayer)
	{
		this.players = players;
		
		sortedPlayers = new ArrayList<PlayerData>(players);
		Collections.sort(sortedPlayers, Collections.reverseOrder());
		scoresListTargetIndex = sortedPlayers.indexOf(currentPlayer);
		
		this.currentPlayer = currentPlayer;
		mainPane = new FlowPane(Orientation.VERTICAL);

		statDisplayPane = new FixedColumnGridPane();
		
		pageLeftButton = new Button(PAGE_LEFT_LABEL);
		pageRightButton = new Button(PAGE_RIGHT_LABEL);
		
		HBox pageButtonBox = new HBox();
		pageButtonBox.setPadding(statDisplayPane.getPadding());
		pageButtonBox.setSpacing(BUTTON_SPACING);
		pageButtonBox.getChildren().add(pageLeftButton);
		pageButtonBox.getChildren().add(pageRightButton);
		
		pageLeftButton.setDisable(true);
		pageRightButton.setDisable(true);
		
		viewPersonalScoresButton = new Button(PERSONAL_SCORES_BUTTON_LABEL);
		viewGlobalLeaderboardButton = new Button(GLOBAL_LEADERBOARD_BUTTON_LABEL);
		returnToMenuButton = new Button(RETURN_TO_MENU_BUTTON_LABEL);
		
		HBox navigationButtonBox = new HBox();
		navigationButtonBox.setPadding(statDisplayPane.getPadding());
		navigationButtonBox.setSpacing(BUTTON_SPACING);
		
		navigationButtonBox.getChildren().add(viewPersonalScoresButton);
		navigationButtonBox.getChildren().add(viewGlobalLeaderboardButton);
		navigationButtonBox.getChildren().add(returnToMenuButton);
		
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
							//displayGlobalLeaderboard();
							displayGlobalLeaderboard(scoresListTargetIndex);
							return;
						}
					}
					
					displayNoScoresMessage();
				}
				
				if (e.getSource().equals(returnToMenuButton))
				{
					LoggedInMenu menu = new LoggedInMenu(stage, currentPlayer, players);
				}
				
				if (e.getSource().equals(pageLeftButton))
				{
					scoresListTargetIndex -= SCORES_PER_PAGE / 2;
					
					if (scoresListTargetIndex >= 0)
					{
						displayGlobalLeaderboard(scoresListTargetIndex);
					}
					else
					{
						scoresListTargetIndex = 0;
					}
				}
				
				if (e.getSource().equals(pageRightButton)) 
				{
					scoresListTargetIndex += SCORES_PER_PAGE / 2;
					
					if (scoresListTargetIndex < sortedPlayers.size())
					{
						displayGlobalLeaderboard(scoresListTargetIndex);
					}
					else
					{
						scoresListTargetIndex = sortedPlayers.size() - 1;
					}
				}
			}
		};
		
		pageLeftButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		pageRightButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		viewPersonalScoresButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		viewGlobalLeaderboardButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		mainPane.getChildren().add(statDisplayPane);
		mainPane.getChildren().add(pageButtonBox);
		mainPane.getChildren().add(navigationButtonBox);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 430, 400));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.show();
	}
	
	public void displayScore(GameData score)
	{
		statDisplayPane.removeAllNodes();
		pageLeftButton.setDisable(true);
		pageRightButton.setDisable(true);
		
		statDisplayPane.setColumns(NUM_SINGLE_SCORE_COLUMNS);
		statDisplayPane.addAll(new Node[] {new Text(USERNAME_LABEL + ":"), new Text(currentPlayer.getUsername()),
										   new Text(CORSI_SPAN_LABEL + ":"), new Text(Integer.toString(score.getCorsiSpan())),
										   new Text(GAME_DURATION_LABEL + ":"), new Text(score.getGameDuration().sendToString()),
										   new Text(AVG_SEQ_TIME_LABEL + ":"), new Text(score.getAvgSequenceTime().sendToString())});
	}
	
	public void displayPersonalScores()
	{
		if (currentPlayer != null)
		{
			statDisplayPane.removeAllNodes();
			statDisplayPane.setColumns(NUM_PERSONAL_SCORE_COLUMNS);
			statDisplayPane.addAll(new Node[] {new Text(USERNAME_LABEL + ":"), new Text(currentPlayer.getUsername()),
									   new Text(MAX_CORSI_SPAN_LABEL + ":"), new Text(Integer.toString(currentPlayer.getMaxCorsiSpan())),
								       new Text(NUM_GAMES_LABEL + ":"), new Text(Integer.toString(currentPlayer.getNumberOfGames()))});
			
			pageLeftButton.setDisable(true);
			pageRightButton.setDisable(true);
		}
	}
	
	public void displayGlobalLeaderboard(int target)
	{
		pageLeftButton.setDisable(false);
		pageRightButton.setDisable(false);
		
		statDisplayPane.removeAllNodes();
		statDisplayPane.setColumns(NUM_GLOBAL_SCORE_COLUMNS);
		
		// Add column labels
		statDisplayPane.addNode(new Text(USERNAME_LABEL));
		statDisplayPane.addNode(new Text(MAX_CORSI_SPAN_LABEL));
		statDisplayPane.addNode(new Text(NUM_GAMES_LABEL));

		// Get preliminary starting and ending indexes
		int startingIndex = target - (SCORES_PER_PAGE / 2);
		int endingIndex = target + (SCORES_PER_PAGE / 2);
		
		// Verify that starting and ending indexes are not out of bounds
		while (startingIndex < 0)
		{
			++startingIndex;
			pageLeftButton.setDisable(true);
		}
		
		while (endingIndex >= sortedPlayers.size())
		{
			--endingIndex;
			pageRightButton.setDisable(true);
		}
			
		// Modify endingIndex to get SCORES_PER_PAGE scores into the window if possible
		while (endingIndex - startingIndex < SCORES_PER_PAGE && endingIndex < sortedPlayers.size())
		{
			++endingIndex;
		}
		
		// Add information for all players which are on this page
		for (int i = startingIndex; i < endingIndex; ++i)
		{
			PlayerData player = sortedPlayers.get(i);
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
