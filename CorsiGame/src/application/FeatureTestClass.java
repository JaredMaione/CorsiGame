package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FeatureTestClass implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;
	public int x;
	public int y;
	
	public FeatureTestClass()
	{
		x = 5;
		y = -1;
	}
	
}
