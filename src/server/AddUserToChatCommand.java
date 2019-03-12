package server;

import database.Database;
import protocol.Data;
import protocol.MessageBox;


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
class AddUserToChatCommand extends Command {

    AddUserToChatCommand(MessageSender messageSender,
                         UserChatSessions userChatSessions,
                         AllChatSessions allChatSessions,
                         ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    /**
     * Adds another user to an existing chat session
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox) {
        String chatName = messageBox.get(Data.CHAT_NAME);
        //String username = getCurrentThreadUserName();
        String username = messageBox.get(Data.USER_NAME); // replace with above

        if (getUserChatSessions().isInChat(chatName)) {
            Database.addUserToChat(chatName, username);
            //ChatSession chat = getAllChatSessions().getSession(chatName);
            //registerUserWithChat(chat);
            addOtherUserToChat(chatName, username); // replace with above
        }
    }
}
