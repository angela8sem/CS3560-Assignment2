package MiniTwitter;

public class Group implements Component 
{
	private String groupId = "";
	private long creationTime;

	public Group(String grp) 
	{
		groupId = grp;
	}
	
	public long getCreationTime()
	{
		return creationTime;
	}
	
	@Override
	public String getId() 
	{
		return groupId;
	}
}
