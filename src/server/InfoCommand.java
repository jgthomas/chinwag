package server;

import protocol.MessageBox;

public class InfoCommand extends Command {

    InfoCommand(MessageSender messageSender,
                UserChatSessions userChatSessions,
                AllChatSessions allChatSessions,
                ConnectedClients connectedClients)
    {
        super(messageSender, userChatSessions, allChatSessions, connectedClients);
    }

    @Override
    public void execute(MessageBox messageBox) {
        switch (messageBox.getAction()) {
            case LIST_CHAT_SESSIONS:
                break;
            case LIST_MEMBERS:
                break;
            case LIST_LOGGED_IN:
                break;
        }
    }
}
