package server_command;

import database.Database;
import protocol.MessageBox;
import server.*;

import static protocol.Data.CHAT_NAME;

/**
 * CONTRACT
 *
 * Action: Action.LEAVE_CHAT
 *
 * Data Required:
 * Data.CHAT_NAME - the name of the session to leave
 *
 **/
class LeaveChat extends Command {

    LeaveChat(MessageSender messageSender,
              UserState userState,
              AllChatSessions allChatSessions,
              ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * Remove the user from a chat session
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox){
        String chatName = messageBox.get(CHAT_NAME);
        Database.removeUserFromChat(chatName, getCurrentThreadUserName());
        leaveChat(chatName);
    }

    /**
     * Remove the user from the chat session
     *
     * First it removes the user's sender object from the chat
     * (this only needs to be done once, as all users have a reference
     *  to the *same* chat object)
     *
     * Second it removes the chat from the user's current sessions
     *
     * @param chatName the chat from which the user is to be removed
     * */
    private void leaveChat(String chatName) {
        ChatSession chatSession = getAllChatSessions().getSession(chatName);
        removeUserFromChat(chatSession);

        if (chatSession.isEmpty()) {
            getAllChatSessions().removeSession(chatName);
        }
    }

}
