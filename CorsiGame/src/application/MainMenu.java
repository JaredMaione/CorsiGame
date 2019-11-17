package application;

import java.io.File;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// This class manages the main menu, which is the first window the user
// sees upon launching the game
// The player can navigate from the main menu into player registration, player login,
// or help menu
public class MainMenu 
{
	private BorderPane mainPane;
	private HBox buttonBox;
	
	private final int BUTTON_SPACING = 10;
	private final int PADDING_VALUE = 5;
	
	private final String NEW_PLAYER_BUTTON_TEXT = "New Player Sign-Up";
	private final String EXISTING_PLAYER_BUTTON_TEXT = "Existing Player Login";
	private final String HELP_BUTTON_TEXT = "Help";
	
		
	private Button newPlayerButton;
	private Button existingPlayerButton;
	private Button helpButton;
	
	private Stage stage;
	
	private ArrayList<PlayerData> players;
	
	public MainMenu(Stage stage)
	{
		players = loadPlayers();
		
		this.stage = stage;
		stage.setResizable(false);
		
		mainPane = new BorderPane();
		stage.setScene(new Scene(mainPane, 350, 100));
		stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		GameInformationHeader titlePane = new GameInformationHeader();
		
		mainPane.setTop(titlePane);
		mainPane.setPadding(new Insets(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE));
		
		newPlayerButton = new Button(NEW_PLAYER_BUTTON_TEXT);
		existingPlayerButton = new Button(EXISTING_PLAYER_BUTTON_TEXT);
		helpButton = new Button(HELP_BUTTON_TEXT);
		
		buttonBox = new HBox();
		buttonBox.setSpacing(BUTTON_SPACING);
		buttonBox.getChildren().add(newPlayerButton);
		buttonBox.getChildren().add(existingPlayerButton);
		buttonBox.getChildren().add(helpButton);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(newPlayerButton))
				{
					RegistrationMenu menu = new RegistrationMenu(stage, players);
				}
				
				if (e.getSource().equals(existingPlayerButton))
				{
					ReturningPlayerMenu menu = new ReturningPlayerMenu(stage, players);
				}
			}
		};
		
		EventHandler<WindowEvent> windowCloseHandler = new EventHandler<WindowEvent>() 
		{
	          public void handle(WindowEvent e) 
	          {
	              for (PlayerData player : players)
	              {
	            	  FileManager.writeEncrypted(player, System.getProperty("user.dir") + "\\" + FileManager.PLAYER_FILES_FOLDER + "\\" + player.getUsername() + ".ser");
	              }
	          }
		};
		
		stage.setOnCloseRequest(windowCloseHandler);
		
		newPlayerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		existingPlayerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		helpButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		mainPane.setCenter(buttonBox);
		stage.show();
	}
	
	// This method loads all PlayerData files into memory via the FileManager utility class
	private ArrayList<PlayerData> loadPlayers()
	{
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();

		File playerFolder = new File(System.getProperty("user.dir") + "\\" + FileManager.PLAYER_FILES_FOLDER);
		
		if (!playerFolder.exists())
		{
			playerFolder.mkdirs();
		}
		
		if (playerFolder.listFiles() != null)
		{
			for (File file : playerFolder.listFiles())
			{
				if (file.getName().substring(file.getName().indexOf("."), file.getName().length()).equals(".ser"))
				{
					players.add((PlayerData) FileManager.decryptAndReadObj(file.getAbsolutePath()));
				}
			}
		}
				
		return players;
	}
}
