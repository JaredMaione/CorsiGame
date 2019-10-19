package application;

import java.util.ArrayList;

public class PlayerData 
{
	private String username;
	private String password;
	private Date dob;
	private String city;
	private String state;
	private String country;
	private String diagnosis;
	private ArrayList<GameData> scores;
	
	public PlayerData()
	{
		username = "";
		password = "";
		dob = new Date(-1, -1, -1);
		city = "";
		state = "";
		country = "";
		diagnosis = "";
		scores = new ArrayList<GameData>();
	}
	
	public PlayerData(String username, String password, Date dob, String city, String state, String country, String diagnosis)
	{
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.city = city;
		this.state = state;
		this.country = country;
		this.diagnosis = diagnosis;
		scores = new ArrayList<GameData>();
		
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Date getDob() 
	{
		return dob;
	}

	public void setDob(Date dob) 
	{
		this.dob = dob;
	}

	public String getCity() 
	{
		return city;
	}

	public void setCity(String city) 
	{
		this.city = city;
	}

	public String getState() 
	{
		return state;
	}

	public void setState(String state) 
	{
		this.state = state;
	}

	public String getCountry() 
	{
		return country;
	}

	public void setCountry(String country) 
	{
		this.country = country;
	}

	public String getDiagnosis() 
	{
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) 
	{
		this.diagnosis = diagnosis;
	}

	public ArrayList<GameData> getScores() 
	{
		return scores;
	}

	public void setScores(ArrayList<GameData> scores) 
	{
		this.scores = scores;
	}
}
