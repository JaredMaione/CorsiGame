package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	private final int BUTTON_SPACING = 4;
			
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
		statDisplayPane.addNode(new Text("Foo"));
		
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
					displayPersonalScores();
				}
				
				if (e.getSource().equals(viewGlobalLeaderboardButton))
				{
					displayGlobalLeaderboard();
				}
				
				if (e.getSource().equals(returnToMenuButton))
				{
					MainMenu menu = new MainMenu(stage);
				}
			}
		};
		
		viewPersonalScoresButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		viewGlobalLeaderboardButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		returnToMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);

		mainPane.getChildren().add(statDisplayPane);
		mainPane.getChildren().add(buttonBox);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 400, 400));
		stage.setResizable(false);
		stage.show();
	}
	
	private void displayPersonalScores()
	{
		
	}
	
	private void displayGlobalLeaderboard()
	{
		for (PlayerData player : players)
		{
		}
	}
}
