package com.jaredmaione.corsigame;

import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

// This pane can be highlighted and selected, and the object
// which it refers to can be returned
// It requires application.css to be added to the parent Node
public class ItemSelectionPane<T> extends FlowPane
{
	private T obj;
	
	private boolean isSelected;
	
	// The text which will be displayed as the item's label
	private Text descText;
		
	public ItemSelectionPane(T obj, String descString)
	{
		super();
		
		this.obj = obj;
		
		isSelected = false;
		
		descText = new Text(descString);
		
		this.getChildren().add(descText);
	}
	
	public void setSelected(boolean selected)
	{
		if (selected)
		{
			descText.getStyleClass().add("itemSelected");
			this.getStyleClass().add("itemSelected");
		}
		else
		{
			descText.getStyleClass().remove("itemSelected");
			this.getStyleClass().remove("itemSelected");
		}
		
		isSelected = selected;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public T getObj()
	{
		return obj;
	}
	
	public void setObj(T incObj)
	{
		this.obj = incObj;
	}
	
	public void setDescText(String text)
	{
		descText.setText(text);
	}
}