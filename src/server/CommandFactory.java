package server;


import protocol.Action;

class CommandFactory {

        static Command buildCommand
                (Action action,
                 MessageSender messageSender,
                 CurrentChatSessions currentChatSessions,
                 ConnectedClients connectedClients)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(messageSender, currentChatSessions, connectedClients);
                        case LOGIN:
                                return new LoginCommand(messageSender, currentChatSessions, connectedClients);
                        case SIGN_UP:
                                return new SignUpCommand(messageSender, currentChatSessions, connectedClients);
                        case START_NEW_CHAT:
                                return new StartNewChatCommand(messageSender, currentChatSessions, connectedClients);
                        case QUIT:
                                return new QuitCommand(messageSender, currentChatSessions, connectedClients);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
