package server;

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
                         CurrentChatSessions currentChatSessions,
                         ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {
        String chatName = messageBox.get(Data.CHAT_NAME);
        String user = messageBox.get(Data.USER_NAME);
        MessageHandler mh = getUser(user);
        ChatSession chat = getCurrentChatSessions().getSession(chatName);
        addUserToChat(chat, mh);
    }
}
