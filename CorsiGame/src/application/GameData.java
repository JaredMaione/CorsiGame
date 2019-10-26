package application;

import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public static final String FILE_IDENTIFIER = "GAME_DATA";
	
	private int corsiSpan;
	private ElapsedTime avgSequenceTime;
	private ArrayList<ElapsedTime> sequenceTimes;
	private ElapsedTime gameDuration;
	private ArrayList<TimestampedAction> gameActions;
	
	public GameData()
	{
		corsiSpan = 0;
		sequenceTimes = new ArrayList<ElapsedTime>();
		gameDuration = new ElapsedTime(0);
		avgSequenceTime = new ElapsedTime(0);
		gameActions = new ArrayList<TimestampedAction>();
	}
	
	public String sendToString()
	{
		String returnString = Integer.toString(corsiSpan) + "\n" +
							  gameDuration.sendToString() + "\n-";
		
		for (ElapsedTime et : sequenceTimes)
		{
			returnString += "\n" + et.sendToString();
		}
		
		returnString += "\n-\n";
		
		returnString += avgSequenceTime.sendToString() + "\n-";
		

		
		return returnString;
	}
	
	public void addTimestampedAction(TimestampedAction action)
	{
		gameActions.add(action);
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
	
	public ArrayList<TimestampedAction> getGameActions()
	{
		return gameActions;
	}
}
