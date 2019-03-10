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
        String username = messageBox.get(Data.USER_NAME);
        Database.addUserToChat(chatName, username);
        //getUserChatSessions().getSession(chatName).addUser(getMessageSender());
        //for (MessageSender messageSender: getUserChatSessions().getSession(chatName)) {
        //    getUser(messageSender.getUserName()).getUserChatSessions().
        //            getSession(chatName).addUser(getUser(username).getMessageSender());
        //}
        MessageHandler userToAdd = getUser(username);
        addOtherUserToChat(chatName, userToAdd);
    }
}
