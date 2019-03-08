package server;


import protocol.Action;

class CommandFactory {

        static Command buildCommand
                (Action action,
                 MessageSender messageSender,
                 SessionTracker sessionTracker)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(messageSender, sessionTracker);
                        case LOGIN:
                                return new LoginCommand(messageSender, sessionTracker);
                        case SIGN_UP:
                                return new SignUpCommand(messageSender, sessionTracker);
                        case START_NEW_CHAT:
                                return new StartNewChatCommand(messageSender, sessionTracker);
                        case DIRECT_MESSAGE:
                                return new DirectMessageCommand(messageSender, sessionTracker);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
