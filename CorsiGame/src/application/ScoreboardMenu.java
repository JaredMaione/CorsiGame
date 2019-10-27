package application;

import java.util.ArrayList;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
			
	private TwoColumnPane statDisplayPane;
	private FlowPane mainPane;
	private Button viewPersonalScoresButton;
	private Button viewGlobalLeaderboardButton;
	private Button returnToMenuButton;
	
	private Stage stage;
	
	public ScoreboardMenu(Stage stage, ArrayList<PlayerData> players)
	{
		mainPane = new FlowPane(Orientation.VERTICAL);

		statDisplayPane = new TwoColumnPane();
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
		
		mainPane.getChildren().add(statDisplayPane);
		mainPane.getChildren().add(buttonBox);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 400, 400));
		stage.setResizable(false);
		stage.show();
	}
}
