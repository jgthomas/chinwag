package server;

import protocol.MessageBox;

public class InviteUserToChatCommand extends Command {

	InviteUserToChatCommand(MessageSender messageSender, UserChatSessions userChatSessions,
			AllChatSessions allChatSessions, ConnectedClients connectedClients) 
	{
		super(messageSender, userChatSessions, allChatSessions, connectedClients);
	}

	@Override
	void execute(MessageBox messageBox) {
		
	}

}
