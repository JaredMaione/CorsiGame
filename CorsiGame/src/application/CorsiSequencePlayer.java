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
	 * @param secBetweenBlinks Number of seconds between blinks
	 * @param blinkSeconds Number of seconds for which the block will be lit during blink
	 */
	public void playSequence(ArrayList<CorsiBlock> blocks, int secBetweenBlinks, int blinkSeconds)
	{	
		if (blocks.size() > 0)
		{
			blocks.get(blockIndex).blink(blinkSeconds);

			stopwatch.start();
			
			AnimationTimer sequenceTimer = new AnimationTimer()
			{
				@Override
				public void handle(long arg0) 
				{
					if (stopwatch.getMSFromStart() > (blinkSeconds + secBetweenBlinks) * 1000)
					{
						stopwatch.reset();
						
						if (blockIndex == blocks.size() - 1)
						{
							this.stop();
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