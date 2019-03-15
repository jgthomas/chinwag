package server;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.Message;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

/**
 * CONTRACT
 *
 * Action: Action.GET_CHAT_HISTORY
 *
 * Data Required
 * Data.USER_NAME - username of user
 * Data.CHAT_NAME - specified if only single message history is required. If not
 * provided then all message histories for all chats are returned in separate
 * message boxes.
 *
 * */
public class GetChatHistoryCommand extends Command {

	GetChatHistoryCommand(MessageSender messageSender, UserState userState, AllChatSessions allChatSessions,
			ConnectedClients connectedClients) 
	{
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	/**
	 * Returns message history for a chat. If no chat is specified then the 
	 * message history for all chats that the user is in is returned.
	 */
	@Override
	void execute(MessageBox messageBox) {
		String username = messageBox.get(Data.USER_NAME);
		String singleChatName = messageBox.get(Data.CHAT_NAME);
		List<String> chatNames = Database.retrieveChatSessions(username);
		
		if (singleChatName == null) {
			for (String chat : chatNames) {
				sendMessageHistory(chat);
			}
		} else {
			sendMessageHistory(singleChatName);
		}
	}
	
	/**
	 * Queries the database for messages from a particular chat and returns
	 * a corresponding ArrayList of Message objects. 
	 * Sends the Messages back to client in a MessageBox.
	 * @param chatName Chat for which to return its message history.
	 */
	void sendMessageHistory(String chatName) {
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
