package com.jaredmaione.corsigame;

import java.io.Serializable;
import java.util.ArrayList;

// This class stores all information about a sequence for use later
// It can be used to reconstruct a generated sequence
public class CorsiSequenceData implements Serializable
{
	private static final long serialVersionUID = -2483897520589617244L;
	
	private ArrayList<CorsiBlock> blocks;
	private int level;
	private double secBetweenBlinks;
	private double blinkSeconds;
	
	// Whether or not this sequence will start the
	// argument sequence timer once it is finished
	private boolean startSequenceTimer;
	
	private double secToDelay;
	
	public CorsiSequenceData(ArrayList<CorsiBlock> blocks, int level, double secBetweenBlinks, double blinkSeconds, boolean startSequenceTimer, double secToDelay)
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

	public double getSecBetweenBlinks() 
	{
		return secBetweenBlinks;
	}

	public void setSecBetweenBlinks(int secBetweenBlinks) 
	{
		this.secBetweenBlinks = secBetweenBlinks;
	}

	public double getBlinkSeconds() 
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