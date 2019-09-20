package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CorsiBlock extends Rectangle
{
	private final Color LIT_COLOR = Color.YELLOW;
	private final Color UNLIT_COLOR = Color.BLUE;
	
	private boolean isLit;
	private Stopwatch blinkStopwatch;
	
	public CorsiBlock()
	{
		isLit = false;
		setFill(UNLIT_COLOR);
		
		blinkStopwatch = new Stopwatch();
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
}
