package client;

import java.util.TreeMap;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

/**
 * Stores a user's data locally once that user has logged on
 * 
 * On Login, create one of these objects
 * 
 * @author ed
 *
 */

public class User {
	private TreeMap<String, ChatSession> chatSessions; //Tracks the chats of which user is a member.
	private Client client;
	private String userName;
	
	public User(Client client) {
		this.chatSessions = new TreeMap<>();
		this.client = client;
	}
	
	public TreeMap<String, ChatSession> getChatSessions() {
		return chatSessions;
	}

	public void setChatSessions(TreeMap<String, ChatSession> chatSessions) {
		this.chatSessions = chatSessions;
	}
	
	public void addChatSession(ChatSession chatSession) {
		chatSessions.put(chatSession.getName(), chatSession);
	}
	
	public void requestChatSessions() {
		client.sendMessage(new MessageBox(Action.GET_CHAT_SESSIONS));
	}

	public void requestSessionMembers(ChatSession chatSession) {
		MessageBox mb = new MessageBox(Action.GET_MEMBERS);
		mb.add(Data.CHAT_NAME, chatSession.getName());
		client.sendMessage(mb);
	}
	
	public void requestLoggedInMembers(ChatSession chatSession) {
		MessageBox mb = new MessageBox(Action.GET_LOGGED_IN);
		mb.add(Data.CHAT_NAME, chatSession.getName());
		client.sendMessage(mb);
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

}
