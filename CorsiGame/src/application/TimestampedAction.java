package application;

public abstract class TimestampedAction 
{
	public static final String FILE_IDENTIFIER = "TIMESTAMPED_ACTION";
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
		return FILE_IDENTIFIER + "\n" + Long.toString(msFromStart);
	}
}