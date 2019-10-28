package application;

import java.util.ArrayList;

import javafx.stage.Stage;

public class LoggedInMenu 
{
	private ArrayList<PlayerData> players;
	private Stage stage;
	
	public LoggedInMenu(Stage stage, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.players = players;
	}
}
