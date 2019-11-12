package application;

import java.util.ArrayList;

// This class is responsible for generating CorsiBlocks to be used in the game
// It generates blocks with random positions based on given max x and y values
public abstract class CorsiBlockGenerator 
{
	public static final int BLOCK_SIDE_LENGTH = 20;
	
	public static ArrayList<CorsiBlock> generateBlocks(int numBlocks, int maxX, int maxY)
	{
		ArrayList<CorsiBlock> blocks = new ArrayList<CorsiBlock>();
		
		for (int i = 0; i < numBlocks; ++i) 
		{
			double x = 0.0;
			double y = 0.0;
			
			// Verify that block is spawning in an open space (must be a certain distance away from all others)
			boolean validLocation = false;
			while (!validLocation)
			{
				x = Math.random() * (maxX - BLOCK_SIDE_LENGTH);
				y = Math.random() * (maxY - BLOCK_SIDE_LENGTH);
				
				validLocation = true;
				
				// Check generated position with other blocks
				for (CorsiBlock block : blocks)
				{
					double blockX = block.getX();
					double blockY = block.getY();
					
					validLocation = validLocation && 
									(blockX - x >  BLOCK_SIDE_LENGTH * 1.5 || (x - blockX > BLOCK_SIDE_LENGTH * 2.5)) &&
									(blockY - y > BLOCK_SIDE_LENGTH * 1.5 || (y - blockY > BLOCK_SIDE_LENGTH * 2.5));	
				}
			}
			
			blocks.add(new CorsiBlock(x, y, BLOCK_SIDE_LENGTH));
		}
		
		return blocks;
	}
}
