package application;

public class TimestampedAction 
{
	protected long msFromStart;
	
	public TimestampedAction(long msFromStart)
	{
		this.msFromStart = msFromStart;
	}
	
	// This constructor calculates msFromStart (calculation is abstracted away)
	public TimestampedAction(long startTime, long currentTime)
	{
		msFromStart = currentTime - startTime;
	}
}
