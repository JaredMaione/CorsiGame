package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;

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
	
	public int playSequence(ArrayList<CorsiBlock> blocks, int level, int secBetweenBlinks, int blinkSeconds, boolean startSequenceTimer, Text message)
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
			
			AnimationTimer sequencePlayTimer = new AnimationTimer()
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
							
							if (startSequenceTimer)
							{
								sequenceTimer.start();
							}
							
							return;
						}
						
						++blockIndex;
						blocks.get(blockIndex).blink(blinkSeconds);
						stopwatch.start();
					}
				}
			};
			
			sequencePlayTimer.start();
			
			return estimateSequenceTime(blocks.size(), secBetweenBlinks, blinkSeconds);
		}
		
		return 0;
	}
	
	private int estimateSequenceTime(int numBlocks, int secBetweenBlinks, int blinkSeconds)
	{
		return new Integer((blinkSeconds + secBetweenBlinks) * numBlocks);
	}
}