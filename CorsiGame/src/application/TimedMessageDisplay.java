package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

public class TimedMessageDisplay extends AnimationTimer
{
	private String messageToDisplay;
	private double x;
	private double y;
	private Group objGroup;
	private double secToWait;
	
	public TimedMessageDisplay(String message, double x, double y, Group group, double secToWait)
	{
		messageToDisplay = message;
		this.x = x;
		this.y = y;
		objGroup = group;
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
	}

}
