package application;

public class Stopwatch 
{
	private boolean isRunning;
	
	// In MS
	private long startTime;
	
	private ElapsedTime elapsedTime;
	
	public Stopwatch()
	{
		startTime = -1;
		isRunning = false;
	}
	
	public void start()
	{
		startTime = System.currentTimeMillis();
		isRunning = true;
	}

	public ElapsedTime stop()
	{
		isRunning = false;
		elapsedTime = new ElapsedTime(System.currentTimeMillis() - startTime);
		return elapsedTime;
	}
	
	public ElapsedTime getLastElapsedTime()
	{
		return elapsedTime;
	}
	
	// Returns the number of ms since this stopwatch was started
	public long getMSFromStart()
	{
		if (startTime == -1)
		{
			return 0;
		}
		
		return System.currentTimeMillis() - startTime;
	}
	
	public void reset()
	{
		stop();
		startTime = -1;
		elapsedTime = null;
		isRunning = false;
	}
}
