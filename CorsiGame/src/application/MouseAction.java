package application;

public class MouseAction extends TimestampedAction
{
	private Position clickPosition;
	
	public MouseAction(long msFromStart, Position clickPosition)
	{
		super(msFromStart);
		this.clickPosition = clickPosition;
	}
	
	public MouseAction(long startTime, long currentTime, Position clickPosition)
	{
		super(startTime, currentTime);
		this.clickPosition = clickPosition;
	}
}
