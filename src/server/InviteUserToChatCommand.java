package server;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class InviteUserToChatCommand extends Command {

	InviteUserToChatCommand(MessageSender messageSender, UserChatSessions userChatSessions,
			AllChatSessions allChatSessions, ConnectedClients connectedClients) 
	{
		super(messageSender, userChatSessions, allChatSessions, connectedClients);
	}

	@Override
	void execute(MessageBox messageBox) {
		// pull out invitee username and chatname they're invited to
		String chatName = messageBox.get(Data.CHAT_NAME);
		String invitee = messageBox.get(Data.USER_NAME);
		
		// build messagebox for invitee with invite command and username of sender
		MessageBox inviteBox = new MessageBox(Action.INVITE);
		inviteBox.add(Data.USER_NAME, getMessageSender().getUserName());
		
		if(getConnectedClients().getClientByUserName(invitee) != null) {
			getConnectedClients().getClientByUserName(invitee).getMessageSender().sendMessage(inviteBox);			
		}
	}

}
