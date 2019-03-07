package server;


public class CommandFactory {

        static Command buildCommand
                (Action action,
                 SessionTracker sessionTracker)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(sessionTracker);
                        case LOGIN:
                                return new LoginCommand(sessionTracker);
//                        case USERS:
//                                return new UsersCommand(sessionTracker);
//                        case CREATE:
//                                return new CreateCommand(sessionTracker);
//                        case SESSION:
//                                return new SessionCommand(sessionTracker);
//                        case SWITCH:
//                                return new SwitchCommand(sessionTracker);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
