package MiniTwitter;

//Observer Pattern
//Implemented by User and UserView
public interface Observable 
{
	public void attach(String id);
    public void notifyObservers(String tweet);
}
