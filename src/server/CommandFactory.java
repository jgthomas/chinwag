package server;


public class CommandFactory {

        static Command buildCommand
                (Action action,
                 ConnectionTracker connectionTracker)
        {
                switch (action) {
                        case CHAT:
                                return new ChatCommand(connectionTracker);
                        case LOGIN:
                                return new LoginCommand(connectionTracker);
//                        case USERS:
//                                return new UsersCommand(connectionTracker);
//                        case CREATE:
//                                return new CreateCommand(connectionTracker);
//                        case SESSION:
//                                return new SessionCommand(connectionTracker);
//                        case SWITCH:
//                                return new SwitchCommand(connectionTracker);
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}
