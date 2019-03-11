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
                        UserChatSessions userChatSessions,
                        AllChatSessions allChatSessions,
                        ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    /**
     * Creates a new chat session.
     *
     * Can optionally add a user to the chat session at the same time.
     *
     * @param messageBox the command from the client to perform
     * */
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
            Database.addUserToChat(newChatName, getCurrentThreadUserName());
            registerUserWithChat(newChat);
            registerChatOnSystem(newChat);
            //newChat.addUser(getMessageSender());
            //registerNewChat(newChat);

            String userToChatWith = messageBox.get(Data.USER_NAME);
            if (userToChatWith != null) {
                addOtherUserToChat(newChatName, userToChatWith);
            }
        }
    }

    /**
     * Adds a newly created chat to the master record, AND to the
     * chats of the current user
     *
     * @param chatSession the session to add to the master record
     * */
    //private void registerNewChat(ChatSession chatSession) {
    //    getAllChatSessions().addSession(chatSession);
    //   getUserChatSessions().addSession(chatSession);
    //}
}
