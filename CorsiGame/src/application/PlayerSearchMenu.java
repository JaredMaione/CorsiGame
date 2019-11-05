package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	private final int ENTER_KEY_CODE = 13;
	
	private Stage stage;
	private ArrayList<PlayerData> players;
	private ArrayList<ItemSelectionPane<PlayerData>> playerSelectionPanes;
	private FlowPane mainPane;
	
	private TextField searchField;
	
	private Button submitSearchButton;
	private Button viewPlayerButton;
	private Button backButton;
	
	private HBox searchBox;
	private HBox navigationBox;

	public PlayerSearchMenu(Stage stage, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.players = players;
		playerSelectionPanes = new ArrayList<ItemSelectionPane<PlayerData>>();
		
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
		
		navigationBox = new HBox();
		navigationBox.setSpacing(COMPONENT_SPACING);
		
		viewPlayerButton = new Button(VIEW_PLAYER_BUTTON_LABEL);
		backButton = new Button(BACK_BUTTON_LABEL);
		
		navigationBox.getChildren().add(viewPlayerButton);
		navigationBox.getChildren().add(backButton);
	
		mainPane.getChildren().add(searchBox);
		mainPane.getChildren().add(navigationBox);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(submitSearchButton))
				{
					displayPlayers(searchForPlayer(searchField.getText().trim()));
				}
				
				if (e.getSource().equals(viewPlayerButton))
				{
					
				}
				
				if (e.getSource().equals(backButton))
				{
					AdminMenu menu = new AdminMenu(stage, players);
				}
			}
		};
		
		submitSearchButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		viewPlayerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		
		EventHandler<KeyEvent> enterKeyHandler = new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent e) 
			{
				if (e.getCharacter().toCharArray()[0] == ENTER_KEY_CODE)
				{
					displayPlayers(searchForPlayer(searchField.getText().trim()));
				}
			}
		};
		
		searchField.addEventFilter(KeyEvent.KEY_TYPED, enterKeyHandler);
		
		stage.setScene(new Scene(mainPane, 400, 300));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
	}
	
	private void displayPlayers(ArrayList<PlayerData> playersToDisplay)
	{
		mainPane.getChildren().clear();
		mainPane.getChildren().add(searchBox);
		
		playerSelectionPanes.clear();
		
		for (PlayerData player : playersToDisplay)
		{
			ItemSelectionPane<PlayerData> pane = new ItemSelectionPane<PlayerData>(player, player.getUsername());
			mainPane.getChildren().add(pane);
			playerSelectionPanes.add(pane);
		}
		
		
		EventHandler<MouseEvent> resultClickHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource() instanceof ItemSelectionPane<?>)
				{
					for (ItemSelectionPane<PlayerData> pane : playerSelectionPanes)
					{
						if (e.getSource().equals(pane))
						{
							pane.setSelected(true);
						}
						else
						{
							pane.setSelected(false);
						}
					}
				}
			}
		};
		
		for (ItemSelectionPane<PlayerData> pane : playerSelectionPanes)
		{
			pane.addEventFilter(MouseEvent.MOUSE_CLICKED, resultClickHandler);
		}
		
		mainPane.getChildren().add(navigationBox);
	}
	
	private ArrayList<PlayerData> searchForPlayer(String username)
	{
		ArrayList<PlayerData> matchingPlayers = new ArrayList<PlayerData>();
		
		for (PlayerData player : players)
		{
			if (player.getUsername().toLowerCase().contains(username.toLowerCase()))
			{
				matchingPlayers.add(player);
			}
		}
		
		return matchingPlayers;
	}
}
