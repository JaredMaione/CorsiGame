package application;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReturningPlayerMenu 
{
	private final String USERNAME_FIELD_LABEL = "Username:";
	private final String PASSWORD_LABEL = "Password:";
	
	private final String SUBMIT_BUTTON_LABEL = "Submit & Play";
	private final String CANCEL_BUTTON_LABEL = "Cancel & Return to Menu";
	
	private TextField usernameField;
	private PasswordField passwordField;
	
	private Button submitButton;
	private Button cancelButton;
	
	private TwoColumnPane formPane;
	
	public ReturningPlayerMenu(Stage stage)
	{
		usernameField = new TextField();
		passwordField = new PasswordField();
		
		submitButton = new Button(SUBMIT_BUTTON_LABEL);
		cancelButton = new Button(CANCEL_BUTTON_LABEL);
		
		formPane = new TwoColumnPane();
		
		formPane.addAll(new Node[] 
				{
					new Text(USERNAME_FIELD_LABEL), usernameField,
					new Text(PASSWORD_LABEL), passwordField,
					submitButton, cancelButton
				});
		
		stage.setScene(new Scene(formPane, 300, 130));
		stage.setResizable(false);
		stage.show();
	}
}
