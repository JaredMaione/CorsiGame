package application;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CorsiBlock extends Rectangle
{
	private final Color LIT_COLOR = Color.RED;
	private final Color UNLIT_COLOR = Color.BLUE;
	
	private boolean isClickable;
	private boolean isLit;
	private Stopwatch blinkStopwatch;
	
	public CorsiBlock()
	{
		super();
		isLit = false;
		setFill(UNLIT_COLOR);
		
		blinkStopwatch = new Stopwatch();
	}
	
	public CorsiBlock(double x, double y,  double sideLength)
	{
		this();
		this.setX(x);
		this.setY(y);
		this.setWidth(sideLength);
		this.setHeight(sideLength);
	}
	
	public void blink(double numSeconds)
	{
		setLit(true);
		blinkStopwatch.start();
		
		AnimationTimer blinkTimer = new AnimationTimer()
		{
			@Override
			public void handle(long arg0) 
			{
				if ((((double) blinkStopwatch.getMSFromStart()) / 1000.0) >= numSeconds)
				{
					setLit(false);
					blinkStopwatch.reset();
					this.stop();
				}
			}

		};
		
		blinkTimer.start();
	}
	
	public void setLit(boolean lit)
	{
		isLit = lit;
		
		if (isLit)
		{
			setFill(LIT_COLOR);
		}
		else
		{
			setFill(UNLIT_COLOR);
		}
	}
	
	public boolean isLit()
	{
		return isLit;
	}
	
	public void setClickable(boolean canClick)
	{
		isClickable = canClick;
	}
	
	public boolean isClickable()
	{
		return isClickable;
	}
}