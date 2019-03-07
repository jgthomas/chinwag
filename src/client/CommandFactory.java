package client;

import server.Action;
import server.MessageBox;

class CommandFactory {

        static Command buildCommand
                (Action action, MessageBox mb)
        {
                switch (action) {
                        case CHAT:
                                return new MessageCommand();
                        default:
                                throw new IllegalStateException("Unrecognised command: " + action);
                }
        }
}