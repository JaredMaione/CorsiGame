package application;

public class GameEndAction extends TimestampedAction 
{
	public GameEndAction(long msFromStart)
	{
		super(msFromStart);
	}
	
	public GameEndAction(long startTime, long currentTime)
	{
		super(startTime, currentTime);
	}
}
