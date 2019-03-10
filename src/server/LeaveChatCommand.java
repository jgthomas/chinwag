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
public class LeaveChatCommand extends Command{

    LeaveChatCommand(MessageSender messageSender,
                        CurrentChatSessions currentChatSessions,
                        ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, connectedClients);
    }

    @Override
    void execute(MessageBox messageBox){
        String chatname = messageBox.get(CHAT_NAME);
        MessageSender senderToBeRemoved = getMessageSender();
        Database.removeUserFromChat(chatname, senderToBeRemoved.getUserName());
        for (MessageSender messageSender: getCurrentChatSessions().getSession(chatname)) {
            getConnectedClients().getClientByUserName(messageSender.getUserName()).getCurrentChatSessions().
                    getSession(chatname).removeUser(senderToBeRemoved);
        }
        getCurrentChatSessions().removeSession(chatname);
    }

}
