package application;

import java.io.Serializable;

public class ElapsedTime implements Serializable
{
	private static final long serialVersionUID = 2164275180143319116L;

	public static final String FILE_IDENTIFIER = "ELAPSED_TIME";
	
	private int minutes;
	private int seconds;
	private int ms;
	
	public ElapsedTime(long timeInMS)
	{
		// THIS NEEDS TESTING
		seconds = (int) (timeInMS / 1000);
		ms = (int) (timeInMS % 1000);
		
		if (seconds > 59)
		{
			minutes = seconds / 60;
			seconds = seconds % 60;
		}
		else
		{
			minutes = 0;
		}
	}
	
	public String sendToString()
	{
		return minutes + ":" + seconds + ":" + ms;
	}
	
	public void addTime(ElapsedTime timeToAdd)
	{
		minutes += timeToAdd.getMinutes();
		seconds += timeToAdd.getSeconds();
		ms += timeToAdd.getMS();
	}
	
	public long toMS()
	{
		return (minutes * 60000) + (seconds * 1000) + ms;
	}
	
	public int getMinutes()
	{
		return minutes;
	}
	
	public int getSeconds()
	{
		return seconds;
	}
	
	public int getMS()
	{
		return ms;
	}
}
