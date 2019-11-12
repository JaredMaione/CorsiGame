package application;

public class GameEndAction extends TimestampedAction 
{
	private static final long serialVersionUID = -237313697040482769L;

	public GameEndAction(long msFromStart)
	{
		super(msFromStart);
	}
	
	public GameEndAction(long startTime, long currentTime)
	{
		super(startTime, currentTime);
	}
}
