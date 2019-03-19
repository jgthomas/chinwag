package server_command;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.*;

class Quit extends Command {

    Quit(MessageSender messageSender,
         UserState userState,
         AllChatSessions allChatSessions,
         ConnectedClients connectedClients)
    {
        super(messageSender, userState, allChatSessions, connectedClients);
    }

    /**
     * Placeholder.
     *
     * Quit is done by exiting the listening loop
     *
     * @param messageBox the command from the client to perform
     * */
    @Override
    public void execute(MessageBox messageBox) {
        MessageBox mb = new MessageBox(Action.UPDATE_LOGGED_OUT);
        mb.add(Data.USER_NAME, getCurrentThreadUserName());

        for (ChatSession chatSession : getUserState()) {
            getMessageSender().postMessage(chatSession, mb);
        }

        for (String name : getUserState().getAllFriends()) {
            MessageHandler user = getUser(name);
            user.getMessageSender().sendMessage(mb);
        }
    }
}
