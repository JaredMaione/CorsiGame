package application;

import java.util.ArrayList;

public class SequenceInitiationAction extends TimestampedAction
{
	private CorsiSequenceData sequenceData;
	
	public SequenceInitiationAction(long msFromStart, CorsiSequenceData sequenceData)
	{
		super(msFromStart);
		this.sequenceData = sequenceData;
	}
	
	public SequenceInitiationAction(long startTime, long currentTime, CorsiSequenceData sequenceData)
	{
		super(startTime, currentTime);
		this.sequenceData = sequenceData;
	}
	
	public CorsiSequenceData getSequence()
	{
		return sequenceData;
	}
}
