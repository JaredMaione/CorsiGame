package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;

public class CorsiSequencePlayer 
{
	private Stopwatch stopwatch;
	private int blockIndex;
	
	public CorsiSequencePlayer()
	{
		stopwatch = new Stopwatch();
		blockIndex = 0;
	}
	
	/**
	 * 
	 * @param blocks Sequence of CorsiBlocks
	 * @param level Current level which dictates how many blocks will make up the sequence (starting index 1)
	 * @param secBetweenBlinks Number of seconds between blinks
	 * @param blinkSeconds Number of seconds for which the block will be lit during blink
	 */
	public void playSequence(ArrayList<CorsiBlock> blocks, int level, int secBetweenBlinks, int blinkSeconds)
	{	
		blockIndex = 0;
		
		if (blocks.size() > 0)
		{
			// Lock out all blocks
			for (CorsiBlock block : blocks)
			{
				block.setClickable(false);
			}
			
			blocks.get(blockIndex).blink(blinkSeconds);

			stopwatch.start();
			
			AnimationTimer sequenceTimer = new AnimationTimer()
			{
				@Override
				public void handle(long arg0) 
				{
					// Condition: the total time from the beginning of the blink to the delay for the next blink has passed
					if (stopwatch.getMSFromStart() > (blinkSeconds + secBetweenBlinks) * 1000)
					{
						stopwatch.reset();
						
						if (blockIndex == level - 1 || blockIndex == blocks.size())
						{
							this.stop();
							
							// Allow blocks to be clicked again
							for (CorsiBlock block :blocks)
							{
								block.setClickable(true);
							}
							
							return;
						}
						
						++blockIndex;
						blocks.get(blockIndex).blink(blinkSeconds);
						stopwatch.start();
					}
				}
			};
			
			sequenceTimer.start();
		}
	}
}