package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class FixedColumnGridPane extends GridPane 
{
	private final int[] paddingData = {5, 5, 10, 15};
	private ArrayList<Node> nodes;
	private int numColumns;

	public FixedColumnGridPane()
	{
		super();
		
		this.setHgap(10);
		this.setVgap(10);

		this.setPadding(new Insets(paddingData[0], paddingData[1], paddingData[2], paddingData[3]));
		
		nodes = new ArrayList<Node>();
		numColumns = 2;
	}
	
	public FixedColumnGridPane(int numColumns)
	{
		this();
		this.numColumns = numColumns;
	}
	
	public void setColumns(int numColumns)
	{
		this.numColumns = numColumns;
		rebuildPane();
	}
	
	public void addNode(Node node)
	{
		nodes.add(node);
		rebuildPane();
	}
	
	public void addAll(Node[] nodes)
	{
		for (Node node : nodes)
		{
			addNode(node);
		}
	}
	
	public void removeAllNodes()
	{
		this.getChildren().clear();
		nodes.clear();
	}
	
	private void rebuildPane()
	{
		this.getChildren().clear();
		for (int c = 0, r= 0, i = 0; i < nodes.size(); ++i)
		{
			this.add(nodes.get(i), c, r);

			// Increment row and column indexes
			if (c < numColumns - 1)
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
