package com.jaredmaione.corsigame;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

// This class keeps track of all relevant data from a single
// game played by a single user
public class GameData implements Serializable
{
	private static final long serialVersionUID = -4433645078534843484L;

	public static final String FILE_IDENTIFIER = "GAME_DATA";
	
	private int corsiSpan;
	
	// The average time to complete a sequence based on all sequences played
	// in the game
	private ElapsedTime avgSequenceTime;
	
	// The time to complete each sequence played during the game
	private ArrayList<ElapsedTime> sequenceTimes;
	
	private ElapsedTime gameDuration;
	private ArrayList<TimestampedAction> gameActions;
	private Date gameDate;
	private Time gameStartTime;
	
	public GameData()
	{
		corsiSpan = 0;
		sequenceTimes = new ArrayList<ElapsedTime>();
		gameDuration = new ElapsedTime(0);
		avgSequenceTime = new ElapsedTime(0);
		gameActions = new ArrayList<TimestampedAction>();
		gameDate = new Date(LocalDate.now());
		gameStartTime = new Time();
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
		avgSequenceTime = calculateAvgSequenceTime();
	}
	
	public ElapsedTime getAvgSequenceTime()
	{
		return avgSequenceTime;
	}
	
	private ElapsedTime calculateAvgSequenceTime()
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
	
	public Date getGameDate()
	{
		return gameDate;
	}
	
	public Time getGameStartTime()
	{
		return gameStartTime;
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