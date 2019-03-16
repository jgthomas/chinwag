package protocol;

import java.util.Set;
import java.util.HashSet;


/**
 * Represents the commands that can be sent between client and
 * server.
 *
 * */
public enum Action {
        CHAT("CHAT"),
        SEND_IMAGE("SEND_IMAGE"),
        LOGIN("LOGIN"),
        START_NEW_CHAT("START_NEW_CHAT"),
        LEAVE_CHAT("LEAVE_CHAT"),
        QUIT("QUIT"),
        SIGN_UP("SIGN_UP"),
        ACCEPT("ACCEPT"),
        DENY("DENY"),
        SERVER_MESSAGE("SERVER_MESSAGE"),
        ADD_USER("ADD_USER"),
        ADD_FRIEND("ADD_FRIEND"),
        GET_CHAT_SESSIONS("GET_CHAT_SESSIONS"),
        GIVE_CHAT_SESSIONS("GIVE_CHAT_SESSIONS"),
        GET_MEMBERS("GET_MEMBERS"),
        GIVE_MEMBERS("GIVE_MEMBERS"),
        GET_LOGGED_IN("GET_LOGGED_IN"),
        GIVE_LOGGED_IN("GIVE_LOGGED_IN"),
		INVITE("INVITE"),
		UPDATE_LOGGED_IN("UPDATE_LOGGED_IN"),
		UPDATE_MEMBERS("UPDATE_MEMBERS"),
		GET_CHAT_HISTORY("GET_CHAT_HISTORY"),
		GIVE_CHAT_HISTORY("GIVE_CHAT_HISTORY"),
        GET_FRIENDS("GET_FRIENDS"),
        GIVE_FRIENDS("GIVE_FRIENDS");

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
