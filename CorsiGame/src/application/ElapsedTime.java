package application;

public class ElapsedTime 
{
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
			minutes = (seconds / 60);
		}
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
