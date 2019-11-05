package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReturningPlayerMenu 
{
	private final String USERNAME_FIELD_LABEL = "Username:";
	private final String PASSWORD_LABEL = "Password:";
	
	private final String SUBMIT_BUTTON_LABEL = "Submit & Play";
	private final String CANCEL_BUTTON_LABEL = "Cancel & Return to Menu";
	
	private final String ERROR_TITLE = "Error";
	private final String INVALID_CREDENTIALS_MESSAGE = "Invalid username and/or password! Please try again.";
	
	private final String ADMIN_USERNAME = "admin";
	private final String ADMIN_PASSWORD = "CS3160";
	
	private final int ENTER_KEY_CODE = 13;
	
	private TextField usernameField;
	private PasswordField passwordField;
	
	private Button submitButton;
	private Button cancelButton;
	
	private FixedColumnGridPane formPane;
	
	private ArrayList<PlayerData> playersList;
	
	private Stage stage;
	
	public ReturningPlayerMenu(Stage stage, ArrayList<PlayerData> playersList)
	{
		usernameField = new TextField();
		passwordField = new PasswordField();
		
		submitButton = new Button(SUBMIT_BUTTON_LABEL);
		cancelButton = new Button(CANCEL_BUTTON_LABEL);
		
		this.playersList = playersList;
		this.stage = stage;
		
		formPane = new FixedColumnGridPane();
		
		formPane.addAll(new Node[] 
				{
					new Text(USERNAME_FIELD_LABEL), usernameField,
					new Text(PASSWORD_LABEL), passwordField,
					submitButton, cancelButton
				});
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(submitButton))
				{
					authenticateInput();
				}
				
				if (e.getSource().equals(cancelButton))
				{
					MainMenu menu = new MainMenu(stage);
				}
			}
		};
		
		EventHandler<KeyEvent> enterKeyHandler = new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent e) 
			{
				if (e.getCharacter().toCharArray()[0] == ENTER_KEY_CODE)
				{
					authenticateInput();
				}
			}
		};

		passwordField.addEventFilter(KeyEvent.KEY_TYPED, enterKeyHandler);
		
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		stage.setScene(new Scene(formPane, 300, 130));
		stage.setResizable(false);
		stage.show();
	}
	
	private void authenticateInput()
	{
		PlayerData player = matchCredentialsToPlayer(usernameField.getText().trim(), passwordField.getText().trim());
		
		if (player != null)
		{
			LoggedInMenu menu = new LoggedInMenu(stage, player, playersList);
		}
		else if (usernameField.getText().equals(ADMIN_USERNAME) && passwordField.getText().trim().equals(ADMIN_PASSWORD))
		{
			AdminMenu menu = new AdminMenu(stage, playersList);
		}
		else
		{
			Alert invalidPasswordAlert = new Alert(AlertType.INFORMATION);
			invalidPasswordAlert.setTitle(ERROR_TITLE);
			invalidPasswordAlert.setHeaderText(INVALID_CREDENTIALS_MESSAGE);
			invalidPasswordAlert.show();
		}
	}
	
	private PlayerData matchCredentialsToPlayer(String username, String password)
	{
		for (PlayerData player : playersList)
		{
			if (player.getUsername().equals(username) && player.getPassword().equals(password))
			{
				return player;
			}
		}
		
		return null;
	}
}
