package MiniTwitter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
public class UserView extends JFrame implements Observable
{
	private JPanel contentPane;
	private User currentUser;
	private JTextField followUserTextField;
	private JTextField tweetTextField;
	private JButton followUserButton;
	private JButton postTweetButton;
	private JLabel followingLabel;
	private JLabel newsfeedLabel;
	private ArrayList<String> followingIdList;
	private List<String> newsfeed;
	private JList<String> followingList;
	private JList<String> newsfeedList;
	private DefaultListModel<String> newsfeedListModel;
	private DefaultListModel<String> followingListModel;
	private String tweet;
	private String followUserId;	
	private JScrollPane followingScrollPane;
	private JScrollPane newsfeedScrollPane;
	private HashMap<String, User> userMap = AdminControlPanel.getUserMap();

	@SuppressWarnings("unchecked")
	public UserView(User myUser) 
	{		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		
		currentUser = myUser.getUser();
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		newsfeedListModel = new DefaultListModel<String>();

		setTitle("Username: "+ currentUser.getId());
		
		followUserTextField = new JTextField();
		followUserTextField.setBounds(10, 10, 270, 30);
		contentPane.add(followUserTextField);
		
		followUserButton = new JButton("Follow User");
		followUserButton.setBounds(290, 10, 115, 30);
		followUserButton.addActionListener(new FollowUserButtonListener());
		contentPane.add(followUserButton);
		
		followingLabel = new JLabel("Current Following:");
		followingLabel.setBounds(10, 45, 180, 15);
		contentPane.add(followingLabel);
		
		followingListModel = new DefaultListModel<String>();
		followingList = new JList<String>(followingListModel);
		followingList.setBounds(10, 65, 415, 145);
		contentPane.add(followingList);
		followingIdList = (ArrayList<String>) currentUser.getFollowingList().clone();
	
		for (int i = 0; i < followingIdList.size(); i++)
		{
			followingListModel.addElement(followingIdList.get(i));
		}

		tweetTextField = new JTextField();
		tweetTextField.setBounds(10, 230, 270, 30);
		contentPane.add(tweetTextField);
		
		postTweetButton = new JButton("Post tweet");
		postTweetButton.setBounds(290, 230, 115, 30);
		postTweetButton.addActionListener(new PostTweetButtonListener());
		contentPane.add(postTweetButton);

		newsfeedLabel = new JLabel("News Feed:");
		newsfeedLabel.setBounds(10, 265, 210, 15);
		contentPane.add(newsfeedLabel);
		newsfeedList = new JList<String>(newsfeedListModel);
		newsfeedList.setBounds(10, 290, 415, 115);
		newsfeed = (ArrayList<String>) currentUser.getNewsfeed().clone();
		contentPane.add(newsfeedList);
		
		for (int i = 0; i < newsfeed.size(); i++)
		{
			newsfeedListModel.addElement(newsfeed.get(i));
		}

		followingScrollPane = new JScrollPane(followingList);
		followingScrollPane.setBounds(10, 65, 415, 145);
		contentPane.add(followingScrollPane);

		newsfeedScrollPane = new JScrollPane(newsfeedList);
		newsfeedScrollPane.setBounds(10, 290, 415, 115);
		contentPane.add(newsfeedScrollPane);
	}
	
	public void notifyObservers(String tweet) 
	{
		for (int i = 0; i < currentUser.getFollowerList().size(); i++) 
		{
			String follower = currentUser.getFollowerList().get(i);
			userMap.get(follower).update(tweet);
		}
	}
	
	public void attach(String id) 
	{
		currentUser.addToFollowingList(id);
		userMap.get(id).addToFollowerList(currentUser.getId());
	}
	
	//Follow User Button Listener to follow a user
	private class FollowUserButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			followUserId = followUserTextField.getText();
			
			if(!userMap.containsKey(followUserId)) 
			{
				JOptionPane.showMessageDialog(null, "User does not exist");
			} 
			else if (followUserId.equals(currentUser.getId())) 
			{
				JOptionPane.showMessageDialog(null, "You can't follow yourself");
			} 
			else if(currentUser.getFollowingList().contains(followUserId)){
				JOptionPane.showMessageDialog(null, "You are already following this user");
			} 
			else 
			{
				attach(followUserId);
				JOptionPane.showMessageDialog(null, "You are now following " + followUserId);
				followingListModel.addElement(followUserId);
			}
		}
	}
	
	//Post Tweet Button Listener to post a tweet to newsfeed 
	private class PostTweetButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{				
			tweet = "-   " + currentUser.getId() + ": " + tweetTextField.getText();
			notifyObservers(tweet);
			currentUser.update(tweet);
			newsfeedListModel.addElement(tweet);;
			
			TweetTotalVisitor tweetTotal = new TweetTotalVisitor();
			tweetTotal.accept();
				
			//Check for positive words and calculate percentage
			String[] positiveWords = {"Good", "Great", "Excellent", "Amazing", "Love", "Awesome", "Beautiful"};
			PositivePercentageVisitor positiveTotal = new PositivePercentageVisitor();
			
			for (int i = 0; i < positiveWords.length; i++) 
			{
				if (tweet.contains(positiveWords[i])) 
				{
					positiveTotal.accept();
				}
			}
		};
	}	
}