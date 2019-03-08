package server;

import protocol.MessageBox;

/**
 * CONTRACT
 *
 * Action: Action.DIRECT_MESSAGE
 *
 * Data Required
 * Data.USER_NAME - the message recipient
 * Data.MESSAGE - the content to send to the chat
 *
 **/
public class DirectMessageCommand extends Command {

    DirectMessageCommand(MessageSender messageSender, SessionTracker sessionTracker) {
        super(messageSender, sessionTracker);
    }

    @Override
    public void execute(MessageBox messageBox) {
        ChatContext global = getSessionTracker().getSession("global");
        getMessageSender().postMessage(global, messageBox);
    }
}
