package application;

public abstract class TimestampedAction 
{
	protected long msFromStart;
	
	public TimestampedAction(long msFromStart)
	{
		this.msFromStart = msFromStart;
	}
	
	// This constructor calculates msFromStart (calculation is abstracted away from caller)
	public TimestampedAction(long startTime, long currentTime)
	{
		msFromStart = currentTime - startTime;
	}
	
	public long getMSFromStart()
	{
		return msFromStart;
	}
	
	public String sendToString()
	{
		return Long.toString(msFromStart);
	}
}