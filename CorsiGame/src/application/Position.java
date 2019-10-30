package application;

import java.io.Serializable;

public class Position implements Serializable
{
	private static final long serialVersionUID = -4072427736021987251L;
	
	private double x;
	private double y;
	
	public Position(double mouseX, double mouseY)
	{
		x = mouseX;
		y = mouseY;
	}
	
	public void set(double mouseX, double mouseY)
	{
		x = mouseX;
		y = mouseY;
	}
	
	public void setX(double mouseX)
	{
		x = mouseX;
	}
	
	public void setY(double mouseY)
	{
		y = mouseY;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
}
