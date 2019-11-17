package application;

// This class keeps track of when a sequence was started
// It also stores the sequence itself
public class SequenceInitiationAction extends TimestampedAction
{
	private static final long serialVersionUID = 5317944950267416564L;
	
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