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

    @Override
    public void execute(MessageBox messageBox) {
        String chatname = messageBox.get(Data.CHAT_NAME);
        String username = messageBox.get(Data.USER_NAME);
        Database.addUserToChat(chatname, username);
        getUserChatSessions().getSession(chatname).addUser(getMessageSender());
        for (MessageSender messageSender: getUserChatSessions().getSession(chatname)) {
            getUser(messageSender.getUserName()).getUserChatSessions().
                    getSession(chatname).addUser(getUser(username).getMessageSender());
        }
    }
}
