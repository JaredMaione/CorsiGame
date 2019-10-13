package application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainMenu 
{
	private BorderPane pane;
	private HBox buttonBox;
	private final int BUTTON_SPACING = 10;
	
	private final String NEW_PLAYER_BUTTON_TEXT = "New Player Sign-Up";
	private final String EXISTING_PLAYER_BUTTON_TEXT = "Existing Player Login";
	private final String HELP_BUTTON_TEXT = "Help";
	private final String GAME_TITLE = "Standard Corsi Task";
	private final String GAME_AUTHOR = "Jared Maione";
	private final String DEVELOPMENT_YEAR = "2019";
	private final String DEVELOPMENT_PLACE = "University of Colorado Colorado Springs";
	
	private Button newPlayerButton;
	private Button existingPlayerButton;
	private Button helpButton;
	
	private Stage stage;
	
	public MainMenu(Stage stage)
	{
		this.stage = stage;
		stage.setResizable(false);
		
		pane = new BorderPane();
		stage.setScene(new Scene(pane, 400, 400));

		newPlayerButton = new Button(NEW_PLAYER_BUTTON_TEXT);
		existingPlayerButton = new Button(EXISTING_PLAYER_BUTTON_TEXT);
		helpButton = new Button(HELP_BUTTON_TEXT);
		
		buttonBox = new HBox();
		buttonBox.setSpacing(BUTTON_SPACING);
		buttonBox.getChildren().add(newPlayerButton);
		buttonBox.getChildren().add(existingPlayerButton);
		buttonBox.getChildren().add(helpButton);
		
		pane.setCenter(buttonBox);
		stage.show();
	}
}
