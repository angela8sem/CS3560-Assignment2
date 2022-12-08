package MiniTwitter;

public class PositivePercentageVisitor implements Visitable 
{
	private static int posPercTotal;
	
	@Override
	public void accept() 
	{
		posPercTotal++;
	}
	
	public int get() 
	{
		return posPercTotal;
	}

}
