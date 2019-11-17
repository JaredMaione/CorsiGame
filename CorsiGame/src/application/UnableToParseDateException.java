package application;

// This exception is thrown if an argument String is unable to be parsed in a Date constructor
public class UnableToParseDateException extends Exception
{
	private static final long serialVersionUID = 4427133837310484723L;

	public UnableToParseDateException(String s)
	{
		super(s);
	}
}
