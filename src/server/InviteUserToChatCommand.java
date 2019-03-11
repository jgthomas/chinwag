package server;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

/**
 * CONTRACT
 *
 * Action: Action.INVITE
 *
 * Data Required
 * Data.CHAT_NAME - the name of the chat
 * Data.USER_NAME - the user to invite to the chat
 * 
 *
 * */
public class InviteUserToChatCommand extends Command {

	InviteUserToChatCommand(MessageSender messageSender, UserChatSessions userChatSessions,
			AllChatSessions allChatSessions, ConnectedClients connectedClients) 
	{
		super(messageSender, userChatSessions, allChatSessions, connectedClients);
	}

	/**
	 * Sends an invite to invitee to join a chat session
	 */
	@Override
	void execute(MessageBox messageBox) {
		// pull out invitee username and chatname they're invited to
		String chatName = messageBox.get(Data.CHAT_NAME);
		String invitee = messageBox.get(Data.USER_NAME);
		
		// build messagebox for invitee with invite command and username of sender
		MessageBox inviteBox = new MessageBox(Action.INVITE);
		inviteBox.add(Data.USER_NAME, getMessageSender().getUserName());
		inviteBox.add(Data.CHAT_NAME, chatName);
		
		// if invitee is online then send invite to them
		if(getConnectedClients().getClientByUserName(invitee) != null) {
			getConnectedClients().getClientByUserName(invitee).getMessageSender().sendMessage(inviteBox);			
		}
	}

}
