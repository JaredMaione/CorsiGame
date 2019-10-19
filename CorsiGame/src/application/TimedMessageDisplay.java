package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class TimedMessageDisplay
{
	private Text messageToDisplay;
	private double x;
	private double y;
	private double secToWait;
	private Stopwatch stopwatch;
	
	public TimedMessageDisplay(Text message, double secToWait)
	{
		messageToDisplay = message;

		this.secToWait = secToWait;
	}

	
	public void displayMessage()
	{
		stopwatch.start();
		messageToDisplay.setVisible(true);
		
		AnimationTimer sequencePlayTimer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				// Condition: the total time from the beginning of the blink to the delay for the next blink has passed
				if (stopwatch.getMSFromStart() > secToWait * 1000)
				{
					stopwatch.reset();
				}
			}
		};
		
		sequencePlayTimer.start();
	}

}
