package server_command;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.AllChatSessions;
import server.ConnectedClients;
import server.MessageSender;
import server.UserState;

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

	InviteUserToChatCommand(MessageSender messageSender,
							UserState userState,
							AllChatSessions allChatSessions,
							ConnectedClients connectedClients)
	{
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	/**
	 * Sends an invite to invitee to join a chat session
	 */
	@Override
	public void execute(MessageBox messageBox) {
		// pull out invitee username and chatname they're invited to
		String chatName = messageBox.get(Data.CHAT_NAME);
		String invitee = messageBox.get(Data.USER_NAME);
		
		// build messagebox for invitee with invite command, username of sender, 
		//   and chatname of chatsession they're being invited to
		MessageBox inviteBox = new MessageBox(Action.INVITE);
		inviteBox.add(Data.USER_NAME, getCurrentThreadUserName());
		inviteBox.add(Data.CHAT_NAME, chatName);
		
		// if invitee is online then send invite to them
		if(getUser(invitee) != null) {
			getUser(invitee).getMessageSender().sendMessage(inviteBox);			
		}
	}

}
