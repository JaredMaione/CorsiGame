package application;

public class BlockClickedAction extends MouseClickAction
{
	private CorsiBlock clickedBlock;
	
	public BlockClickedAction(long msFromStart, Position mousePosition, int clickCount, boolean dragged, CorsiBlock clickedBlock)
	{
		super(msFromStart, mousePosition, clickCount, dragged);
		this.clickedBlock = clickedBlock;
	}
	
	public BlockClickedAction(long startTime, long currentTime, Position mousePosition,  int clickCount, boolean dragged, CorsiBlock clickedBlock)
	{
		super(startTime, currentTime, mousePosition, clickCount, dragged);
		this.clickedBlock = clickedBlock;
	}
	
	public CorsiBlock getBlock()
	{
		return clickedBlock;
	}
}
