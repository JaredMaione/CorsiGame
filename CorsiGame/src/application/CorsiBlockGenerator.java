package application;

import java.util.ArrayList;

public abstract class CorsiBlockGenerator 
{
	private final int BLOCK_SIDE_LENGTH = 100;
	
	public ArrayList<CorsiBlock> generateBlocks(int numBlocks, int maxX, int maxY)
	{
		ArrayList<CorsiBlock> blocks = new ArrayList<CorsiBlock>();
		
		for (int i = 0; i < numBlocks; ++i) 
		{
			blocks.add(new CorsiBlock((Math.random() * (maxX - BLOCK_SIDE_LENGTH)), (Math.random() * (maxY - BLOCK_SIDE_LENGTH)), BLOCK_SIDE_LENGTH));
		}
		
		return blocks;
	}
}
