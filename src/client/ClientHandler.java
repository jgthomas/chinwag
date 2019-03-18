package client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import database.Message;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
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
			case GIVE_FRIENDS:
				handleGiveFriends(mb, gui);
				return;
			default:
				throw new IllegalStateException("Unrecognised command: " + action);
		}
	}
	
	public void handleChat(MessageBox mb, ClientGUI gui) {
		gui.displayMessage(mb);
	}
	
	/**
	 * ServerMessage means that the server refused to create the chat room specified by the user,
	 * for example because it duplicates an existing chat room name
	 * 
	 * @param mb
	 * @param gui
	 */
	public void handleServerMessage(MessageBox mb, ClientGUI gui) {
		gui.drawChatCreationRefusal();
	}
	
	public void handleDeny(ClientGUI gui) {
		Platform.runLater(() -> gui.refuseLogin());
	}
	
	public void handleAccept(MessageBox mb, ClientGUI gui) {
		client.getUser().setUserName(mb.get(Data.USER_NAME));
		
		MessageBox requestChatSessions = new MessageBox(Action.GET_CHAT_SESSIONS);
		requestChatSessions.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestChatSessions);
		
		MessageBox requestFriends = new MessageBox(Action.GET_FRIENDS);
		requestFriends.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestFriends);
		
		MessageBox requestLoggedIn = new MessageBox(Action.GET_LOGGED_IN);
		client.sendMessage(requestLoggedIn);
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
			gui.getTreeViewRoot().getChildren().add(new TreeItem<String>(session));
			Platform.runLater(() -> gui.addSessionSpace(session));
			MessageBox requestMembers = new MessageBox(Action.GET_MEMBERS);
			requestMembers.add(Data.CHAT_NAME, session);
			client.sendMessage(requestMembers);
		}
		gui.getChatTreeView().getSelectionModel().selectFirst();
		gui.getChatTreeView().getSelectionModel().getSelectedItem().setExpanded(true);
		Platform.runLater(() -> gui.login());
		MessageBox requestChatHistory = new MessageBox(Action.GET_CHAT_HISTORY);
		requestChatHistory.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestChatHistory);
	}
	
	public void handleGiveChatHistory(MessageBox mb, ClientGUI gui) {
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
//<<<<<<< HEAD
//		//user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setSessionMembers(sessionMembers);
//		client.getLoggedInUsers().clear();
//		for(String username : mb.get(Data.LOGGED_IN_MEMBERS).split(protocol.Token.SEPARATOR.getValue())) {
//			client.getLoggedInUsers().add(username);
//		}
//=======
		String[] loggedInUsersServer = retrieveJoinedData(mb, Data.LOGGED_IN_MEMBERS);
		TreeSet<String> loggedInUsersClient = new TreeSet<>();
		for (String userName: loggedInUsersServer) {
			loggedInUsersClient.add(userName);
		}
		user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setSessionMembers(loggedInUsersClient);
	}
	
	public void handleGiveFriends(MessageBox mb, ClientGUI gui) {
		for(String friend : mb.get(Data.USER_FRIENDS).split(protocol.Token.SEPARATOR.getValue())) {
			gui.getFriendsList().add(friend);
		}
	}
	
	/**
	 * Helper method to split the Strings sent by the server into a String array for processing
	 * 
	 * @param mb - MessageBox containing the data
	 * @param dataType - type of data you want
	 * @return Data split into individual elements of array
	 */
	public String[] retrieveJoinedData(MessageBox mb, Data dataType) {
		return mb.get(dataType).split(protocol.Token.SEPARATOR.getValue());
	}
}
