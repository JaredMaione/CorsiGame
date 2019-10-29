package application;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class GameInformationHeader extends FlowPane
{
	private final String GAME_TITLE = "Corsi Task";
	private final String GAME_AUTHOR = "Developed by Jared Maione";
	
	private final int HEIGHT = 55;
	
	public GameInformationHeader()
	{
		this.setOrientation(Orientation.VERTICAL);
		Text gameTitle = new Text(GAME_TITLE);
		gameTitle.getStyleClass().add("title_text");
		
		Text gameAuthor = new Text(GAME_AUTHOR);
		gameAuthor.getStyleClass().add("subtitle_text");
		
		getChildren().add(gameTitle);
		getChildren().add(gameAuthor);
		setPrefHeight(HEIGHT);
	}
}