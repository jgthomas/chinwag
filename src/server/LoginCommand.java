package server;


import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

import java.util.Date;

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
				 CurrentChatSessions currentChatSessions,
				 ConnectedClients connectedClients)
	{
		super(messageSender, currentChatSessions, connectedClients);
	}

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

	public void verifyUser(String username, String password){
		if (Database.isValidUser(username, password)){

			MessageBox mb = new MessageBox(Action.ACCEPT);
			getMessageSender().sendMessage(mb);
			setUserName(username);
			registerSender();
			addAsLoggedInClient(getMessageSender().id(), username);
		} else {
			MessageBox mb = new MessageBox(Action.DENY);
			getMessageSender().sendMessage(mb);
			if (Server.getFailedAttempts().get(username) == 2) {
				Server.getFailedAttempts().remove(username);
				Server.getLockedAccounts().put(username, new Date());
			} else if (Server.getFailedAttempts().get(username) == null)
				Server.getFailedAttempts().put(username, 1);
			else
				Server.getFailedAttempts().put(username, Server.getFailedAttempts().get(username) + 1);
		}
	}

	private void setUserName(String name) {
		getMessageSender().setUserName(name);
	}

	private void registerSender() {
		getCurrentChatSessions().getSession("global").addUser(getMessageSender());
	}

	private void addAsLoggedInClient(String id, String userName) {
		getConnectedClients().addClientByUserName(id, userName);
	}
}
