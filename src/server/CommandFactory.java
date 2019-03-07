package server;


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
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
