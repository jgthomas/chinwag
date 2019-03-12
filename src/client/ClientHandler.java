package client;

import java.util.TreeMap;
import java.util.TreeSet;

import javafx.application.Platform;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class ClientHandler {
	private Client client;
	private ClientGUI gui;
	private User user;
	private TreeSet<String> onlineUsers;
	private TreeSet<String> sessionMembers;
	
	public ClientHandler(Client client, ClientGUI gui,
			User user) {
		this.client = client;
		this.gui = gui;
		this.user = user;
	}
	
	public void handle(MessageBox mb) {
		Action action = mb.getAction();
		switch(action) {
			case CHAT:
				handleChat(mb, gui);
				return;
			case SERVER_MESSAGE:
				handleServerMessage(mb, gui);
				return;
			case DENY:
				handleDeny(gui);
				return;
			case ACCEPT:
				handleAccept(mb, gui);
				return;
			case INVITE:
				handleInvite(mb, gui);
				return;
			case GIVE_MEMBERS:
				handleGiveMembers(mb, gui, user);
				return;
			case GIVE_LOGGED_IN:
				handleUpdateLoggedIn(mb, gui, user);
				return;
			default:
				throw new IllegalStateException("Unrecognised command: " + action);
		}
	}
	
	public void handleChat(MessageBox mb, ClientGUI gui) {
		gui.displayMessage(mb);
	}
	
	public void handleServerMessage(MessageBox mb, ClientGUI gui) {
		handleChat(mb, gui);
	}
	
	public void handleDeny(ClientGUI gui) {
		DenyUpdate du = new DenyUpdate(gui);
		Platform.runLater(du);
	}
	
	public void handleAccept(MessageBox mb, ClientGUI gui) {
		LoginUpdate lu = new LoginUpdate(gui);
		Platform.runLater(lu);
	}
	
	public void handleInvite(MessageBox mb, ClientGUI gui) {
		InviteUpdate iu = new InviteUpdate(gui, mb);
		Platform.runLater(iu);
		gui.setInviteName(mb.get(Data.CHAT_NAME));
	}
	
	public void handleGiveMembers(MessageBox mb, ClientGUI gui, User user) {
//		user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setOnlineUsers(onlineUsers);
	}
	
	public void handleUpdateLoggedIn(MessageBox mb, ClientGUI gui, User user) {
//		user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setSessionMembers(sessionMembers);
	}
}
