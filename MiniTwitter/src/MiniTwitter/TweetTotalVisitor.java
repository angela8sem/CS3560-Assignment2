package MiniTwitter;

public class TweetTotalVisitor implements Visitable 
{
	private static int tweetTotal;
	
	@Override
	public void accept() 
	{
		tweetTotal++;
	}
	
	public int get() 
	{
		return tweetTotal;
	}

}
