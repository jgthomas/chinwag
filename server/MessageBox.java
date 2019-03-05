package server;

import java.util.ArrayList;
import java.io.Serializable;


public class MessageBox implements Serializable {
        private final Action command;
        private final String target;
        private final String data;
        /* we can add anything here as long as it's serializable */

        /**
         * Creates a data transfer object.
         *
         * All of this is totally subject to change, obviously, but as this
         * is what's passed between client and server, I thought it should have
         * some documentation of its current state.
         *
         *
         * @param command a member of the Action enum, indicating what the
         *                recipient should do, login, send message, etc.
         *
         * @param target  an (optional) parameter regarding the target
         *                of the command, e.g. direct a message at a particular user
         *                ignored if blank
         *
         * @param data    the content of the communication, also optional. this
         *                could be the chat message, the name a user is logging
         *                in with, or whatever
         *
         *
         * Sample uses:
         *
         * (1) new MessageBox(Action.CHAT, "", "hello losers!")
         *     - tells the server to send the message to all users in the chat
         *
         * (2) new MessageBox(Action.LOGIN, "", "username")
         *     - tells the server to log the user in, no password stuff done yet, so
         *       all this really does is set a username
         *
         * (3) new MessageBox(Action.QUIT, "", "")
         *     - tells server to disconnect the client, the second two arguments
         *       could be filled in, they'd just be ignored for QUIT
         *
         * (4) new MessageBox(Action.CHAT, "@jack", "yo man")
         *     - this does not yet exist, but the idea is that this would send
         *       the message to the named user, '@' or however this is indicated
         *       is unknown
         *
         * */
        public MessageBox(Action command, String target, String data) {
                this.command = command;
                this.target = target;
                this.data = data;
        }

        /*
         * Constructor omitting target
         *
         */
        public MessageBox(Action command, String data) {
                this(command, new String(), data);
        }

        /*
         * Constructor with no target and a default CHAT action
         *
         */
        public MessageBox(String data) {
                this(Action.CHAT, new String(), data);
        }

        /*
         * Constructor taking only a command
         *
         */
        public MessageBox(Action command) {
                this(command, new String(), new String());
        }

        public Action getCommand() {
                return command;
        }

        public String getTarget() {
                return target;
        }

        public String getData() {
                return data;
        }
}
