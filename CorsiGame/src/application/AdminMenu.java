package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminMenu 
{
	private final int COMPONENT_SPACING = 6;
	private final int PADDING_VALUE = 5;
	
	private FlowPane mainPane;
	private ArrayList<PlayerData> players;
	private Stage stage;
	
	private Button viewScoreboardButton;
	private Button playerLookupButton;
	private Button mainMenuButton;
	
	private final String ADMIN_LOGGED_IN_MESSAGE = "Administrator Menu";
	
	public AdminMenu(Stage stage, ArrayList<PlayerData> players)
	{
		HBox buttonBox = new HBox();
		this.players = players;
		
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));
		mainPane.getChildren().add(new GameInformationHeader());
		mainPane.getChildren().add(new Text(ADMIN_LOGGED_IN_MESSAGE));
		mainPane.setVgap(COMPONENT_SPACING);
		mainPane.getChildren().add(buttonBox);
		
		this.stage = stage;
		stage.setScene(new Scene(mainPane, 360, 120));
	}
}
