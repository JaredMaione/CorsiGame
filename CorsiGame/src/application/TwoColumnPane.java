package application;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TwoColumnPane extends GridPane 
{
	private final int[] paddingData = {5, 5, 5, 15};

	public TwoColumnPane()
	{
		super();
		
		this.setHgap(10);
		this.setVgap(10);

		this.setPadding(new Insets(paddingData[0], paddingData[1], paddingData[2], paddingData[3]));
	}
	
	protected void addNodes(Node[] nodes)
	{
		for (int c = 0, r= 0, i = 0; i < nodes.length; ++i)
		{
			this.add(nodes[i], c, r);

			// Increment row and column indexes
			if (c < (1))
			{
				++c;
			}
			else
			{
				c = 0;
				r += 1;
			}
		}
	}
}
