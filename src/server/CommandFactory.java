package server;


class CommandFactory {

        static Command buildCommand
                (Action action,
                 SessionTracker sessionTracker)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(sessionTracker);
                        case LOGIN:
                                return new LoginCommand(sessionTracker);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
