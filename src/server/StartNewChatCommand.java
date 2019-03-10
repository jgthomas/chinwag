package server;

import database.Database;
import protocol.Action;
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
                        AllChatSessions allChatSessions,
                        ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, allChatSessions, connectedClients);
    }

    @Override
    void execute(MessageBox messageBox) {
    	// first check if chat session name already exists
        String newChatName = messageBox.get(Data.CHAT_NAME);
        if (Database.chatExists(newChatName)){
            MessageBox mb = new MessageBox(Action.SERVER_MESSAGE);
            mb.add(Data.MESSAGE, "This chat name already exists, please try another one.");
            getMessageSender().sendMessage(mb);
        } else {
            ChatSession newChat = new ChatSession(newChatName);
            Database.addUserToChat(newChatName, getMessageSender().getUserName());
            newChat.addUser(getMessageSender());
            getCurrentChatSessions().addSession(newChat);

            String userToChatWith = messageBox.get(Data.USER_NAME);
            if (userToChatWith != null) {
                MessageHandler user = getUser(userToChatWith);
                addUserToChat(newChat, user);
            }
        }
    }
}
