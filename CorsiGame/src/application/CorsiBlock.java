package application;

import java.io.Serializable;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// This class is a clickable rectangle which may be placed on the screen
// It inherits from the JavaFX rectangle class and thus works with other JavaFX components
// It can blink when clicked, but does not have its own event handler
// The blink method should be called by the event handler
public class CorsiBlock extends Rectangle implements Serializable
{
	private static final long serialVersionUID = 8373831206086832375L;
	
	private final BlockColor LIT_COLOR = BlockColor.RED;
	private final BlockColor UNLIT_COLOR = BlockColor.BLUE;
	
	private boolean isClickable;
	private boolean isLit;
	private Stopwatch blinkStopwatch;
	
	private Position position;
	
	public CorsiBlock()
	{
		super();
		isLit = false;
		
		setFill(UNLIT_COLOR);
		
		blinkStopwatch = new Stopwatch();
		position = new Position(0, 0);
	}
	
	public CorsiBlock(double x, double y,  double sideLength)
	{
		this();
		this.setX(x);
		this.setY(y);
		this.position.set(x, y);
		this.setWidth(sideLength);
		this.setHeight(sideLength);
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public void refreshPosition()
	{
		this.setX(position.getX());
		this.setY(position.getY());
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
	
	public void setFill(BlockColor fillColor)
	{
		switch(fillColor)
		{
		case BLUE :
			setFill(Color.BLUE);
			break;
		case RED :
			setFill(Color.RED);
			break;
		default :
			setFill(Color.BLACK);
		}
		
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