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
	private TreeMap<String, ClientChatSession> chatSessions; //Tracks the chats of which user is a member.
	private Client client;
	private String userName;
	
	public User(Client client) {
		this.chatSessions = new TreeMap<>();
		this.client = client;
	}
	
	public TreeMap<String, ClientChatSession> getChatSessions() {
		return chatSessions;
	}

	public void setChatSessions(TreeMap<String, ClientChatSession> chatSessions) {
		this.chatSessions = chatSessions;
	}
	
	public void addChatSession(ClientChatSession chatSession) {
		chatSessions.put(chatSession.getName(), chatSession);
	}
	
	public void requestChatSessions() {
		ClientSender cs = new ClientSender(client);
		cs.sendMessage(new MessageBox(Action.GET_CHAT_SESSIONS));
	}

	public void requestSessionMembers(ClientChatSession chatSession) {
		ClientSender cs = new ClientSender(client);
		MessageBox mb = new MessageBox(Action.GET_MEMBERS);
		mb.add(Data.CHAT_NAME, chatSession.getName());
		cs.sendMessage(mb);
	}
	
	public void requestLoggedInMembers(ClientChatSession chatSession) {
		ClientSender cs = new ClientSender(client);
		MessageBox mb = new MessageBox(Action.GET_LOGGED_IN);
		mb.add(Data.CHAT_NAME, chatSession.getName());
		cs.sendMessage(mb);
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

}
