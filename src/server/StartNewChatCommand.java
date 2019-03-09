package server;

import protocol.Data;
import protocol.MessageBox;


/**
 * CONTRACT
 *
 * Action: Action.START_NEW_CHAT
 *
 * Data Required:
 * Data.CHAT_NAME - the name of the new session
 *
 * Data Optional
 * Data.USER_NAME - the user to chat with, they will be pulled into chat
 *                  if not provided, a chat session with only its creator
 *                  will be made
 *
 **/
class StartNewChatCommand extends Command {

    StartNewChatCommand(MessageSender messageSender,
                        CurrentChatSessions currentChatSessions,
                        ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, connectedClients);
    }

    @Override
    void execute(MessageBox messageBox) {
    	// first check if chat session name already exists
    	
    	
        String newChatName = messageBox.get(Data.CHAT_NAME);
        ChatSession newChat = new ChatSession(newChatName);
        newChat.addUser(getMessageSender());
        getSessionTracker().addSession(newChat);

        String userToChatWith = messageBox.get(Data.USER_NAME);

        if (userToChatWith != null) {
            MessageHandler user = getUser(userToChatWith);
            addUserToChat(newChat, user);
        }
    }
}
