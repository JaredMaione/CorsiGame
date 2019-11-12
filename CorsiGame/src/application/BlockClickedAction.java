package application;

// This class logs the moment when the player clicks on a block
// It is a subclass of MouseAction, which is a subclass of TimestampedAction
public class BlockClickedAction extends MouseClickAction
{
	private static final long serialVersionUID = -3919053914713999810L;
	
	private CorsiBlock clickedBlock;
	
	public BlockClickedAction(long msFromStart, Position mousePosition, int clickCount, CorsiBlock clickedBlock)
	{
		super(msFromStart, mousePosition, clickCount);
		this.clickedBlock = clickedBlock;
	}
	
	public BlockClickedAction(long startTime, long currentTime, Position mousePosition,  int clickCount, CorsiBlock clickedBlock)
	{
		super(startTime, currentTime, mousePosition, clickCount);
		this.clickedBlock = clickedBlock;
	}
	
	public CorsiBlock getBlock()
	{
		return clickedBlock;
	}
}
