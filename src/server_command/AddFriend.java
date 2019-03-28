package server_command;

import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.*;


/**
 * CONTRACT
 *
 * Action: Action.ADD_FRIEND
 *
 * Data Required
 * Data.USER_NAME - username of friend
 *
 * */
class AddFriend extends Command {

	AddFriend(MessageSender messageSender,
			  UserState userState,
			  AllChatSessions allChatSessions,
              ConnectedClients connectedClients)
	{
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	/**
	 * Adds the specified friend username to the database and to the UserState
	 * object for this client. 
	 * 
	 * If already a friend then just send a message back to inform client of this.
	 */
	@Override
	public void execute(MessageBox messageBox) {
		String friend = messageBox.get(Data.USER_NAME);
		if (Database.userExists(friend)){
			if(!Database.isFriend(getCurrentThreadUserName(), friend)) {
				Database.insertFriend(getCurrentThreadUserName(), friend);
				getUserState().addFriend(friend);
			} else {
				MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
				mb.add(Data.MESSAGE, "This user is already in your friend list");
				getMessageSender().sendMessage(mb);
			}
		} else {
			MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
			mb.add(Data.MESSAGE, "The username entered doesn't exist.");
			getMessageSender().sendMessage(mb);
		}
	}

}
