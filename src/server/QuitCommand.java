package server;

import protocol.MessageBox;

public class QuitCommand extends Command {

    QuitCommand(MessageSender messageSender,
                CurrentChatSessions currentChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {

    }
}
