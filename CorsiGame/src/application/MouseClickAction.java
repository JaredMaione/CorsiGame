package application;

public class MouseClickAction extends MouseAction
{
	private int clickCount;
	private boolean dragged;
	
	public MouseClickAction(long msFromStart, Position mousePosition, int clickCount, boolean dragged)
	{
		super(msFromStart, mousePosition);
		this.clickCount = clickCount;
		this.dragged = dragged;
	}
	
	public MouseClickAction(long startTime, long currentTime, Position mousePosition, int clickCount, boolean dragged)
	{
		super(startTime, currentTime, mousePosition);
		
		this.clickCount = clickCount;
		this.dragged = dragged;
	}
}
