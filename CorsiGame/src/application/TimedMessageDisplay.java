package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class TimedMessageDisplay extends AnimationTimer
{
	private Text messageToDisplay;
	private double x;
	private double y;
	private double secToWait;
	
	public TimedMessageDisplay(Text message, double secToWait)
	{
		messageToDisplay = message;

		this.secToWait = secToWait;
	}

	@Override
	public void handle(long now) 
	{
		try 
		{
			Thread.sleep((long) secToWait * 1000);
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.stop();
		
		messageToDisplay.setVisible(false);
	}
	
	public void displayMessage()
	{
		messageToDisplay.setVisible(true);
		this.start();
	}

}
