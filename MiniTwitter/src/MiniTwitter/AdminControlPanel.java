package MiniTwitter;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame implements Visitor
{
	private static AdminControlPanel instance;
	private JPanel panel;
	private String userID;
	private String groupID;
	private JTextField userText;
	private JTextField groupText;
	private JButton addUserBtn;		
	private JButton addGroupBtn;		
	private JButton userViewBtn;		
	private JButton userTotalBtn;		
	private JButton groupTotalBtn;		
	private JButton tweetTotalBtn;		
	private JButton postivePrecentageBtn;
	private JTree tree;
	private TreeModel treeModel;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode currentNode;	
	private JScrollPane treeScrollPane;
	private static HashMap<String, User> userMap = new HashMap<String, User>();
	private static HashMap<String, Group> groupMap= new HashMap<String, Group>();
	
	//Singleton pattern
	public static AdminControlPanel getInstance()
	{
		if (instance == null)
			instance = new AdminControlPanel();
		return instance;
	}

	private AdminControlPanel() 
	{	
		root = new DefaultMutableTreeNode("Root");
		initComponents();		
	}
	
	public void initComponents() 
	{
		setBounds(100, 100, 600, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		
		setTitle("Admin");
		
		userText = new JTextField(20);
		userText.setBounds(185, 10, 200, 30);
		panel.add(userText);
		
		groupText = new JTextField(20);
		groupText.setBounds(185, 45, 200, 30);
		panel.add(groupText);
		
		addUserBtn = new JButton("Add User");
		addUserBtn.setBounds(400, 10, 160, 30);
		addUserBtn.addActionListener(new AddUserBtnListener());
		panel.add(addUserBtn);
		
		addGroupBtn = new JButton("Add Group");
		addGroupBtn.setBounds(400, 45, 160, 30);		
		addGroupBtn.addActionListener(new AddGroupBtnListener());
		panel.add(addGroupBtn);
		
		userViewBtn = new JButton("Open User View");
		userViewBtn.setBounds(180, 85, 380, 30);
		userViewBtn.addActionListener(new userViewBtnListener());
		panel.add(userViewBtn);
		
		userTotalBtn = new JButton("Show User Total");
		userTotalBtn.setBounds(185, 250, 190, 30);
		userTotalBtn.addActionListener(new userTotalBtnListener());
		panel.add(userTotalBtn);
		
		tweetTotalBtn = new JButton("Show Messages Total");
		tweetTotalBtn.setBounds(185, 280, 190, 30);
		tweetTotalBtn.addActionListener(new messageTotalBtnListener());
		panel.add(tweetTotalBtn);
		
		groupTotalBtn = new JButton("Show Group Total");
		groupTotalBtn.setBounds(380, 250, 190, 30);
		groupTotalBtn.addActionListener(new groupTotalBtnListener());
		panel.add(groupTotalBtn);
		
        postivePrecentageBtn = new JButton("Show Positive Percentage");
        postivePrecentageBtn.setBounds(380, 280, 190, 30);
        postivePrecentageBtn.addActionListener(new postivePrecentageBtnListener());
        panel.add(postivePrecentageBtn);

        tree = new JTree(root);
        tree.setBounds(10, 5, 150, 295);
        panel.add(tree);
        treeModel = tree.getModel();
        treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setBounds(10, 6, 150, 295);
        panel.add(treeScrollPane);
	}
	
	public void addUser(String userId, DefaultMutableTreeNode node) 
	{
		User newUser = new User(userId);	
		userMap.put(userId, newUser);
		UserTotalVisitor userTotVis = new UserTotalVisitor();
		visit(userTotVis);
		node.add(new DefaultMutableTreeNode(userId));
		JOptionPane.showMessageDialog(null, "User: " + userId + " has been added to " + node);
		((DefaultTreeModel) treeModel).reload();
	}
	public void addGroup(String id, DefaultMutableTreeNode node)
	{
		Group g = new Group(id);
		groupMap.put(id, g);
		GroupTotalVisitor gtv = new GroupTotalVisitor();
		visit(gtv);
		node.add(new DefaultMutableTreeNode(id));		
		JOptionPane.showMessageDialog(null, "Group: " + id + " has been added to " + node);
		((DefaultTreeModel) treeModel).reload();
	}
	
	//Add User Button. Check if user is valid or not
	private class AddUserBtnListener implements ActionListener
	{
			public void actionPerformed(ActionEvent e)
			{	
				userID = userText.getText();
				currentNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				//username can't be empty
				if (userID.isEmpty() == true) 
				{
					JOptionPane.showMessageDialog(null, "Please enter a username");
				} 
				//username can't be taken
				else if (userMap.containsKey(userID)) 
				{
					JOptionPane.showMessageDialog(null, "User already exists, please enter a new user");
				} 
				//users can only be in groups or root
				else if (userMap.containsKey(currentNode.toString())) 
				{
					JOptionPane.showMessageDialog(null, "You can only create users in the Root or in groups");
				} 
				//if no node is selected, user is added to the Root
				else if (currentNode == null) 
				{
					addUser(userID, root);
				} 
				//add user in selected node
				else 
				{
					addUser(userID, currentNode);
				}
			}
	}

	//Add Group Button Listener. Check if group is valid
	private class AddGroupBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			groupID = groupText.getText();
			currentNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			//group name can't be empty
			if (groupID.isEmpty() == true)
			{
				JOptionPane.showMessageDialog(null, "Please enter a group name");
			} 
			//group name can't be taken
			else if (groupMap.containsKey(groupID))
			{
				JOptionPane.showMessageDialog(null, "Group name already exists, please enter a new group");
			} 
			//group can't be within a user
			else if (userMap.containsKey(currentNode.toString()))
			{
				JOptionPane.showMessageDialog(null, "You can only create groups in the Root or in other groups");
			} 
			//if no node is selected, group is added to the Root
			else if (currentNode == null)
			{
				addGroup(groupID, root);
			} 
			//add group in selected node
			else
			{
				addGroup(groupID, currentNode);
			}
		}
	}
	
	//User View Button to open up the user view
	private class userViewBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			currentNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (currentNode == null || groupMap.containsKey(currentNode.toString()))
            {
            	JOptionPane.showMessageDialog(null, "Please select a user to view");
            } 
            else 
            {
            	User u = userMap.get(currentNode.toString());
    			UserView userView = new UserView(u);
    			userView.setVisible(true);
            }
		}
	}
	
	//Show User Total Button shows total number of users
	private class userTotalBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			UserTotalVisitor userTotal = new UserTotalVisitor();
			JOptionPane.showMessageDialog(null, "Total number of users: " + userTotal.get());
		}
	}
	
	//Show Group Total Button shows total number of groups
	private class groupTotalBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{		
			GroupTotalVisitor groupTotal = new GroupTotalVisitor();
			JOptionPane.showMessageDialog(null, "Total number of groups: " + groupTotal.get());
		}
	}
	
	//Show Messages Total Button shows total number of messages
	private class messageTotalBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{		
			TweetTotalVisitor tweetTotal = new TweetTotalVisitor();
			JOptionPane.showMessageDialog(null, "Total number of messages: " + tweetTotal.get());
		}
	}
	
	//Show Positive Percentage Total Button shows total percentage of positive messages
	private class postivePrecentageBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			TweetTotalVisitor tweetTotal = new TweetTotalVisitor();
			double total = tweetTotal.get();
			
			PositivePercentageVisitor positiveTotal = new PositivePercentageVisitor();
			double posTotal = positiveTotal.get();
			
			double posPerc = (posTotal/total) * 100;
			JOptionPane.showMessageDialog(null, "Percentage of positive tweets: " + posPerc + "%");
		}
	}
	
	public static HashMap<String, User> getUserMap () 
	{
		return userMap;
	}
	//Implementing the Visitor Interface
	public void visit (UserTotalVisitor userTotVis) 
	{
		userTotVis.accept();
	}
	public void visit (GroupTotalVisitor grpTotVis) 
	{
		grpTotVis.accept();
	}
	public void visit (TweetTotalVisitor tweetTotVis) 
	{
		
	}
	public void visit (PositivePercentageVisitor posPercVis) 
	{
		
	}
}
