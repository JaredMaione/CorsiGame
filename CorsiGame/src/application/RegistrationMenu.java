package application;

import java.io.File;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	
	private final String SUBMIT_BUTTON_LABEL = "Submit";
	private final String CANCEL_BUTTON_LABEL = "Cancel";
	
	private TextField usernameField;
	private TextField dobField;
	private TextField cityField;
	private TextField stateField;
	private TextField countryField;
	private TextField diagnosisField;
	
	private TwoColumnPane formPane;
	
	private Button submitButton;
	private Button cancelButton;
	
	private Stage stage;
	
	public RegistrationMenu(Stage stage)
	{
		FlowPane mainPane = new FlowPane(Orientation.VERTICAL);
		formPane = new TwoColumnPane();
		
		usernameField = new TextField();
		dobField = new TextField();
		cityField = new TextField();
		stateField = new TextField();
		countryField = new TextField();
		diagnosisField = new TextField();
		
		formPane.addAll(new Node[] 
		{
			new Text(USERNAME_FIELD_LABEL), usernameField,
			new Text(DOB_FIELD_LABEL), dobField,
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
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(3);
		buttonBox.getChildren().add(submitButton);
		buttonBox.getChildren().add(cancelButton);
		mainPane.getChildren().add(buttonBox);
		
		stage.setScene(new Scene(mainPane, 400, 400));
		stage.show();		
	}
}
