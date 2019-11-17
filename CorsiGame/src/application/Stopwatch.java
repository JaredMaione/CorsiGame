package application;

import java.io.Serializable;

// This class is a stopwatch which can be started, stopped, and reset
// just like a real life stopwatch
// Once the watch is stopped, it uses an ElapsedTime object to track the time which has gone by
public class Stopwatch implements Serializable
{
	private static final long serialVersionUID = -6833815408946316276L;

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
		
		if (elapsedTime == null)
		{
			elapsedTime = new ElapsedTime(System.currentTimeMillis() - startTime);
		}
		else
		{
			elapsedTime.addTime(new ElapsedTime(System.currentTimeMillis() - startTime));
		}
		
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
	
	public boolean isRunning()
	{
		return isRunning;
	}
}