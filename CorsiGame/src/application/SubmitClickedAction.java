package application;

// Keeps track of when the submit button was clicked after a sequence
public class SubmitClickedAction extends MouseClickAction 
{
	private static final long serialVersionUID = -5774668168222264880L;

	public SubmitClickedAction(long msFromStart, Position mousePosition, int clickCount)
	{
		super(msFromStart, mousePosition, clickCount);
	}
	
	public SubmitClickedAction(long startTime, long currentTime, Position mousePosition, int clickCount)
	{
		super(startTime, currentTime, mousePosition, clickCount);
	}
}
