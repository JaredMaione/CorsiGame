package com.jaredmaione.corsigame;

import java.io.Serializable;

// The base class for all actions which can be saved for game replay
public abstract class TimestampedAction implements Serializable
{
	private static final long serialVersionUID = -3686299188586827656L;
	
	public static final String FILE_IDENTIFIER = "TIMESTAMPED_ACTION";
	private long msFromStart;
	
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