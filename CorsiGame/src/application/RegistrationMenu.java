package application;

import javafx.scene.control.TextField;
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
	
	public RegistrationMenu(Stage stage)
	{
		formPane = new TwoColumnPane();
	}
}
