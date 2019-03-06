package server;

import java.util.Set;
import java.util.HashSet;


/**
 * Represents the commands that can be sent between client and
 * server.
 *
 * */
public enum Action {
        CHAT("CHAT"),
        LOGIN("LOGIN"),
        USERS("USERS"),
        CREATE("CREATE"),
        SESSION("SESSION"),
        SWITCH("SWITCH"),
        QUIT("QUIT");

        private static final Set<String> validCommands = new HashSet<>();

        static {
                for (Action action : Action.values()) {
                        validCommands.add(action.value());
                }
        }

        String token;

        Action(String t) {
                token = t;
        }

        public String getToken() {
                return token;
        }

        public static boolean isValidCommand(String command) {
                return validCommands.contains(command);
        }
}
