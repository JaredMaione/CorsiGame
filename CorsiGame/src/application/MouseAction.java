package application;

public class MouseAction extends TimestampedAction
{
	protected Position mousePosition;
	
	public MouseAction(long msFromStart, Position mousePosition)
	{
		super(msFromStart);
		this.mousePosition = mousePosition;
	}
	
	public MouseAction(long startTime, long currentTime, Position mousePosition)
	{
		super(startTime, currentTime);
		this.mousePosition = mousePosition;
	}
}
