package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

public class PlayerData implements Serializable
{	
	private static final long serialVersionUID = 252115055904727829L;
	
	public static final String FILE_IDENTIFIER = "PLAYER_DATA";
	private String username;
	private String password;
	private Date dob;
	private String city;
	private String state;
	private String country;
	private String diagnosis;
	private ArrayList<GameData> gameDataList;
	
	public PlayerData()
	{
		username = "";
		password = "";
		dob = new Date(-1, -1, -1);
		city = "";
		state = "";
		country = "";
		diagnosis = "";
		gameDataList = new ArrayList<GameData>();
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
		gameDataList = new ArrayList<GameData>();
	}
	
	public String sendToString()
	{
		String dataString = FILE_IDENTIFIER + "\n" + 
			   username + "\n" +
			   password + "\n" +
			   dob.toFormattedString() + "\n" + 
			   city + "\n" +
			   state + "\n" + 
			   country + "\n" +
			   diagnosis;
		
		for (GameData gameData : gameDataList)
		{
			dataString += "\n" + gameData.sendToString();
		}
		
		return dataString;
	}
	
	public void readFromString(String data)
	{
		try 
		{
			BufferedReader dataReader = new BufferedReader(new StringReader(data));
						
			username = dataReader.readLine();
			password = dataReader.readLine();
			dob = new Date(dataReader.readLine());
			city = dataReader.readLine();
			state = dataReader.readLine();
			country = dataReader.readLine();
			diagnosis = dataReader.readLine();
			
			if (username == null ||
				password == null ||
				dob == null ||
				city == null ||
				state == null ||
				country == null ||
				diagnosis == null)
			{
				System.out.println("Error");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		catch (UnableToParseDateException e) 
		{
			dob = new Date(0, 0, 0);
			e.printStackTrace();
		}
	}
	
	public int getMaxCorsiSpan()
	{
		int max = -1;
		
		for (GameData data : gameDataList)
		{
			if (data.getCorsiSpan() > max)
			{
				max = data.getCorsiSpan();
			}
		}
		
		return max;
	}
	
	public int getNumberOfGames()
	{
		return gameDataList.size();
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
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

	public ArrayList<GameData> getGameDataList() 
	{
		return gameDataList;
	}

	public void setGameDataList(ArrayList<GameData> dataList) 
	{
		this.gameDataList = dataList;
	}
	
	public void addGameData(GameData data)
	{
		gameDataList.add(data);
	}
}
