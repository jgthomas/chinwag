package server;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.Message;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class GetChatHistoryCommand extends Command {

	GetChatHistoryCommand(MessageSender messageSender, UserState userState, AllChatSessions allChatSessions,
			ConnectedClients connectedClients) 
	{
		super(messageSender, userState, allChatSessions, connectedClients);
	}

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
