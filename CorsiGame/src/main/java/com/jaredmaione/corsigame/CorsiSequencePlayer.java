package com.jaredmaione.corsigame;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;

// This class will play a CorsiSequence based on several parameters
public class CorsiSequencePlayer 
{
	private Stopwatch stopwatch;
	private Stopwatch sequenceTimer;
	
	private int blockIndex;
	
	public CorsiSequencePlayer(Stopwatch sequenceTimer)
	{
		stopwatch = new Stopwatch();
		blockIndex = 0;
		this.sequenceTimer = sequenceTimer;
	}
	
	// This method plays a sequence
	// It returns a very accurate estimate of the amount of time it will
	// take the sequence to play from start to finish
	public double playSequence(ArrayList<CorsiBlock> blocks, int level, double secBetweenBlinks, double blinkSeconds, boolean startSequenceTimer, double secToDelay)
	{	
		blockIndex = 0;
		
		if (blocks.size() > 0)
		{
			// Lock out all blocks to prevent user from clicking them early
			for (CorsiBlock block : blocks)
			{
				block.setClickable(false);
			}
			
			AnimationTimer sequencePlayTimer = new AnimationTimer()
			{
				@Override
				public void handle(long arg0) 
				{
					// Condition: the total time from the beginning of the blink to the delay for the next blink has passed
					if (stopwatch.getMSFromStart() > (blinkSeconds + secBetweenBlinks) * 1000)
					{
						stopwatch.reset();
						
						// Unlock all blocks after the sequence is over and begin sequence timer (if applicable
						if (blockIndex == level - 1 || blockIndex == blocks.size())
						{
							this.stop();
							
							// Allow blocks to be clicked again
							for (CorsiBlock block :blocks)
							{
								block.setClickable(true);
							}
							
							if (startSequenceTimer)
							{
								sequenceTimer.start();
							}
							
							return;
						}
						
						// Handle to next block in sequence
						++blockIndex;
						blocks.get(blockIndex).blink(blinkSeconds);
						stopwatch.start();
					}
				}
			};
			
			// This timer delays the start of the sequence to allow for a message to be displayed
			// by another class
			AnimationTimer delayTimer = new AnimationTimer()
			{
				@Override
				public void handle(long now) 
				{
					if (stopwatch.getMSFromStart() > secToDelay * 1000)
					{
						stopwatch.reset();
						
						blocks.get(blockIndex).blink(blinkSeconds);

						stopwatch.start();
						
						sequencePlayTimer.start();
						this.stop();
					}
				}
			};
			
			stopwatch.start();
			delayTimer.start();
			
			return estimateSequenceTime(level, secBetweenBlinks, blinkSeconds, secToDelay);
		}
		
		return 0;
	}
	
	// Plays a sequence based off of an existing CorsiSequenceData object
	public double playSequence(CorsiSequenceData data)
	{
		return playSequence(data.getBlocks(), data.getLevel(), data.getSecBetweenBlinks(), data.getBlinkSeconds(), data.isStartSequenceTimer(), data.getSecToDelay());
	}
	
	// This method estimates the amount of time it will take to play a sequence from beginning to end 
	private double estimateSequenceTime(int level, double secBetweenBlinks, double blinkSeconds)
	{
		return (blinkSeconds + secBetweenBlinks) * level;
	}
	
	// This method estimates the amount of time it will take to play a sequence, including any delay before the sequence
	// begins playing
	private double estimateSequenceTime(int level, double secBetweenBlinks, double blinkSeconds, double secToDelay)
	{
		return estimateSequenceTime(level, secBetweenBlinks, blinkSeconds) + secToDelay;
	}
}