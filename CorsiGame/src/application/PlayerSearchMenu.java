package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayerSearchMenu 
{
	private final String SEARCH_FIELD_LABEL = "Enter player username:";
	private final String SUBMIT_SEARCH_BUTTON_LABEL = "Search";
	
	private final String VIEW_PLAYER_BUTTON_LABEL = "View Player";
	private final String BACK_BUTTON_LABEL = "Back";
	
	private final int COMPONENT_SPACING = 6;
	private final int PADDING_VALUE = 5;
	
	private Stage stage;
	private ArrayList<PlayerData> players;
	private FlowPane mainPane;
	
	private TextField searchField;
	
	private Button submitSearchButton;
	private Button viewPlayerButton;
	private Button backButton;
	
	private HBox searchBox;

	public PlayerSearchMenu(Stage stage, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.players = players;
		
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setHgap(COMPONENT_SPACING);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));

		searchBox = new HBox();
		searchBox.setSpacing(COMPONENT_SPACING);
		
		searchField = new TextField();
		submitSearchButton = new Button(SUBMIT_SEARCH_BUTTON_LABEL);

		searchBox.getChildren().add(new Text(SEARCH_FIELD_LABEL));
		searchBox.getChildren().add(searchField);
		
		searchBox.getChildren().add(submitSearchButton);
		
		mainPane.getChildren().add(searchBox);
		
		stage.setScene(new Scene(mainPane, 400, 300));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
	}
}
