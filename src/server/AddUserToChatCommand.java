package server;

import protocol.MessageBox;

class AddUserToChatCommand extends Command {

    AddUserToChatCommand(MessageSender messageSender,
                         CurrentChatSessions currentChatSessions,
                         ConnectedClients connectedClients)
    {
        super(messageSender, currentChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {
        
    }
}
