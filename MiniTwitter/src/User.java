package MiniTwitter;

import java.util.ArrayList;

public class User implements Component, Observer 
{
	private String userID = "";
	private ArrayList<String> followerList;
	private ArrayList<String> followingList;
	private ArrayList<String> newsfeed;
	private long creationTime;
	private long lastUpdateTime;

	public User(String id) 
	{
		userID = id;
		followingList = new ArrayList<String>();
		followerList = new ArrayList<String>();
		newsfeed = new ArrayList<String>();
		creationTime = System.currentTimeMillis();
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public User getUser() 
	{
		return User.this;
	}

	public void addToFollowingList(String string) 
	{
		followingList.add(string);
	}
	public ArrayList<String> getFollowingList() 
	{
		return followingList;
	}

	public void addToFollowerList(String string) 
	{
		followerList.add(string);
	}
	public ArrayList<String> getFollowerList() 
	{
		return followerList;
	}

	public ArrayList<String> getNewsfeed() 
	{

		return (ArrayList<String>) newsfeed;
	}
	
	public long getCreationTime()
	{
		return creationTime;
	}

	@Override
	public String getId() 
	{
		return userID;
	}
	
	@Override
	public void update(String string) 
	{
		newsfeed.add(string);
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public long getLastTimeUpdate()
	{
		return lastUpdateTime;
	}

	
	
}
