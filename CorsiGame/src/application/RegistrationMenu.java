package application;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationMenu 
{	
	private final String USERNAME_FIELD_LABEL = "Username:";
	private final String DOB_FIELD_LABEL = "Date of Birth:";
	private final String CITY_FIELD_LABEL = "City:";
	private final String STATE_FIELD_LABEL = "State, Province, or Region:";
	private final String COUNTRY_FIELD_LABEL = "Country:";
	private final String DIAGNOSIS_FIELD_LABEL = "Diagnosis:";
	private final String TERMS_LABEL = "By clicking \"Submit\", you consent to the storage and analysis of all information provided. You " +
									   "also consent to the analysis of any and all game activity. If you click \"Cancel\", no information " +
									   	"will be stored and you will be returned to the main menu.";
	private final String PASSWORD_LABEL = "Password:";
	private final String PASSWORD_LABEL_CONFIRM = "Confirm password:";
	
	private final String SUBMIT_BUTTON_LABEL = "Submit & Play Game!";
	private final String CANCEL_BUTTON_LABEL = "Cancel";
	
	private final String ERROR_TITLE = "Invalid Data!";
	private final String INVALID_INPUT_MESSAGE = "One or more of the fields is either not filled or is filled incorrectly. Please review and try again.";
	
	private TextField usernameField;
	private PasswordField passwordField;
	private PasswordField passwordConfirmField;
	
	private DatePicker dobSelect;
	private TextField cityField;
	private TextField stateField;
	private TextField countryField;
	private TextField diagnosisField;
	
	private FixedColumnGridPane formPane;
	
	private Button submitButton;
	private Button cancelButton;
	
	private Stage stage;
	
	private ArrayList<PlayerData> players;
	
	public RegistrationMenu(Stage stage, ArrayList<PlayerData> players)
	{
		this.stage = stage;
		this.players = players;
		
		FlowPane mainPane = new FlowPane(Orientation.VERTICAL);
		
		formPane = new FixedColumnGridPane();
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		passwordConfirmField = new PasswordField();
		dobSelect = new DatePicker(LocalDate.now());
		cityField = new TextField();
		stateField = new TextField();
		countryField = new TextField();
		diagnosisField = new TextField();
		
		formPane.addAll(new Node[] 
		{
			new Text(USERNAME_FIELD_LABEL), usernameField,
			new Text(PASSWORD_LABEL), passwordField,
			new Text(PASSWORD_LABEL_CONFIRM), passwordConfirmField,
			new Text(DOB_FIELD_LABEL), dobSelect,
			new Text(CITY_FIELD_LABEL), cityField,
			new Text(STATE_FIELD_LABEL), stateField,
			new Text(COUNTRY_FIELD_LABEL), countryField,
			new Text(DIAGNOSIS_FIELD_LABEL), diagnosisField
		});
		
		mainPane.setPadding(formPane.getPadding());
		
		mainPane.getChildren().add(formPane);
		
		Text termsText = new Text(TERMS_LABEL);
		termsText.setWrappingWidth(350);
		mainPane.getChildren().add(termsText);
		
		submitButton = new Button(SUBMIT_BUTTON_LABEL);
		cancelButton = new Button(CANCEL_BUTTON_LABEL);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				if (e.getSource().equals(submitButton))
				{
					if (inputDataValid())
					{
						createProfileAndLogin();
					}
					else
					{
						Alert invalidDataAlert = new Alert(AlertType.INFORMATION);
						invalidDataAlert.setTitle(ERROR_TITLE);
						invalidDataAlert.setHeaderText(INVALID_INPUT_MESSAGE);
						invalidDataAlert.showAndWait();
					}
				}
				
				if (e.getSource().equals(cancelButton))
				{
					MainMenu menu = new MainMenu(stage);
				}
			}
		};
		
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(3);
		buttonBox.getChildren().add(submitButton);
		buttonBox.getChildren().add(cancelButton);
		buttonBox.setPadding(formPane.getPadding());
		mainPane.getChildren().add(buttonBox);
		
		stage.setScene(new Scene(mainPane, 400, 450));
		stage.show();
	}
	
	private void createProfileAndLogin()
	{
		PlayerData player = new PlayerData();
		player.setUsername(usernameField.getText().trim());
		player.setPassword(passwordField.getText().trim());
		player.setCity(cityField.getText().trim());
		player.setState(stateField.getText().trim());
		player.setCountry(countryField.getText().trim());
		player.setDiagnosis(diagnosisField.getText().trim());
		player.setDob(new Date(dobSelect.getValue()));
		
		players.add(player);
		
		LoggedInMenu menu = new LoggedInMenu(stage, player, players);
	}
	
	private boolean inputDataValid()
	{
		return !usernameField.getText().equals("") &&
			   !passwordField.getText().equals("") &&
			   !passwordConfirmField.getText().equals("") &&
			   (passwordField.getText().equals(passwordConfirmField.getText())) &&
			   !cityField.getText().equals("") &&
			   !stateField.getText().equals("") &&
			   !countryField.getText().equals("") &&
			   !diagnosisField.getText().equals("") &&
			   dobSelect.getValue() != null;
	}
}
