package server_command;

import database.Database;
import protocol.Data;
import protocol.MessageBox;
import server.*;


/**
 * CONTRACT
 *
 * Action: Action.ADD_USER
 *
 * Data Required
 * Data.CHAT_NAME - the name of the chat
 * Data.USER_NAME - the user to add to the chat
 *
 * */
class AddUserToChat extends Command {

    AddUserToChat(MessageSender messageSender,
                  UserState userState,
                  AllChatSessions allChatSessions,
                  ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * Adds another user to an existing chat session
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox) {
		String chatName = messageBox.get(Data.CHAT_NAME);
		String username = getCurrentThreadUserName();
		Database.addUserToChat(chatName, username);
		ChatSession chat = getAllChatSessions().getSession(chatName);
		registerUserWithChat(chat);
	}
}