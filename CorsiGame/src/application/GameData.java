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
		sequenceTimes = new ArrayList<ElapsedTime>();
		gameDuration = new ElapsedTime(0);
		avgSequenceTime = new ElapsedTime(0);
	}
	
	public void addToAvgSequenceTime(ElapsedTime time)
	{
		sequenceTimes.add(time);
		avgSequenceTime = calculateAverage();
	}
	
	public ElapsedTime getAvgSequenceTime()
	{
		return avgSequenceTime;
	}
	
	private ElapsedTime calculateAverage()
	{
		if (sequenceTimes == null || sequenceTimes.size() == 0)
		{
			return new ElapsedTime(0);
		}
		else
		{
			long timeInMS = 0;
			
			for (ElapsedTime time : sequenceTimes)
			{
				timeInMS += time.toMS();
			}
			
			timeInMS /= sequenceTimes.size();
			
			return new ElapsedTime(timeInMS);
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
