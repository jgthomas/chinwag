package server;


import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

import java.util.Date;
import java.util.List;

/**
 * CONTRACT
 *
 * Action: Action.LOGIN
 *
 * Data Required:
 * Data.USER_NAME
 * Data.PASSWORD
 *
 **/
class LoginCommand extends Command {

	LoginCommand(MessageSender messageSender,
				 UserState userState,
				 AllChatSessions allChatSessions,
				 ConnectedClients connectedClients)
	{
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	/**
	 * Logs a user in.
	 *
	 * Checks for previously unsuccessful attempts and loads user's chat sessions
	 *
	 * @param messageBox the command from the client to perform
	 * */
	@Override
	public void execute(MessageBox messageBox) {
		String username = messageBox.get(Data.USER_NAME);
		String password = messageBox.get(Data.PASSWORD);
		if (Database.userExists(username)){
			if (Server.getLockedAccounts().containsKey(username)){
				if (new Date().getTime() - Server.getLockedAccounts().get(username).getTime() >= 600000){
					Server.getLockedAccounts().remove(username);
					verifyUser(username, password);
				} else {
					MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
					mb.add(Data.MESSAGE, "The account has been locked, please try again in 10 minutes.");
					getMessageSender().sendMessage(mb);
				}
			} else
				verifyUser(username, password);
		} else {
			MessageBox mb = new MessageBox(Action.DENY);
			getMessageSender().sendMessage(mb);
		}
	}

	private void verifyUser(String username, String password){
		// get the salt for the specified user
		byte[] salt = Hasher.stringToBytes(Database.getSalt(username));
		
		// hash the combined password + salt
    	String hash = Hasher.hashPassword(password, salt);
    	
    	/*
    	 * Query database for matching username + hash.
    	 * Login user if match is found, else send deny message and update
    	 * failed attempts counter and/or locked accounts.
    	 */
		if (Database.isValidUser(username, hash)){
			sendAcceptMessage(username);
			setUserName(username);
			addAsLoggedInClient(getCurrentThreadID(), username);
			registerUserWithGlobal();
			loadSessions();
			loadFriends();
		} else {
			sendDenyMessage();
			if (Server.getFailedAttempts().get(username) == null) {
					Server.getFailedAttempts().put(username, 1);
			}
			else if (Server.getFailedAttempts().get(username).equals(2)) {
				Server.getFailedAttempts().remove(username);
				Server.getLockedAccounts().put(username, new Date());
			} else
				Server.getFailedAttempts().put(username, Server.getFailedAttempts().get(username) + 1);
		}
	}

	/**
	 * Loads from database the ChatSession names. Creates new ChatSession if it
	 * doesn't exist, registers the chat with system, and the user with that chat.
	 */
	private void loadSessions(){
		List<String> chatSessions = Database.retrieveChatSessions(getCurrentThreadUserName());
		if (chatSessions != null) {
			for (String chatName : chatSessions) {
				if (getAllChatSessions().sessionExists(chatName)) {
					registerUserWithChat(getAllChatSessions().getSession(chatName));
				} else {
					ChatSession chatSession = new ChatSession(chatName);
					registerUserWithChat(chatSession);
					registerChatOnSystem(chatSession);
				}
			}
		}
	}
	
	/**
	 * Loads from database the usernames of friends into UserState object 
	 */
	private void loadFriends() {
		List<String> friends = Database.retrieveFriends(getCurrentThreadUserName());
		if (friends != null) {
			for (String friend : friends) {
				getUserState().addFriend(friend);
			}
		}
	}
	
	// placeholder: send message history to client

	private void setUserName(String name) {
		getMessageSender().setUserName(name);
	}

	/**
	 * Will be removed once we get rid of global chat - needed until then!
	 * */
	private void registerUserWithGlobal() {
		ChatSession chatSession = getAllChatSessions().getSession("global");
		registerUserWithChat(chatSession);
		//getUserState().getSession("global").addUser(getMessageSender());
		//getAllChatSessions().getSession("global").addUser(getMessageSender());
	}

	private void addAsLoggedInClient(String id, String userName) {
		getConnectedClients().addClientByUserName(id, userName);
	}

	private void sendAcceptMessage(String userName) {
		MessageBox mb = new MessageBox(Action.ACCEPT);
		mb.add(Data.USER_NAME, userName);
		getMessageSender().sendMessage(mb);
	}

	private void sendDenyMessage() {
		MessageBox mb = new MessageBox(Action.DENY);
		getMessageSender().sendMessage(mb);
	}
}
