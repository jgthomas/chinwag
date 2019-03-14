package server;

import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;


/**
 * CONTRACT
 *
 * Action: Action.ADD_FRIEND
 *
 * Data Required
 * Data.USER_NAME - username of friend
 *
 * */
public class AddFriendCommand extends Command {

	AddFriendCommand(MessageSender messageSender, UserChatSessions userChatSessions, AllChatSessions allChatSessions,
			ConnectedClients connectedClients) 
	{
		super(messageSender, userChatSessions, allChatSessions, connectedClients);
	}

	@Override
	void execute(MessageBox messageBox) {
		String friend = messageBox.get(Data.USER_NAME);
		if(!Database.isFriend(getCurrentThreadUserName(), friend)) {
			Database.insertFriend(getCurrentThreadUserName(), friend);
			
		} else {
			MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
			mb.add(Data.MESSAGE, "This user is already in your friend list");
			getMessageSender().sendMessage(mb);
		}
	}

}
