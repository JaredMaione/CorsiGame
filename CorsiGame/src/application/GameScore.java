package application;

import java.util.ArrayList;

public class GameScore 
{
	private int corsiSpan;
	private ElapsedTime avgSequenceTime;
	private ArrayList<ElapsedTime> sequenceTimes;
	private ElapsedTime gameDuration;
	
	public GameScore()
	{
		corsiSpan = 0;
	}
	
	public void addToAvgSequenceTime(ElapsedTime time)
	{
		
	}
	
	private ElapsedTime calculateAverage()
	{
		if (sequenceTimes.size() == 0)
		{
			return new ElapsedTime(0);
		}
	}
	
	public int getCorsiSpan() 
	{
		return corsiSpan;
	}
	
	public void setCorsiSpan(int span) 
	{
		corsiSpan = span;
	}
	
	public ElapsedTime getGameDuration()
	{
		return gameDuration;
	}
	
	public void setGameDuration(ElapsedTime duration)
	{
		gameDuration = duration;
	}
}
