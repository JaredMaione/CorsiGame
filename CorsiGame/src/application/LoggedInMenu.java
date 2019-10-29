package application;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoggedInMenu 
{
	private final String PLAY_BUTTON_LABEL = "Play Game";
	private final String SCOREBOARD_BUTTON_LABEL = "View Scoreboard";
	private final String LOGOUT_BUTTON_LABEL = "Logout & Return to Menu";

	private ArrayList<PlayerData> players;
	private PlayerData loggedInPlayer;
	
	private Stage stage;
	
	private Button playButton;
	private Button logoutButton;
	private Button scoreboardButton;
	
	public LoggedInMenu(Stage stage, PlayerData loggedInPlayer, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.players = players;
	}
}
