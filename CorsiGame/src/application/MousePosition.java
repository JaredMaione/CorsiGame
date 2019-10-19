package application;

public class MousePosition 
{
	private double x;
	private double y;
	
	public MousePosition(double mouseX, double mouseY)
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
