package application;

import java.util.ArrayList;

public class GameData 
{
	private int corsiSpan;
	private ElapsedTime avgSequenceTime;
	private ArrayList<ElapsedTime> sequenceTimes;
	private ElapsedTime gameDuration;
	private ArrayList<MousePosition> mousePositions;
	
	public GameData()
	{
		corsiSpan = 0;
		sequenceTimes = new ArrayList<ElapsedTime>();
		mousePositions = new ArrayList<MousePosition>();
		gameDuration = new ElapsedTime(0);
		avgSequenceTime = new ElapsedTime(0);
	}
	
	public void addMousePosition(MousePosition pos)
	{
		mousePositions.add(pos);
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
	
	public ArrayList<MousePosition> getMousePositions()
	{
		return mousePositions;
	}
}
