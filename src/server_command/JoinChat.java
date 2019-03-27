package server_command;

import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.*;

import java.util.concurrent.ConcurrentMap;


/**
 * CONTRACT
 *
 * Action: Action.ADD_USER
 *
 * Data Required
 * Data.CHAT_NAME - the name of the chat
 * Data.USER_NAME - the username of invite sender
 *
 * */
class JoinChat extends Command {

    JoinChat(MessageSender messageSender,
                  UserState userState,
                  AllChatSessions allChatSessions,
                  ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * Adds current user to an existing chat session
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox) {
		String chatName = messageBox.get(Data.CHAT_NAME);
		String username = getCurrentThreadUserName();
		Database.addUserToChat(chatName, username);
		ChatSession chat = getAllChatSessions().getSession(chatName);
		MessageBox mb = new MessageBox(Action.CONFIRM);
		mb.add(Data.CHAT_NAME, chatName);
		mb.add(Data.USER_NAME, username);
		ChatSession chatSession = getAllChatSessions().getSession(chatName);
		for (MessageSender ms: chatSession) {
			ms.sendMessage(mb);
		}
        registerUserWithChat(chat);
	}
}
