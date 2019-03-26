package server_command;

import database.Database;
import protocol.Data;
import protocol.MessageBox;
import server.AllChatSessions;
import server.ConnectedClients;
import server.MessageSender;
import server.UserState;

/**
 * CONTRACT
 *
 * Action: Action.REMOVE_FRIEND
 *
 * Data Required
 * Data.USER_NAME - username of friend
 *
 * */
public class RemoveFriend extends Command{

    RemoveFriend(MessageSender messageSender,
              UserState userState,
              AllChatSessions allChatSessions,
              ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * Removes the specified friend username from the database and from the UserState
     * object for this client.
     *
     * If already a friend then just send a message back to inform client of this.
     */
    @Override
    public void execute(MessageBox messageBox){
        String friend = messageBox.get(Data.USER_NAME);
        if (Database.isFriend(getCurrentThreadUserName(), friend)) {
            Database.removeFriend(getCurrentThreadUserName(), friend);
            getUserState().getAllFriends().remove(friend);
        }
    }

}
