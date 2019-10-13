package application;

import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu 
{
	private BorderPane mainPane;
	private HBox buttonBox;
	private final int BUTTON_SPACING = 10;

	
	private final String NEW_PLAYER_BUTTON_TEXT = "New Player Sign-Up";
	private final String EXISTING_PLAYER_BUTTON_TEXT = "Existing Player Login";
	private final String HELP_BUTTON_TEXT = "Help";
	private final String GAME_TITLE = "Standard Corsi Task";
	private final String GAME_AUTHOR = "Developed by Jared Maione";
	
	private Button newPlayerButton;
	private Button existingPlayerButton;
	private Button helpButton;
	
	private Stage stage;
	
	public MainMenu(Stage stage)
	{
		this.stage = stage;
		stage.setResizable(false);
		
		mainPane = new BorderPane();
		stage.setScene(new Scene(mainPane, 400, 400));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		FlowPane textPane = new FlowPane(Orientation.VERTICAL);
		Text gameTitle = new Text(GAME_TITLE);
		gameTitle.getStyleClass().add("title_text");
		
		Text gameAuthor = new Text(GAME_AUTHOR);
		gameAuthor.getStyleClass().add("subtitle_text");
		
		textPane.getChildren().add(gameTitle);
		textPane.getChildren().add(gameAuthor);
		textPane.setPrefHeight(80);
		
		mainPane.setTop(textPane);
		
		newPlayerButton = new Button(NEW_PLAYER_BUTTON_TEXT);
		existingPlayerButton = new Button(EXISTING_PLAYER_BUTTON_TEXT);
		helpButton = new Button(HELP_BUTTON_TEXT);
		
		buttonBox = new HBox();
		buttonBox.setSpacing(BUTTON_SPACING);
		buttonBox.getChildren().add(newPlayerButton);
		buttonBox.getChildren().add(existingPlayerButton);
		buttonBox.getChildren().add(helpButton);
		
		mainPane.setCenter(buttonBox);
		stage.show();
	}
}
