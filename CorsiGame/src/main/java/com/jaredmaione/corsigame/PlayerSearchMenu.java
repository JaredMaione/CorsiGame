package com.jaredmaione.corsigame;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// This class manages the menu by which the administer can search for a specific player
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
	
	private ScrollPane searchResultsScrollPane;

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
		
		searchResultsScrollPane = new ScrollPane();
		searchResultsScrollPane.setPrefSize(100, 100);
		searchResultsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		searchResultsScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	
		mainPane.getChildren().add(searchBox);
		mainPane.getChildren().add(searchResultsScrollPane);
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
					PlayerData player = getSelectedPlayer();
					if (player != null)
					{
						PlayerViewMenu menu = new PlayerViewMenu(stage, player, players);
					}
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
		// stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
	}
	
	// Returns the PlayerData object whose ItemSelectionPane is currently selected
	private PlayerData getSelectedPlayer()
	{
		for (ItemSelectionPane<PlayerData> pane : playerSelectionPanes)
		{
			if (pane.isSelected())
			{
				return pane.getObj();
			}
		}
		
		return null;
	}
	
	// Creates ItemSelectionPanes for all players to be displayed and adds them to the menu
	private void displayPlayers(ArrayList<PlayerData> playersToDisplay)
	{
		FlowPane playerDisplayPane = new FlowPane(Orientation.VERTICAL);
		
		playerDisplayPane.getChildren().clear();
		
		playerSelectionPanes.clear();
		
		// Mouse click handler for pane selection
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
		
		for (PlayerData player : playersToDisplay)
		{
			ItemSelectionPane<PlayerData> pane = new ItemSelectionPane<PlayerData>(player, player.getUsername());
			playerDisplayPane.getChildren().add(pane);
			pane.addEventFilter(MouseEvent.MOUSE_CLICKED, resultClickHandler);
			playerSelectionPanes.add(pane);
		}
		
		searchResultsScrollPane.setContent(playerDisplayPane);
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