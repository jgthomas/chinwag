package client;

import java.util.TreeMap;

/**
 * Stores a user's data locally once that user has logged on
 * 
 * @author ed
 *
 */

public class User {
	private TreeMap<String, ClientChatSession> chatSessions; //Tracks the chats of which user is a member.
	
	public User() {
		this.chatSessions = new TreeMap<>();
	}
	
	public TreeMap<String, ClientChatSession> getChatSessions() {
		return chatSessions;
	}

	public void setChatSessions(TreeMap<String, ClientChatSession> chatSessions) {
		this.chatSessions = chatSessions;
	}
	
	

}
