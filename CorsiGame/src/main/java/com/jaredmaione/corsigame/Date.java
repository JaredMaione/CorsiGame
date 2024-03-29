package com.jaredmaione.corsigame;

import java.io.Serializable;
import java.time.LocalDate;

// This class stores a date's month, day, and year
// Can be constructed from a String, LocalDate, or integer values for month, day, and year
public class Date implements Serializable
{
	private static final long serialVersionUID = 7576121268789295899L;

	private int month;
	private int day;
	private int year;
	
	private final String EXCEPTION_MESSAGE = "Unable to parse date from string!";
	
	public Date(int month, int day, int year)
	{
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	public Date(String dateString) throws UnableToParseDateException
	{
		if (dateString.indexOf("/") == -1 || dateString == null)
		{
			throw new UnableToParseDateException(EXCEPTION_MESSAGE);
		}
		else
		{
			try
			{
				month = Integer.parseInt(dateString.substring(0, dateString.indexOf("/")));
				
				dateString = dateString.substring(dateString.indexOf("/") + 1, dateString.length());
				day = Integer.parseInt(dateString.substring(0, dateString.indexOf("/")));
				
				dateString = dateString.substring(dateString.indexOf("/") + 1, dateString.length());
				year = Integer.parseInt(dateString);
			}
			catch(NumberFormatException ex)
			{
				throw new UnableToParseDateException(EXCEPTION_MESSAGE);
			}
		}
	}
	
	public Date(LocalDate date)
	{
		month = date.getMonthValue();
		day = date.getDayOfMonth();
		year = date.getYear();
	}
	
	public String toFormattedString()
	{
		return String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;
	}

	public int getMonth() 
	{
		return month;
	}

	public void setMonth(int month) 
	{
		this.month = month;
	}

	public int getDay() 
	{
		return day;
	}

	public void setDay(int day) 
	{
		this.day = day;
	}

	public int getYear() 
	{
		return year;
	}

	public void setYear(int year) 
	{
		this.year = year;
	}
}