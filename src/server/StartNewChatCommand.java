package server;

import protocol.Data;
import protocol.MessageBox;


/**
 * CONTRACT
 *
 * Action: Action.NEW_CHAT
 *
 * Data:
 *
 * Data.CHAT_NAME - the name of the new session
 *
 **/
class StartNewChatCommand extends Command {

    StartNewChatCommand(MessageSender messageSender, SessionTracker sessionTracker) {
        super(messageSender, sessionTracker);
    }

    @Override
    void execute(MessageBox messageBox) {
        String newChatName = messageBox.get(Data.CHAT_NAME);
        ChatContext newChat = new ChatSession(newChatName);
        newChat.addUser(getMessageSender());
        getSessionTracker().addSession(newChat);
    }
}
