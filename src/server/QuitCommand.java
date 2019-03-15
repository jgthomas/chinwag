package server;

import protocol.MessageBox;

class QuitCommand extends Command {

    QuitCommand(MessageSender messageSender,
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

    }
}
