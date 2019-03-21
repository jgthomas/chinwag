package client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class Handler {
	private Client client;
	private LoginController controller;
	private User user;
	private TreeSet<String> onlineUsers;
	private TreeSet<String> sessionMembers;
	
	public Handler(Client client, LoginController controller,
			User user) {
		this.client = client;
		this.controller = controller;
		this.user = user;
	}
	
	public void handle(MessageBox mb) {
		Action action = mb.getAction();
		switch(action) {
			case CHAT:
				handleChat(mb, controller);
				return;
			case SERVER_MESSAGE:
				handleServerMessage(mb, controller);
				return;
			case DENY:
				handleDeny(controller);
				return;
			case ACCEPT:
				handleAccept(mb, controller);
				return;
			case INVITE:
				handleInvite(mb, controller);
				return;
			case GIVE_CHAT_SESSIONS:
				handleGiveSessions(mb);
			case GIVE_MEMBERS:
				handleGiveMembers(mb, controller, user);
				return;
			case GIVE_LOGGED_IN:
				handleGiveLoggedIn(mb, controller, user);
				return;
			case GIVE_CHAT_HISTORY:
				handleGiveChatHistory(mb, controller);
				return;
			case GIVE_FRIENDS:
				handleGiveFriends(mb, controller);
				return;
			case UPDATE_LOGGED_IN:
				handleUpdateLoggedIn(mb, controller, user);
				return;
			case UPDATE_LOGGED_OUT:
				handleUpdateLoggedOut(mb, controller, user);
				return;
			default:
				throw new IllegalStateException("Unrecognised command: " + action);
		}
	}
	
	public void handleChat(MessageBox mb, LoginController controller) {
		controller.getMainController().displayMessage(mb);
	}
	
	/**
	 * ServerMessage means that the server refused to create the chat room specified by the user,
	 * for example because it duplicates an existing chat room name
	 * 
	 * @param mb
	 * @param gui
	 */
	public void handleServerMessage(MessageBox mb, LoginController controller) {
		//controller.getLoginController().drawChatCreationRefusal();
		System.out.println(mb.get(Data.MESSAGE));
	}
	
	public void handleDeny(LoginController controller) {
		Platform.runLater(() -> controller.refuseLogin());
	}
	
	public void handleAccept(MessageBox mb, LoginController controller) {
		client.getUser().setUserName(mb.get(Data.USER_NAME));
		
		Platform.runLater(() -> {
			try {
				controller.login();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		MessageBox requestChatSessions = new MessageBox(Action.GET_CHAT_SESSIONS);
		requestChatSessions.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestChatSessions);
		
		MessageBox requestFriends = new MessageBox(Action.GET_FRIENDS);
		requestFriends.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestFriends);
		
		MessageBox requestLoggedIn = new MessageBox(Action.GET_LOGGED_IN);
		client.sendMessage(requestLoggedIn);
	}
	
	public void handleInvite(MessageBox mb, LoginController controller) {
		controller.getMainController().setInviteName(mb.get(Data.CHAT_NAME));
		controller.getMainController().setInviter(mb.get(Data.USER_NAME));
		Platform.runLater(() -> controller.getMainController().drawInviteScreen(mb));
	}
	
	public void handleGiveSessions(MessageBox mb) {
		List<String> chatSessions;
		chatSessions = Arrays.asList(mb.get(Data.CHAT_SESSIONS)
				.split(protocol.Token.SEPARATOR.getValue()));
		for(String session : chatSessions) {
			controller.getTreeViewRoot().getChildren().add(new TreeItem<String>(session));
			controller.addSessionSpace(session);
			MessageBox requestMembers = new MessageBox(Action.GET_MEMBERS);
			requestMembers.add(Data.CHAT_NAME, session);
			client.sendMessage(requestMembers);
		}
		MessageBox requestChatHistory = new MessageBox(Action.GET_CHAT_HISTORY);
		requestChatHistory.add(Data.USER_NAME, client.getUser().getUserName());
		client.sendMessage(requestChatHistory);
	}
	
	public void handleGiveChatHistory(MessageBox mb, LoginController controller) {
		Collections.reverse(mb.getMessageHistory());
		controller.getMessageSpaces().get(mb.get(Data.CHAT_NAME)).clear();
		for(Message message : mb.getMessageHistory()) {
			controller.getMessageSpaces().get(mb.get(Data.CHAT_NAME))
								  .appendText(message.getSender() + ">>> " +
										  message.getContent() + "\n");
		}
		
	}
	
	public void handleGiveMembers(MessageBox mb, LoginController controller, User user) {
		//user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setOnlineUsers(onlineUsers);
		String members = mb.get(Data.CHAT_MEMBERS);
		for (TreeItem<String> t : controller.getTreeViewRoot().getChildren()) {
			if (t.getValue().equals(mb.get(Data.CHAT_NAME))) {
				for (String member : members.split(protocol.Token.SEPARATOR.getValue())) {
					t.getChildren().add(new TreeItem<String>(member));
				}
			}
		}
	}
	
	public void handleGiveLoggedIn(MessageBox mb, LoginController controller, User user) {
		//client.getLoggedInUsers().clear();
		for(String username : mb.get(Data.LOGGED_IN_MEMBERS).split(protocol.Token.SEPARATOR.getValue())) {
			client.getLoggedInUsers().add(username);
		}
	}
	
	public void handleUpdateLoggedIn(MessageBox mb, LoginController controller, User user) {
		//user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setSessionMembers(sessionMembers);
		//client.getLoggedInUsers().clear();
		for(String username : mb.get(Data.USER_NAME).split(protocol.Token.SEPARATOR.getValue())) {
			client.getLoggedInUsers().add(username);
		}
		Platform.runLater(() -> controller.getMainController().updateFriendsListView());
//		String[] loggedInUsersServer = retrieveJoinedData(mb, Data.LOGGED_IN_MEMBERS);
//		TreeSet<String> loggedInUsersClient = new TreeSet<>();
//		for (String userName: loggedInUsersServer) {
//			loggedInUsersClient.add(userName);
//		}
//		user.getChatSessions().get(mb.get(Data.CHAT_NAME)).setSessionMembers(loggedInUsersClient);
	}
	
	public void handleUpdateLoggedOut(MessageBox mb, LoginController controller, User user) {
		client.getLoggedInUsers().remove(mb.get(Data.USER_NAME));
		Platform.runLater(() -> controller.getMainController().updateFriendsListView());
	}
	
	public void handleGiveFriends(MessageBox mb, LoginController controller) {
		for(String friend : mb.get(Data.USER_FRIENDS).split(protocol.Token.SEPARATOR.getValue())) {
			controller.getFriendsList().add(friend);
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
