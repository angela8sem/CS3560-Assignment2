package MiniTwitter;

public class GroupTotalVisitor implements Visitable 
{
	private static int groupTotal;
	
	@Override
	public void accept() 
	{
		groupTotal++;
	}
	
	public int get() 
	{
		return groupTotal;
	}

}
