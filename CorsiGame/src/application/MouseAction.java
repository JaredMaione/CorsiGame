package application;

// This class keeps track of when and where the mouse moves at a particular time
public class MouseAction extends TimestampedAction
{
	private static final long serialVersionUID = 1731036758250251218L;
	
	private Position mousePosition;
	
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
	
	public Position getMousePosition()
	{
		return mousePosition;
	}
}
