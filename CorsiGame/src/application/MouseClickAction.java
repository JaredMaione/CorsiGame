package application;

public class MouseClickAction extends MouseAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6702552219772516694L;
	
	private int clickCount;
	
	public MouseClickAction(long msFromStart, Position mousePosition, int clickCount)
	{
		super(msFromStart, mousePosition);
		this.clickCount = clickCount;
	}
	
	public MouseClickAction(long startTime, long currentTime, Position mousePosition, int clickCount)
	{
		super(startTime, currentTime, mousePosition);
		
		this.clickCount = clickCount;
	}
}
