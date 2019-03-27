package server_command;

import protocol.MessageBox;
import server.AllChatSessions;
import server.ConnectedClients;
import server.MessageSender;
import server.UserState;

public class Logout extends Command {

	public Logout(MessageSender messageSender, UserState userState, AllChatSessions allChatSessions,
			ConnectedClients connectedClients) {
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	@Override
	public void execute(MessageBox messageBox) {
		
		
		
	}

}
