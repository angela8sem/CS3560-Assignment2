package MiniTwitter;

public class UserTotalVisitor implements Visitable 
{
	private static int userTotal;
	
	@Override
	public void accept() 
	{
		userTotal++;
	}
	
	public int get() 
	{
		return userTotal;
	}

}
