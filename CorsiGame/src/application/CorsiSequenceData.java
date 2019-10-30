package application;

import java.io.Serializable;
import java.util.ArrayList;

public class CorsiSequenceData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2483897520589617244L;
	
	private ArrayList<CorsiBlock> blocks;
	private int level;
	private int secBetweenBlinks;
	private int blinkSeconds;
	private boolean startSequenceTimer;
	private double secToDelay;
	
	public CorsiSequenceData(ArrayList<CorsiBlock> blocks, int level, int secBetweenBlinks, int blinkSeconds, boolean startSequenceTimer, double secToDelay)
	{
		this.blocks = blocks;
		this.level = level;
		this.secBetweenBlinks = secBetweenBlinks;
		this.blinkSeconds = blinkSeconds;
		this.startSequenceTimer = startSequenceTimer;
		this.secToDelay = secToDelay;
	}

	public ArrayList<CorsiBlock> getBlocks() 
	{
		return blocks;
	}

	public void setBlocks(ArrayList<CorsiBlock> blocks) 
	{
		this.blocks = blocks;
	}

	public int getLevel() 
	{
		return level;
	}

	public void setLevel(int level) 
	{
		this.level = level;
	}

	public int getSecBetweenBlinks() 
	{
		return secBetweenBlinks;
	}

	public void setSecBetweenBlinks(int secBetweenBlinks) 
	{
		this.secBetweenBlinks = secBetweenBlinks;
	}

	public int getBlinkSeconds() 
	{
		return blinkSeconds;
	}

	public void setBlinkSeconds(int blinkSeconds) 
	{
		this.blinkSeconds = blinkSeconds;
	}

	public boolean isStartSequenceTimer() 
	{
		return startSequenceTimer;
	}

	public void setStartSequenceTimer(boolean startSequenceTimer) 
	{
		this.startSequenceTimer = startSequenceTimer;
	}

	public double getSecToDelay() 
	{
		return secToDelay;
	}

	public void setSecToDelay(double secToDelay) 
	{
		this.secToDelay = secToDelay;
	}
}
