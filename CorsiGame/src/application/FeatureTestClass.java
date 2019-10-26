package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

public class FeatureTestClass implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;
	private int x;
	private int y;
	
	public FeatureTestClass()
	{
		try {
			FileOutputStream os = new FileOutputStream("test.ser");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
