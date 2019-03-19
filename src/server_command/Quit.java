package server_command;

import protocol.Action;
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
        getMessageSender().sendMessage(new MessageBox(Action.UPDATE_LOGGED_OUT));
    }
}
