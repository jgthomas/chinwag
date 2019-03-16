package server_command;

import java.util.ArrayList;

import database.Database;
import database.Message;
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
 * Action: Action.GET_CHAT_HISTORY
 *
 * Data Required
 * Data.CHAT_NAME - specified if only single message history is required. If not
 * provided then all message histories for all chats are returned in separate
 * message boxes.
 *
 * */
class GetChatHistoryCommand extends Command {

	GetChatHistoryCommand(MessageSender messageSender,
						  UserState userState,
						  AllChatSessions allChatSessions,
						  ConnectedClients connectedClients)
	{
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	/**
	 * Returns message history for a chat. 
	 */
	@Override
	public void execute(MessageBox messageBox) {
		String chatName = messageBox.get(Data.CHAT_NAME);
		int messageLimit = 200;
		ArrayList<Message> messageList = Database.retrieveMessages(chatName, messageLimit);
		if (messageList != null) {
			MessageBox mb = new MessageBox(Action.GIVE_CHAT_HISTORY);
			mb.add(Data.CHAT_NAME, chatName);
			mb.addMessageHistory(messageList);
			getMessageSender().sendMessage(mb);
		}
	}
}
