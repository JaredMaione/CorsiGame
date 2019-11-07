package application;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time implements Serializable
{
	private static final long serialVersionUID = 6858418072454255473L;
	
	private int hr;
	private int min;
	private int sec;
	
	public Time()
	{
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
		
		hr = Integer.parseInt(dateString.substring(0, dateString.indexOf(":")));
		dateString = dateString.substring(dateString.indexOf(":") + 1);
		
		min = Integer.parseInt(dateString.substring(0, dateString.indexOf(":")));
		dateString = dateString.substring(dateString.indexOf(":") + 1);
		
		sec = Integer.parseInt(dateString.substring(0, 2));
	}
	
	public Time(int hr, int min, int sec)
	{
		this.hr = hr;
		this.min = min;
		this.sec = sec;
	}

	public int getHr() 
	{
		return hr;
	}

	public void setHr(int hr) 
	{
		this.hr = hr;
	}

	public int getMin() 
	{
		return min;
	}

	public void setMin(int min) 
	{
		this.min = min;
	}

	public int getSec() 
	{
		return sec;
	}

	public void setSec(int sec) 
	{
		this.sec = sec;
	}
}
