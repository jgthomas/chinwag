package server_command;

import database.Database;
import protocol.Data;
import protocol.MessageBox;
import server.AllChatSessions;
import server.ConnectedClients;
import server.MessageSender;
import server.UserState;

public class RemoveFriend extends Command{

    RemoveFriend(MessageSender messageSender,
              UserState userState,
              AllChatSessions allChatSessions,
              ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox){
        String friend = messageBox.get(Data.USER_NAME);
        if (Database.isFriend(getCurrentThreadUserName(), friend)) {
            Database.removeFriend(getCurrentThreadUserName(), friend);
            getUserState().getAllFriends().remove(friend);
        }
    }

}
