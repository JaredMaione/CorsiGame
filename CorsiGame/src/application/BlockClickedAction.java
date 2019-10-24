package application;

public class BlockClickedAction extends MouseAction
{
	private CorsiBlock clickedBlock;
	
	public BlockClickedAction(long msFromStart, Position clickPosition, CorsiBlock clickedBlock)
	{
		super(msFromStart, clickPosition);
		this.clickedBlock = clickedBlock;
	}
	
	public BlockClickedAction(long startTime, long currentTime, Position clickPosition, CorsiBlock clickedBlock)
	{
		super(startTime, currentTime, clickPosition);
		this.clickedBlock = clickedBlock;
	}
	
	public CorsiBlock getBlock()
	{
		return clickedBlock;
	}
}
