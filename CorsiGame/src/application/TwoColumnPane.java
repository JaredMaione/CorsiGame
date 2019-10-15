package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TwoColumnPane extends GridPane 
{
	private final int[] paddingData = {5, 5, 5, 15};
	private ArrayList<Node> nodes;
	private final int NODES_PER_ROW = 2;

	public TwoColumnPane()
	{
		super();
		
		this.setHgap(10);
		this.setVgap(10);

		this.setPadding(new Insets(paddingData[0], paddingData[1], paddingData[2], paddingData[3]));
		
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node node)
	{
		nodes.add(node);
		rebuildPane();
	}
	
	private void rebuildPane()
	{
		this.getChildren().clear();
		for (int c = 0, r= 0, i = 0; i < nodes.size(); ++i)
		{
			this.add(nodes.get(i), c, r);

			// Increment row and column indexes
			if (c < NODES_PER_ROW - 1)
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
