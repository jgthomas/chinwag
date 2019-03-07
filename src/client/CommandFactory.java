package client;

import protocol.Action;
import protocol.MessageBox;

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