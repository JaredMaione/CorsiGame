package application;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationMenu 
{
	private TwoColumnPane formPane;
	
	private final String NAME_FIELD_LABEL = "Name";
	private final String DOB_FIELD_LABEL = "Date of Birth";
	private final String CITY_FIELD_LABEL = "City";
	private final String STATE_FIELD_LABEL = "State, Province, or Region";
	private final String COUNTRY_FIELD_LABEL = "Country";
	private final String DIAGNOSIS_FIELD_LABEL = "Diagnosis";
	
	private TextField nameField;
	private TextField dobField;
	private TextField cityField;
	private TextField stateField;
	private TextField countryField;
	private TextField diagnosisField;
	
	private Stage stage;
	
	public RegistrationMenu(Stage stage)
	{
		formPane = new TwoColumnPane();
		
		nameField = new TextField();
		dobField = new TextField();
		cityField = new TextField();
		stateField = new TextField();
		countryField = new TextField();
		diagnosisField = new TextField();
		
		formPane.addAll(new Node[] 
		{
			new Text(NAME_FIELD_LABEL), nameField,
			new Text(DOB_FIELD_LABEL), dobField,
			new Text(CITY_FIELD_LABEL), cityField,
			new Text(STATE_FIELD_LABEL), stateField,
			new Text(COUNTRY_FIELD_LABEL), countryField,
			new Text(DIAGNOSIS_FIELD_LABEL), diagnosisField
		});
		
		stage.setScene(new Scene(formPane, 400, 400));
		
		
	}
}
