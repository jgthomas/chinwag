package client;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Clientside object representing an individual chat the user in. 
 * 
 * Stores information on the other
 * members of this session, as well as which of those members are online.
 * 
 * Stores chat history in the form of a list of ChatMessage objects (Time, Sender, MessageBody)
 * 
 * ChatListView in GUI stores the sessions you are in.
 * 
 * 
 * @author ed
 *
 */

public class ClientChatSession {
	
	private String name;
	private TreeSet<String> sessionMembers; //TreeSet so they can be ordered
	private TreeSet<String> onlineUsers; //Names of the users that are online
	private ArrayList<ChatMessage> chatHistory;
	
	public ClientChatSession(String name) {
		this.name = name;
		sessionMembers = new TreeSet<>();
		onlineUsers = new TreeSet<>();
		chatHistory = new ArrayList<>();
	}
	
	
	public String getName() {
		return name;
	}
	public TreeSet<String> getSessionMembers() {
		return sessionMembers;
	}
	public void setSessionMembers(TreeSet<String> sessionMembers) {
		this.sessionMembers = sessionMembers;
	}
	public TreeSet<String> getOnlineUsers() {
		return onlineUsers;
	}
	public void setOnlineUsers(TreeSet<String> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}
	public ArrayList<ChatMessage> getChatHistory() {
		return chatHistory;
	}
	public void setChatHistory(ArrayList<ChatMessage> chatHistory) {
		this.chatHistory = chatHistory;
	}
	
	
}
