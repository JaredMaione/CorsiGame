package application;

import java.util.ArrayList;

public abstract class CorsiBlockGenerator 
{
	private static final int BLOCK_SIDE_LENGTH = 10;
	
	public static ArrayList<CorsiBlock> generateBlocks(int numBlocks, int maxX, int maxY)
	{
		ArrayList<CorsiBlock> blocks = new ArrayList<CorsiBlock>();
		
		for (int i = 0; i < numBlocks; ++i) 
		{
			double x = 0.0;
			double y = 0.0;
			
			// Verify that block is spawning in an open space (must be 1 or more blocks away from all others)
			boolean validLocation = false;
			while (!validLocation)
			{
				x = Math.random() * (maxX - BLOCK_SIDE_LENGTH);
				y = Math.random() * (maxY - BLOCK_SIDE_LENGTH);
				
				validLocation = true;
				
				for (CorsiBlock block : blocks)
				{
					if (block.getX() > x)
					{
						validLocation = validLocation && (block.getX() - x > BLOCK_SIDE_LENGTH);
					}
					else
					{
						validLocation = validLocation && (x - block.getX() > (BLOCK_SIDE_LENGTH * 2));
					}
					
					if (block.getY() > y)
					{
						validLocation = validLocation && (block.getY() - x > BLOCK_SIDE_LENGTH);
					}
					else
					{
						validLocation = validLocation && (y - block.getY() > (BLOCK_SIDE_LENGTH * 2));
					}
				}
			}
			
			blocks.add(new CorsiBlock(x, y, BLOCK_SIDE_LENGTH));
		}
		
		return blocks;
	}
}
