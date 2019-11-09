package application;

import com.sun.prism.paint.Color;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ReplayCursor extends Circle 
{
	private final static int CURSOR_RADIUS = 4;
	private final double CURSOR_BLINK_TIME = 0.1;
	private final Paint NORMAL_COLOR = Paint.valueOf("black");
	private final Paint BLINK_COLOR = Paint.valueOf("#00FF40");
	
	private Stopwatch cursorBlinkStopwatch;

			
	public ReplayCursor()
	{
		super(CURSOR_RADIUS);
		cursorBlinkStopwatch = new Stopwatch();
	}
	
	public void blink()
	{
		setFill(BLINK_COLOR);
		cursorBlinkStopwatch.start();
		
		AnimationTimer blinkTimer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if ((((double) cursorBlinkStopwatch.getMSFromStart()) / 1000.0) >= CURSOR_BLINK_TIME)
				{
					cursorBlinkStopwatch.reset();
					setFill(NORMAL_COLOR);
					this.stop();
				}
			}
		};
		
		blinkTimer.start();
	}
}
