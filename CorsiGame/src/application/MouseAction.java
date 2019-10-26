package application;

public class MouseAction extends TimestampedAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1731036758250251218L;
	
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
	
	public Position getMousePosition()
	{
		return mousePosition;
	}
}
