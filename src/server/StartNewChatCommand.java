package server;

import protocol.Data;
import protocol.MessageBox;

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
