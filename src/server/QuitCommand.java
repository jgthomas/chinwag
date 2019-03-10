package server;

import protocol.MessageBox;

public class QuitCommand extends Command {

    QuitCommand(MessageSender messageSender,
                CurrentChatSessions currentChatSessions,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, allChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {

    }
}
