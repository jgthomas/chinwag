package server_command;

import database.Database;
import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.*;


/**
 * CONTRACT
 *
 * Action: Action.START_NEW_CHAT
 *
 * Data Required:
 * Data.CHAT_NAME - the name of the new session
 *
 **/
class StartNewChat extends Command {

    StartNewChat(MessageSender messageSender,
                 UserState userState,
                 AllChatSessions allChatSessions,
                 ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * Creates a new chat session.
     *
     * Can optionally add a user to the chat session at the same time.
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox) {
        String newChatName = messageBox.get(Data.CHAT_NAME);
        if (Database.chatExists(newChatName)){
            MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
            mb.add(Data.MESSAGE, "This chat name already exists, please try another one.");
            getMessageSender().sendMessage(mb);
        } else {
            ChatSession newChat = new ChatSession(newChatName);
            registerUserWithChat(newChat);
            registerChatOnSystem(newChat);
            Database.addUserToChat(newChatName, getCurrentThreadUserName());
        }
    }
}
