package application;

import java.io.File;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// This class is responsible for displaying the help menu
// It can transition back to the main menu when the player chooses to do so
public class HelpMenu 
{
	private final String MENU_BUTTON_TEXT = "Return to Menu";
	private final String HELP_FILE_NAME = "help.txt";
	
	private final int[] paddingData = {5, 5, 10, 15};
	
	private final int WINDOW_WIDTH = 600;
	private final int WINDOW_HEIGHT = 400;
	
	private Stage stage;
	private Button menuButton;
	private FlowPane mainPane;
	private ScrollPane scrollPane;
	
	public HelpMenu(Stage stage)
	{
		this.stage = stage;
		
		mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setPadding(new Insets(paddingData[0], paddingData[1], paddingData[2], paddingData[3]));
		
		scrollPane = new ScrollPane();
		
		menuButton = new Button(MENU_BUTTON_TEXT);
		
		EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e) 
			{
				MainMenu menu = new MainMenu(stage);
			}
		};

		
		menuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonHandler);
		
		File helpFolder = new File(System.getProperty("user.dir") + "\\" + FileManager.INFO_FILES_FOLDER);
		helpFolder.mkdirs();
		
		File helpFile = new File(helpFolder.getAbsolutePath() + "\\" + HELP_FILE_NAME);
		
		if (!helpFile.exists())
		{
			try 
			{
				helpFile.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		Text helpText = new Text(FileManager.readTextFile(helpFile));
		helpText.setWrappingWidth(WINDOW_WIDTH - 50);
		scrollPane.setContent(helpText);
		scrollPane.setPrefSize(WINDOW_WIDTH - 25, WINDOW_HEIGHT - 75);
		
		mainPane.getChildren().add(scrollPane);
		mainPane.getChildren().add(menuButton);
		
		stage.setScene(new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT));
		stage.setResizable(false);
		stage.show();
	}
}
