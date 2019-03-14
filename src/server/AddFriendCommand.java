package server;

import protocol.MessageBox;

public class AddFriendCommand extends Command {

	AddFriendCommand(MessageSender messageSender, UserChatSessions userChatSessions, AllChatSessions allChatSessions,
			ConnectedClients connectedClients) 
	{
		super(messageSender, userChatSessions, allChatSessions, connectedClients);
	}

	@Override
	void execute(MessageBox messageBox) {
		
	}

}
