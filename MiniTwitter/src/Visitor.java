package MiniTwitter;

//Visitor Pattern
public interface Visitor 
{
	public void visit (UserTotalVisitor userTotVis);
	public void visit (GroupTotalVisitor grpTotVis);
	public void visit (TweetTotalVisitor tweetTotVis);
	public void visit (PositivePercentageVisitor posPercVis);
	
}
