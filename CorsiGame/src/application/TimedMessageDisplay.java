package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.text.Text;

public abstract class TimedMessageDisplay
{
	public static void displayMessage(Text message, double delayToStart, double secVisible)
	{
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if (stopwatch.getMSFromStart() > delayToStart * 1000)
				{
					message.setVisible(true);
				}
				
				if (stopwatch.getMSFromStart()  > (delayToStart + secVisible) * 1000)
				{
					stopwatch.reset();
					message.setVisible(false);
					this.stop();
				}
			}
		};
		
		timer.start();
	}

}
