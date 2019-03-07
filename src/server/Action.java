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
        NEW_CHAT("NEW_CHAT"),
        SESSION("SESSION"),
        SWITCH("SWITCH"),
        QUIT("QUIT"),
        SIGN_UP("SIGN_UP"),
        ACCEPT("ACCEPT"),
        DENY("DENY"),
        SERVER_MESSAGE("SERVER_MESSAGE");

        private static final Set<String> validCommands = new HashSet<>();

        static {
                for (Action action : Action.values()) {
                        validCommands.add(action.getToken());
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
