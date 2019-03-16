package client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import database.Message;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
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
			case GIVE_CHAT_SESSIONS:
				handleGiveSessions(mb);
			case GIVE_MEMBERS:
				handleGiveMembers(mb, gui, user);
				return;
			case GIVE_LOGGED_IN:
				handleUpdateLoggedIn(mb, gui, user);
				return;
			case GIVE_CHAT_HISTORY:
				handleGiveChatHistory(mb, gui);
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
		Platform.runLater(() -> gui.refuseLogin());
	}
	
	public void handleAccept(MessageBox mb, ClientGUI gui) {
		client.getUser().setUserName(mb.get(Data.USER_NAME));
		MessageBox requestChatSessions = new MessageBox(Action.GET_CHAT_SESSIONS);
		requestChatSessions.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestChatSessions);
	}
	
	public void handleInvite(MessageBox mb, ClientGUI gui) {
		Platform.runLater(() -> gui.drawInviteScreen(mb));
		gui.setInviteName(mb.get(Data.CHAT_NAME));
	}
	
	public void handleGiveSessions(MessageBox mb) {
		List<String> chatSessions;
		chatSessions = Arrays.asList(mb.get(Data.CHAT_SESSIONS)
				.split(protocol.Token.SEPARATOR.getValue()));
		for(String session : chatSessions) {
			gui.getObservableChatList().add(session);
			gui.getTreeViewRoot().getChildren().add(new TreeItem<String>(session));
			TextArea newSpace = new TextArea();
			newSpace.setEditable(false);
			gui.getMessageSpaces().put(session, newSpace);
			MessageBox requestMembers = new MessageBox(Action.GET_MEMBERS);
			requestMembers.add(Data.CHAT_NAME, session);
			client.sendMessage(requestMembers);
		}
		gui.getChatListView().getSelectionModel().selectFirst();
		Platform.runLater(() -> gui.login());
		MessageBox requestChatHistory = new MessageBox(Action.GET_CHAT_HISTORY);
		requestChatHistory.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestChatHistory);
	}
	
	public void handleGiveChatHistory(MessageBox mb, ClientGUI gui) {
//		TextArea newSpace = new TextArea();
//		newSpace.setEditable(false);
//		gui.getObservableChatList().add(mb.get(Data.CHAT_NAME));
//		gui.getTreeViewRoot().getChildren().add(new TreeItem<String>(mb.get(Data.CHAT_NAME)));
//		gui.getMessageSpaces().put(mb.get(Data.CHAT_NAME), newSpace);
		Collections.reverse(mb.getMessageHistory());
		for(Message message : mb.getMessageHistory()) {
			gui.getMessageSpaces().get(mb.get(Data.CHAT_NAME))
								  .appendText(message.getSender() + ">>> " +
										  message.getContent() + "\n");
		}
	}
	
	public void handleGiveMembers(MessageBox mb, ClientGUI gui, User user) {
		//user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setOnlineUsers(onlineUsers);
		String members = mb.get(Data.CHAT_MEMBERS);
		for (TreeItem<String> t : gui.getTreeViewRoot().getChildren()) {
			if (t.getValue().equals(mb.get(Data.CHAT_NAME))) {
				for (String member : members.split(protocol.Token.SEPARATOR.getValue())) {
					t.getChildren().add(new TreeItem<String>(member));
				}
			}
		}
	}
	
	public void handleUpdateLoggedIn(MessageBox mb, ClientGUI gui, User user) {
		//user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setSessionMembers(sessionMembers);
	}
	
}
