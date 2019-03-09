package server;


import protocol.Action;

class CommandFactory {

        static Command buildCommand
                (Action action,
                 MessageSender messageSender,
                 Sessions sessionTracker,
                 ConnectedClients connectedClients)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(messageSender, sessionTracker, connectedClients);
                        case LOGIN:
                                return new LoginCommand(messageSender, sessionTracker, connectedClients);
                        case SIGN_UP:
                                return new SignUpCommand(messageSender, sessionTracker, connectedClients);
                        case START_NEW_CHAT:
                                return new StartNewChatCommand(messageSender, sessionTracker, connectedClients);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
