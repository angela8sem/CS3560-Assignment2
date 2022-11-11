package MiniTwitter;

public class Group implements Component 
{
	private String groupId = "";

	public Group(String grp) 
	{
		groupId = grp;
	}
	
	@Override
	public String getId() 
	{
		return groupId;
	}
}
