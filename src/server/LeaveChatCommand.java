package server;

import database.Database;
import protocol.MessageBox;

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
class LeaveChatCommand extends Command{

    LeaveChatCommand(MessageSender messageSender,
                        UserChatSessions userChatSessions,
                        AllChatSessions allChatSessions,
                        ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    @Override
    void execute(MessageBox messageBox){
        String chatname = messageBox.get(CHAT_NAME);
        MessageSender senderToBeRemoved = getMessageSender();
        Database.removeUserFromChat(chatname, senderToBeRemoved.getUserName());
        for (MessageSender messageSender: getUserChatSessions().getSession(chatname)) {
            getUser(messageSender.getUserName()).getUserChatSessions().
                    getSession(chatname).removeUser(senderToBeRemoved);
        }
        getUserChatSessions().removeSession(chatname);
    }

}
